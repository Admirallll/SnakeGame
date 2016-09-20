package ru.admirall.snake;

public class Location {
	
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
	
	public Location offsetLocation(Offset offset) {
		return new Location(x + offset.getXOffset(), y + offset.getYOffset());
	}
}