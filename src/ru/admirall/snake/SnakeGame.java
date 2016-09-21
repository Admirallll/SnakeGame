package ru.admirall.snake;

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
	
	public SnakeGame() {
		this.width = width;
		this.height = height;
	}
	
	public void gameTurn() {
		Set<Location> notEmptyLocations = getNonEmptyLocations();
		Location randomLocation = getRandomEmptyLocation(notEmptyLocations);
		
	}
	
	public Set<Location> getNonEmptyLocations() {
		Set<Location> result = new HashSet<Location>();
		for (Object obj : objects)
			result.add(obj.getLocation());
		for (SnakePart part : snake)
			result.add(snake.getLocation().offsetLocation(part.getLocalOffset()));
		return result;
	}
	
	public Location getRandomEmptyLocation(Set<Location> nonEmpty) {
		Random rng = new Random();
		Location currentLocation;
		while (true) {
			currentLocation = new Location(rng.nextInt() % width, rng.nextInt() % height);
			if (!nonEmpty.contains(currentLocation))
				break;
		}
		return currentLocation;
	}
	
	public boolean isLocationOnMap(Location location) {
		return location.getX() >= 0 && location.getX() < width && location.getY() >= 0 && location.getY() < height;
	}

}
