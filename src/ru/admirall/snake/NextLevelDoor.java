package ru.admirall.snake;

public class NextLevelDoor extends GameObject implements ITurnActioner, ICollisionKiller {

	private int nextLevel;
	private boolean isOpened = false;
	private int openThreshold;
	
	public NextLevelDoor(Location location, int openThreshold, int nextLevel) {
		super(location);
		this.nextLevel = nextLevel;
		this.openThreshold = openThreshold;
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
		if (points >= openThreshold)
			isOpened = true;
		return isOpened;
	}
	
	public boolean isOpened() {
		return isOpened;
	}

	@Override
	public void turnAction(SnakeGame game) {
		tryOpen(game.getLevel().getPlayersPoints());
	}
}
