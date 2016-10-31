package ru.admirall.snake;


public abstract class GameObject implements ICollider {
	private Location location;
	
	public GameObject(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public abstract void collisionAction(SnakeGame game, Player player);
	
	public abstract void visit(IVisitor visitor);
}
