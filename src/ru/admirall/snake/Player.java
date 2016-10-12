package ru.admirall.snake;

public class Player {
	private Snake snake;
	private int score;
	private IPlayerController controller;
	private boolean isAlive = true;
	
	public Player(IPlayerController controller, Location snakeLocation) {
		this.controller = controller;
		snake = new Snake(snakeLocation);
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
	
	public void playerTurn() {
		controller.updateSnakeDirectionInController(snake.getCurrentDirection());
		snake.moveSnake();	
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public PlayerController getController() {
		return (PlayerController) controller;
	}
	
}
