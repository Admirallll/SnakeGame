package ru.admirall.snake;

import java.awt.Color;
import java.util.Map;
import java.util.Stack;

public class PlayerController implements IPlayerController {
	private Map<Integer, Direction> keyToDirection;
	private Stack<Direction> directions = new Stack<Direction>();
	
	public PlayerController(Map<Integer, Direction> keys) {
		this.keyToDirection = keys;
	}
	
	public void listenKey(int keyCode) {
		if (keyToDirection != null && keyToDirection.containsKey(keyCode))
			directions.push(keyToDirection.get(keyCode));
	}

	public void controlSnake(SnakeGame game, Player player) {
		Direction direction = null;
		while (!directions.isEmpty()) {
			direction = directions.pop();
			if (!direction.isOpposingDirections(player.getSnake().getCurrentDirection()))
				break;
		}
		if (direction != null)
			player.getSnake().setDirection(direction);
		directions = new Stack<Direction>();
	}
	
	public void changeSnakeColor(SnakeGame game, Snake snake) {
		for (Player player : game.getPlayers())
			for (Color color : game.getColors())
				if (color != player.getSnake().getColor())
					snake.setColor(color);
	}
	
	
}
