package ru.admirall.snake.gameobjects;

import ru.admirall.snake.IVisitor;
import ru.admirall.snake.primitives.Location;
import ru.admirall.snake.players.Player;
import ru.admirall.snake.SnakeGame;

public class Apple extends GameObject {

	public Apple(Location location) {
		super(location);
	}
	
	public void collisionAction(SnakeGame game, Player player) {
		game.getLevel().deleteObject(this);
		player.addPoints(1);
		game.getLevel().addPlayersPoints(1);
		game.addApplesToCreate(1);
		player.getSnake().addPart();	
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
