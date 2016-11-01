package ru.admirall.snake;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SnakeGame {

	private boolean isEnded;
	private int applesToCreate;
	private ArrayList<Color> colors;
	private ArrayList<Player> players;
	private LevelInfo[] levels;
	private Level currentLevel;
	
	public SnakeGame(LevelInfo[] levels, ArrayList<Player> players) {
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
			for (Player player : players.stream().filter((p) -> p.isAlive()).toArray(Player[]::new)) 
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
		ArrayList<Color> colors = new ArrayList<Color>();
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
	
	public void createAppleOnField() {
		Location randomLocation = getRandomEmptyLocation();
		currentLevel.addObject(new Apple(randomLocation));
	}
	
	public void gameTurn() {
		for (Player player : players) {
			player.playerTurn(this);
			if (!player.isAlive())
				continue;
			for (ICollider obj : getCollisions(player.getSnake().getNextSnakeLocation(), player.getSnake().getHead()))
				obj.collisionAction(this, player);
			player.getSnake().moveSnake();
		}
		
		for (GameObject obj : getLevel().getObjects())
			if (obj instanceof ITurnActioner) {
				ITurnActioner actioner = (ITurnActioner) obj;
				actioner.turnAction(this);
			}
		
		while (applesToCreate > 0) {
			createAppleOnField();
			applesToCreate--;
		}
		isEnded = isGameEnded();
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
	
	private boolean isGameEnded() {
		int alivePlayers = 0;
		for (Player player : players)
			if (player.isAlive())
				alivePlayers++;
		return alivePlayers < 2;
	}

	public void endGame() {
		isEnded = true;
	}
	
	public boolean isEnded() {
		return isEnded;
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
