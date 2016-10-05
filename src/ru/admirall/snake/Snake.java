package ru.admirall.snake;


import java.util.Iterator;

public class Snake implements Iterable<SnakePart> {
	
	private SnakePart head;
	private SnakePart tail;
	
	public Snake(Location headLocation) {
		head = tail = new SnakePart(headLocation);
	}
	
	public SnakePart getTail() {
		return tail;
	}
	
	public void moveSnake(Direction direction) {
		for (SnakePart currentPart : this) {
			if (currentPart != head)
				currentPart.setLocation(currentPart.getNextPart().getLocation());
		}
		Location moveOffset = direction.directionToLocation();
		head.setLocation(head.getLocation().offsetLocation(moveOffset));
	}
	
	public String visit(IVisitor imageVisitor) {
		return imageVisitor.visit(this);
	}
	
	public SnakePart getHead() {
		return head;
	}
	
	public void addPart() {
		SnakePart newPart = new SnakePart(new Location(0, 0)); // sdfasdf
		newPart.setNextPart(tail);
		tail.setPrevPart(newPart);
		tail = newPart;
	}
	
	public Location getLocation() {
		return head.getLocation();
	}

	@Override
	public Iterator<SnakePart> iterator() {
		return new SnakeIterator(this);
	}
}
