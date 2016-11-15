package ru.admirall.snake.gameobjects;

import ru.admirall.snake.players.Player;
import ru.admirall.snake.SnakeGame;

public interface ICollider {
	void collisionAction(SnakeGame game, Player player);
}
