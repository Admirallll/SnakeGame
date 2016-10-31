package ru.admirall.snake;

import java.awt.Color;

public class Player {
	private Snake snake;
	private int score;
	private IPlayerController controller;
	private boolean isAlive = true;
	private Color color;
	
	public Player(IPlayerController controller) {
		this.controller = controller;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
	public void setColor(Color newColor) {
		color = newColor;
	}
	
	public void createSnake(Location snakeLocation) {
		snake = new Snake(snakeLocation);
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
