package ru.admirall.snake.snake;


import ru.admirall.snake.primitives.Direction;
import ru.admirall.snake.primitives.Location;
import ru.admirall.snake.players.Player;
import ru.admirall.snake.SnakeGame;
import ru.admirall.snake.gameobjects.ICollider;
import ru.admirall.snake.gameobjects.ICollisionKiller;

import java.io.Serializable;
import java.util.Iterator;

public class Snake implements Iterable<SnakePart>, ICollider, ICollisionKiller, Serializable {
	
	private Direction prevDirection;
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
	
	public Location getNextSnakeLocation(Location location) {
		return getLocation().offsetLocation(location);
	}
	
	public Location getNextSnakeLocation() {
		return getNextSnakeLocation(currentDirection.directionToLocation());
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
