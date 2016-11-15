package ru.admirall.snake;

import ru.admirall.snake.players.Player;

public interface ControllerAction {
	void action(SnakeGame game, Player player);
}
