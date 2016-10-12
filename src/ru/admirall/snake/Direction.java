package ru.admirall.snake;


import java.util.HashMap;
import java.util.Map;

public enum Direction {
	North,
	South,
	West,
	East;
	
	public Location directionToLocation() {
		Map<Direction,Location> dirToOffset = new HashMap<Direction, Location>() {{
			put(Direction.North, new Location(0, -1));
			put(Direction.South, new Location(0, 1));
			put(Direction.East, new Location(1, 0));
			put(Direction.West, new Location(-1, 0));
		}};
		return dirToOffset.get(this);
	}
	
	public boolean isOpposingDirections(Direction other) {
		return this.reverseDirection() == other;
	}
	
	public Direction reverseDirection() {
		if (this == Direction.North)
			return Direction.South;
		else if (this == Direction.South)
			return Direction.North;
		else if (this == Direction.East)
			return Direction.West;
		else 
			return Direction.East;
	}
}
