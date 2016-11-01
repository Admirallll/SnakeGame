package ru.admirall.snake;

import java.util.ArrayList;
import java.util.Random;

public class BotController implements IPlayerController {
	@Override
	public void controlSnake(SnakeGame game, Player player) {
	    Direction current = player.getSnake().getCurrentDirection();
		ArrayList<Direction> freeDirections = new ArrayList<>();
		for (Direction dir : Direction.values())
		{
			if (!dir.isOpposingDirections(current)) {
			    Location location = player.getSnake().getNextSnakeLocation(dir.directionToLocation());
                if (game.getCollisions(location, player.getSnake().getHead()).stream().filter(x -> x instanceof ICollisionKiller).count() == 0)
                    freeDirections.add(dir);
            }
		}
		Random random = new Random();
		if (freeDirections.size() > 0){
		    if (!freeDirections.contains(current) || random.nextInt(30) < 10)
                player.getSnake().setDirection(freeDirections.get(random.nextInt(freeDirections.size())));
        }

	}
}
