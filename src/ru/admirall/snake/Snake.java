package ru.admirall.snake;

import java.util.List;

public class Snake {
	
	private SnakePart head;
	private SnakePart tail;
	private Location location;
	
	public Snake(int x, int y) {
		head = tail = new SnakePart(new Offset(0, 0));
		location = new Location(x, y);
	}
	
	public void moveSnake(Direction direction, boolean needNewPart) {
		Offset moveOffset = direction.directionToOffset();
		location = location.offsetLocation(moveOffset);
		SnakePart currentPart = tail;
		if (needNewPart) {
			addPart();
		}
		while (currentPart != head) {
			currentPart.setLocalOffset(currentPart.getNextPart().getLocalOffset()
					.summOffset(moveOffset.reverseOffset()));
			currentPart = currentPart.getNextPart();
		}
	}
	
	public void addPart() {
		SnakePart newPart = new SnakePart(new Offset(0, 0));
		newPart.setNextPart(tail);
		tail.setPrevPart(newPart);
		tail = newPart;
	}
	
	public Location getLocation() {
		return location;
	}
}
