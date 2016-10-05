package ru.admirall.snake;

public class Wall extends Object {

	public Wall(Location location) {
		super(location);
	}

	public void collisionAction(SnakeGame game) {
		game.endGame();
	}
	
	public String visit(IVisitor imageVisitor) {
		return imageVisitor.visit(this);
	}
}
