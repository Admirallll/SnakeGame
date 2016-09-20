package ru.admirall.snake;

public class Player {
	private Snake snake;
	private int score;
	
	public Player(int snakeX, int snakeY) {
		snake = new Snake(snakeX, snakeY);
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public void AddPoints(int pointsChange) {
		score += pointsChange;
	}
}