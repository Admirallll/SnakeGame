package ru.admirall.snake;

public class Wall extends GameObject {

	public Wall(Location location) {
		super(location);
	}

	public void collisionAction(SnakeGame game, Player player) {
		player.setAlive(false);;
	}
	
	public void visit(IVisitor visitor) {
		visitor.visit(this);
	}
}
