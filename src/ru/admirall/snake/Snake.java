package ru.admirall.snake;

import java.util.Iterator;
import java.util.List;

public class Snake implements Iterable<SnakePart> {
	
	private SnakePart head;
	private SnakePart tail;
	private Location location;
	
	public Snake(int x, int y) {
		head = tail = new SnakePart(new Offset(0, 0));
		location = new Location(x, y);
	}
	
	public SnakePart getTail() {
		return tail;
	}
	
	public void moveSnake(Direction direction, boolean needNewPart) {
		Offset moveOffset = direction.directionToOffset();
		location = location.offsetLocation(moveOffset);
		if (needNewPart) {
			addPart();
		}
		for (SnakePart currentPart : this) {
			if (currentPart != head)
				currentPart.setLocalOffset(currentPart.getNextPart().getLocalOffset()
						.summOffset(moveOffset.reverseOffset()));
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

	@Override
	public Iterator<SnakePart> iterator() {
		return new SnakeIterator(this);
	}
}
