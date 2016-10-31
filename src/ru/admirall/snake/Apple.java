package ru.admirall.snake;

public class Apple extends GameObject {

	public Apple(Location location) {
		super(location);
	}
	
	public void collisionAction(SnakeGame game, Player player) {
		game.deleteObject(this);
		game.addApplesToCreate(1);
		player.getSnake().addPart();	
	}
	
	public void visit(IVisitor visitor) {
		visitor.visit(this);
	}
}
