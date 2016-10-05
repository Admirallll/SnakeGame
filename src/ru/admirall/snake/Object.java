package ru.admirall.snake;


public abstract class Object {
	private Location location;
	
	public Object(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public abstract void collisionAction(SnakeGame game);
	
	public abstract String visit(IVisitor imageVisitor);
}
