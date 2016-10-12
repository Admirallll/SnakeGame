package ru.admirall.snake;


import java.util.Iterator;

public class Snake implements Iterable<SnakePart> {
	
	private SnakePart head;
	private SnakePart tail;
	private Direction currentDirection;
	
	public Snake(Location headLocation) {
		head = tail = new SnakePart(headLocation);
		currentDirection = Direction.East;
	}
	
	public Direction getCurrentDirection() {
		return currentDirection;
	}
	
	public SnakePart getTail() {
		return tail;
	}
	
	public void collisionAction(SnakeGame game, Player player) {
		player.setAlive(false);
	}
	
	public void setDirection(Direction direction) {
		if (direction != null)
			currentDirection = direction;
	}
	
	public void moveSnake() {
		for (SnakePart currentPart : this) {
			if (currentPart != head)
				currentPart.setLocation(currentPart.getNextPart().getLocation());
		}
		head.setLocation(head.getLocation().offsetLocation(currentDirection.directionToLocation()));
	}
	
	public String visit(IVisitor visitor) {
		return visitor.visit(this);
	}
	
	public SnakePart getHead() {
		return head;
	}
	
	public void addPart() {
		SnakePart newPart = new SnakePart(new Location(0, 0));
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
