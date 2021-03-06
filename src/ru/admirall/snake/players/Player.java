package ru.admirall.snake.players;

import ru.admirall.snake.IVisitor;
import ru.admirall.snake.primitives.Location;
import ru.admirall.snake.SnakeGame;
import ru.admirall.snake.snake.Snake;

import java.awt.Color;
import java.io.Serializable;

public class Player implements Serializable {
	private Snake snake;
	private int points;
	transient private IPlayerController controller;
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
		if (isAlive)
			visitor.visit(this);
	}
	
	public void setColor(Color newColor) {
		color = newColor;
	}
	
	public void createSnake(Location snakeLocation) {
		snake = new Snake(snakeLocation);
	}
	
	public void addPoints(int pointsChange) {
		points += pointsChange;
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
	
	public int getPoints() {
		return points;
	}
	
	public IPlayerController getController() {
		return controller;
	}
	
}
