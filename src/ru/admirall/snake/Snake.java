package ru.admirall.snake;


import java.awt.Color;
import java.util.Iterator;

public class Snake implements Iterable<SnakePart>, ICollider {
	
	
	private Direction prevDirection;
	private SnakePart head;
	private SnakePart tail;
	private Direction currentDirection;
	private Color color;
	
	public Snake(Location headLocation, Color snakeColor) {
		head = tail = new SnakePart(headLocation);
		currentDirection = Direction.East;
		color = snakeColor;
	}
	
	public void setColor(Color newColor) {
		color = newColor;
	}
	
	public Color getColor() {
		return color;
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
		if (direction != null && !direction.isOpposingDirections(prevDirection))
			currentDirection = direction;
	}
	
	public void moveSnake() {
		prevDirection = currentDirection;
		for (SnakePart currentPart : this) {
			if (currentPart != head)
				currentPart.setLocation(currentPart.getNextPart().getLocation());
		}
		head.setLocation(head.getLocation().offsetLocation(currentDirection.directionToLocation()));
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
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
