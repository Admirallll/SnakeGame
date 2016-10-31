package ru.admirall.snake;

import java.awt.Color;

public class Player {
	private Snake snake;
	private int score;
	private IPlayerController controller;
	private boolean isAlive = true;
	
	public Player(IPlayerController controller, Location snakeLocation, Color snakeColor) {
		this.controller = controller;
		snake = new Snake(snakeLocation, snakeColor);
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public void addPoints(int pointsChange) {
		score += pointsChange;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public void playerTurn(SnakeGame game) {
		controller.controlSnake(game, this);
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public KeyboardPlayerController getController() {
		return (KeyboardPlayerController) controller;
	}
	
}
