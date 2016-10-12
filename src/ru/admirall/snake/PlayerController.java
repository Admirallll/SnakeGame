package ru.admirall.snake;

import java.util.Map;

public class PlayerController implements IPlayerController {

	private Direction direction;
	private Map<Integer, Direction> keyToDirection;
	private Direction snakeDirection; 
	
	public PlayerController(Map<Integer, Direction> keys) {
		this.keyToDirection = keys;
	}
	
	public void updateSnakeDirectionInController(Direction direction) {
		snakeDirection = direction;
	}
	
	public void listenKey(int keyCode){
		if (keyToDirection != null && keyToDirection.containsKey(keyCode)) {
			if (!keyToDirection.get(keyCode).isOpposingDirections(snakeDirection))
				direction = keyToDirection.get(keyCode);
		}
	}

	public Direction getDirection() {
		return direction;
	}

}
