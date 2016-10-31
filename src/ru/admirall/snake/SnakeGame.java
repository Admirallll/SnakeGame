package ru.admirall.snake;


import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SnakeGame {
	
	private int width;
	private int height;
	private List<GameObject> objects;
	private boolean isEnded;
	private int applesToCreate;
	private ArrayList<Color> colors;
	private ArrayList<Player> players;
	
	public SnakeGame(LevelInfo levelInfo, ArrayList<Player> players) {
		width = levelInfo.width;
        height = levelInfo.height;
        objects = levelInfo.objects;
        if (levelInfo.borders)
        	placeBorders();
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
	
	public ArrayList<Color> getColors() {
		return colors;
	}
	
	public void placeBorders() {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (x == 0 || y == 0 || x == width-1 || y == height-1)
					objects.add(new Wall(new Location(x, y)));
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public List<GameObject> getObjects() {
		return objects;
	}
	
	public void addApplesToCreate(int apples) {
		applesToCreate += apples;
	}
	
	public void createAppleOnField() {
		Location randomLocation = getRandomEmptyLocation();
		addObject(new Apple(randomLocation));
	}
	
	public void gameTurn() {
		for (Player player : players) {
			player.playerTurn(this);
			if (!player.isAlive())
				continue;
			for (ICollider obj : checkNextSnakeLocation(player))
				obj.collisionAction(this, player);
			player.getSnake().moveSnake();
		}
		
		isEnded = checkEndGameCondition();
		
		while (applesToCreate > 0) {
			createAppleOnField();
			applesToCreate--;
		}
	}
	
	public ArrayList<ICollider> checkNextSnakeLocation(Player player, Direction direction) {
		ArrayList<ICollider> res = new ArrayList<ICollider>();
		List<GameObject> objectsForIteration = new ArrayList<GameObject>(objects);
		Location snakeNextLocation = player.getSnake().getLocation().offsetLocation(direction.directionToLocation());
		for (GameObject obj : objectsForIteration) {
			if (obj.getLocation().equals(snakeNextLocation)) {
				res.add(obj);				
			}
		}
		for (Player secondPlayer : players) {
			for (SnakePart snakePart : secondPlayer.getSnake())
				if (snakePart != player.getSnake().getHead() && snakePart.getLocation().equals(player.getSnake().getLocation()))
					res.add(player.getSnake());
		}
		return res;
	}
	
	public ArrayList<ICollider> checkNextSnakeLocation(Player player) {
		return checkNextSnakeLocation(player, player.getSnake().getCurrentDirection());
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public boolean checkEndGameCondition() {
		int alivePlayers = 0;
		for (Player player : players)
			if (player.isAlive())
				alivePlayers++;
		return alivePlayers < 2;
	}
		
	public void addObject(GameObject obj) {
		objects.add(obj);
	}
	
	public void deleteObject(GameObject obj) {
		objects.remove(obj);
	}

	public void endGame() {
		isEnded = true;
	}
	
	public boolean isEnded() {
		return isEnded;
	}
	
	public Set<Location> getNonEmptyLocations() {
		Set<Location> result = new HashSet<Location>();
		for (GameObject obj : objects)
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
			currentLocation = new Location(rng.nextInt(width), rng.nextInt(height));
			if (!nonEmpty.contains(currentLocation))
				break;
		}
		return currentLocation;
	}
	
	public boolean isLocationOnMap(Location location) {
		return location.getX() >= 0 && location.getX() < width && location.getY() >= 0 && location.getY() < height;
	}
}
