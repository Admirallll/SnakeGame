package ru.admirall.snake;

public class Object {
	private Location location;
	private ObjectType type;
	
	public Object(ObjectType type) {
		this.type = type;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public ObjectType getType() {
		return type;
	}
}
