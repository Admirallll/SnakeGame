package ru.admirall.snake;


import java.io.Serializable;

public abstract class GameObject implements ICollider, Serializable {
	private Location location;
	
	public GameObject(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public abstract void collisionAction(SnakeGame game, Player player);
	
	public abstract void accept(IVisitor visitor);
}
