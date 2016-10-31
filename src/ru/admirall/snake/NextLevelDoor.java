package ru.admirall.snake;

public class NextLevelDoor extends GameObject {

	private int nextLevel;
	private boolean isOpened = false;
	private int openTreshold;
	
	public NextLevelDoor(Location location) {
		super(location);
	}

	@Override
	public void collisionAction(SnakeGame game, Player player) {
		if (isOpened)
			game.changeLevel(nextLevel);
		else
			player.setAlive(false);
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
	public boolean tryOpen(int points) {
		if (points >= openTreshold)
			isOpened = true;
		return isOpened;
	}
	
	public boolean isOpened() {
		return isOpened;
	}
}
