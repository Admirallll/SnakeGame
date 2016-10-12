package ru.admirall.snake;


public abstract class GameObject {
	private Location location;
	
	public GameObject(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public abstract void collisionAction(SnakeGame game, Player player);
	
	public abstract String visit(IVisitor visitor);
}
