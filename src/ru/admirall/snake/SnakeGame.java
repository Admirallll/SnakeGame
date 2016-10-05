package ru.admirall.snake;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SnakeGame {
	
	private int width;
	private int height;
	private Snake snake;
	private int score;
	private List<Object> objects;
	private boolean isEnded;
	private Direction currentDirection;
	
	public SnakeGame(LevelInfo levelInfo) {
		width = levelInfo.width;
        height = levelInfo.height;
        snake = new Snake(levelInfo.snakeStart);
        objects = levelInfo.objects;
        if (levelInfo.borders)
        	placeBorders();
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
	
	public Snake getSnake() {
		return snake;
	}
	
	public List<Object> getObjects() {
		return objects;
	}
	
	public void gameTurn() {
		List<Object> objectsForIteration = new ArrayList<Object>(objects);
		Location snakeNextLocation = snake.getLocation().offsetLocation(currentDirection.directionToLocation());
		
		for (Object obj : objectsForIteration) {
			if (obj.getLocation().equals(snakeNextLocation)) {
				obj.collisionAction(this);
			}	
		}

		snake.moveSnake(currentDirection);
		
		for (SnakePart snakePart : snake) {
			if (snakePart != snake.getHead() && snakePart.getLocation().equals(snake.getHead().getLocation())) {
				isEnded = true;
				return;
			}
		}
	}
		
	public void addObject(Object obj) {
		objects.add(obj);
	}
	
	public void deleteObject(Object obj) {
		objects.remove(obj);
	}
	
	public void addScore(int scores) {
		score += scores;
	}
	
	public void endGame() {
		isEnded = true;
	}
	
	public Direction getCurrentDirection() {
		return currentDirection;
	}
	
	public boolean isEnded() {
		return isEnded;
	}
	
	public Set<Location> getNonEmptyLocations() {
		Set<Location> result = new HashSet<Location>();
		for (Object obj : objects)
			result.add(obj.getLocation());
		for (SnakePart part : snake)
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
	
	public void changeDirection(Direction newDirection) {
		currentDirection = newDirection;
	}
}
