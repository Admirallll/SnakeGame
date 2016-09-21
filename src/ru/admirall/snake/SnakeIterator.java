package ru.admirall.snake;

import java.util.Iterator;

public class SnakeIterator implements Iterator<SnakePart> {

	private SnakePart currentPart;
	
	public SnakeIterator(Snake snake) {
		currentPart = snake.getTail();
	}
	
	@Override
	public boolean hasNext() {
		return currentPart != null;
	}

	@Override
	public SnakePart next() {
		SnakePart part = currentPart;
		currentPart = currentPart.getNextPart();
		return part;
	}
	
}
