package ru.admirall.snake;

public interface IPlayerController {
	public Direction getDirection();
	public void updateSnakeDirectionInController(Direction direction);
}
