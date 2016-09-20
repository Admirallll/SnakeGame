package ru.admirall.snake;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
	North,
	South,
	West,
	East;
	
	public Offset directionToOffset() {
		Map<Direction,Offset> dirToOffset = new HashMap<Direction, Offset>() {{
			put(Direction.North, new Offset(0, 1));
			put(Direction.South, new Offset(0, -1));
			put(Direction.East, new Offset(1, 0));
			put(Direction.West, new Offset(-1, 0));
		}};
		return dirToOffset.get(this);
	}
}
