package ru.admirall.snake;

import java.io.Serializable;
import java.lang.Object;


public class Location implements Serializable {
	
	private int x;
	private int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		Location loc = null;
		if (obj instanceof Location) {
			loc = (Location)obj;
		}
		return x == loc.x && y == loc.y;
	}
	
	@Override
	public int hashCode() {
		return x * 437 + y;
	}
	
	public Location offsetLocation(Location location) {
		return new Location(x + location.getX(), y + location.getY());
	}
	
	public Location reverseLocation(Location location) {
		return new Location(x * -1, y * -1);
	}
}