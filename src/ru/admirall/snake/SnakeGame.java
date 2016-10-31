package ru.admirall.snake;


import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SnakeGame {

	private boolean isEnded;
	private int applesToCreate;
	private List<Color> colors;
	private List<Player> players;
	private Level[] levels;
	private int currentLevelIndex;
	
	public SnakeGame(Level[] levels, List<Player> players) {
		this.levels = levels;
        this.players = players;
        colors = createColors();
	}
	
	public void changeSnakeColor(Snake snake) {
		for (Player player : getPlayers())
			for (Color color : getColors())
				if (color != player.getSnake().getColor())
					snake.setColor(color);
	}
	
	public ArrayList<Color> createColors() {
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.BLUE);
		colors.add(Color.CYAN);
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		return colors;
	}
	
	public List<Color> getColors() {
		return colors;
	}

	public Level getLevel(){
	    return levels[currentLevelIndex];
    }

    public void setCurrentLevelIndex(int index){
        currentLevelIndex = index;
    }
	
	public void addApplesToCreate(int apples) {
		applesToCreate += apples;
	}
	
	public void createAppleOnField() {
		Location randomLocation = getRandomEmptyLocation();
		getLevel().addObject(new Apple(randomLocation));
	}
	
	public void gameTurn() {
		for (Player player : players) {
			player.playerTurn(this);
			if (!player.isAlive())
				continue;
			for (ICollider obj : getCollisions(player.getSnake().getHead()))
				obj.collisionAction(this, player);
			player.getSnake().moveSnake();
		}
		
		isEnded = isGameEnded();
		
		while (applesToCreate > 0) {
			createAppleOnField();
			applesToCreate--;
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
