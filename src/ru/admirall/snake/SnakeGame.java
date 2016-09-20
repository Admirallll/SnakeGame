package ru.admirall.snake;

import java.util.List;

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
	
	public boolean isLocationOnMap(Location location) {
		return location.getX() >= 0 && location.getX() < width && location.getY() >= 0 && location.getY() < height;
	}

}
