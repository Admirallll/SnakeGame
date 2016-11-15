package ru.admirall.snake;


import ru.admirall.snake.gameobjects.Apple;
import ru.admirall.snake.gameobjects.GameObject;
import ru.admirall.snake.gameobjects.ICollider;
import ru.admirall.snake.gameobjects.ITurnActioner;
import ru.admirall.snake.levels.Level;
import ru.admirall.snake.levels.LevelInfo;
import ru.admirall.snake.players.Player;
import ru.admirall.snake.primitives.Location;
import ru.admirall.snake.snake.SnakePart;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SnakeGame implements IGame, Serializable {

	transient private int applesToCreate;
	transient private List<Color> colors;
	private List<Player> players;
	transient private LevelInfo[] levels;
	private Level currentLevel;
	
	public SnakeGame(LevelInfo[] levels, List<Player> players) {
		this.levels = levels;
        this.players = players;
        colors = createColors();
        for (Player player : players)
        	player.setColor(getFreeColor());
        changeLevel(0);
	}
	
	public Color getFreeColor() {
		for (Color color : colors) {
			boolean isFreeColor = true;
			for (Player player : players.stream().filter(Player::isAlive).toArray(Player[]::new))
				if (color == player.getColor())
					isFreeColor = false;
			if (isFreeColor)
				return color;
		}
		return null;
	}
	
	public void changePlayerColor(Player player) {
		player.setColor(getFreeColor());
	}
	
	public ArrayList<Color> createColors() {
		ArrayList<Color> colors = new ArrayList<>();
		colors.add(Color.BLUE);
		colors.add(Color.ORANGE);
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		return colors;
	}
	
	public List<Color> getColors() {
		return colors;
	}
	
	public void changeLevel(int nextLevelIndex) {
		currentLevel = new Level(levels[nextLevelIndex], players);
	}

	public Level getLevel(){
	    return currentLevel;
    }
	
	public void addApplesToCreate(int apples) {
		applesToCreate += apples;
	}

    private void createApplesOnField() {
        while (applesToCreate > 0) {
            Location randomLocation = getRandomEmptyLocation();
            currentLevel.addObject(new Apple(randomLocation));
            applesToCreate--;
        }
    }
	
	public void turn() {
        turnPlayers();

        handleTurnActions();

        createApplesOnField();
	}

    private void turnPlayers() {
        for (Player player : players.stream().filter(Player::isAlive).toArray(Player[]::new)) {
            player.playerTurn(this);
            for (ICollider obj : getCollisions(player.getSnake().getNextSnakeLocation(), player.getSnake().getHead()))
                obj.collisionAction(this, player);
            player.getSnake().moveSnake();
        }
    }

    private void handleTurnActions() {
        for (GameObject obj : getLevel().getObjects())
            if (obj instanceof ITurnActioner) {
                ITurnActioner actioner = (ITurnActioner) obj;
                actioner.turnAction(this);
            }
    }

    public List<ICollider> getCollisions(SnakePart target) {
        return getCollisions(target.getLocation(), target);
	}

	public List<ICollider> getCollisions(Location location, SnakePart target){
        List<ICollider> result = new ArrayList<>();
        result.addAll(getLevel().getObjects(location));

        for (Player player : players) {
            for (SnakePart snakePart : player.getSnake())
                if (snakePart.getLocation().equals(location) && !snakePart.equals(target))
                    result.add(player.getSnake());
        }
        return result;
    }

	public List<Player> getPlayers() {
		return players;
	}
	
	public boolean isEnded() {
		int alivePlayers = 0;
		for (Player player : players)
			if (player.isAlive())
				alivePlayers++;
		return alivePlayers < 2;
	}
	
	public Set<Location> getNonEmptyLocations() {
		Set<Location> result = new HashSet<>();
		for (GameObject obj : getLevel())
			result.add(obj.getLocation());
		for (Player player : players)
			for (SnakePart part : player.getSnake())
				result.add(part.getLocation());
		return result;
	}
	
	public Location getRandomEmptyLocation() {
		Set<Location> nonEmpty = getNonEmptyLocations();
		Random rng = new Random();
		Location currentLocation;
		while (true) {
			currentLocation = new Location(rng.nextInt(getLevel().width), rng.nextInt(getLevel().height));
			if (!nonEmpty.contains(currentLocation))
				break;
		}
		return currentLocation;
	}
}
