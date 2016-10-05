package ru.admirall.snake;

public class Apple extends Object {

	public Apple(Location location) {
		super(location);
	}
	
	public void collisionAction(SnakeGame game) {
		game.addScore(5);
		game.deleteObject(this);
		Location randomLocation = game.getRandomEmptyLocation();
		game.addObject(new Apple(randomLocation));
		game.getSnake().addPart();	
	}
	
	public String visit(IVisitor imageVisitor) {
		return imageVisitor.visit(this);
	}
}
