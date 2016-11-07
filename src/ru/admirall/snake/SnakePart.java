package ru.admirall.snake;


import java.io.Serializable;

public class SnakePart implements Serializable{
	
	private Location location;
	private SnakePart nextPart;
	private SnakePart prevPart;
	
	public SnakePart(Location location) {
		this.location = location;
	}
	
	public SnakePart getNextPart() {
		return nextPart;
	}
	
	public SnakePart getPrevPart() {
		return prevPart;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setNextPart(SnakePart nextPart) {
		this.nextPart = nextPart;
	}
	
	public void setPrevPart(SnakePart prevPart) {
		this.prevPart = prevPart;
	}
}
