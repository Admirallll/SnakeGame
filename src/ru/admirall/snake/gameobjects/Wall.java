package ru.admirall.snake.gameobjects;

import ru.admirall.snake.*;
import ru.admirall.snake.players.Player;
import ru.admirall.snake.primitives.Location;

public class Wall extends GameObject implements ICollisionKiller {

	public Wall(Location location) {
		super(location);
	}

	public void collisionAction(SnakeGame game, Player player) {
		player.setAlive(false);
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
