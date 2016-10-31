package ru.admirall.snake;

import java.util.ArrayList;
import java.util.Random;

public class BotController implements IPlayerController {
	@Override
	public void controlSnake(SnakeGame game, Player player) {
		ArrayList<Direction> freeDirections = new ArrayList<Direction>();
		for (Direction dir : Direction.values())
		{
			if (!dir.isOpposingDirections(player.getSnake().getCurrentDirection()))
				if (game.checkNextSnakeLocation(player, dir).size() == 0)
					freeDirections.add(dir);
		}
		Random rng = new Random();
		if (freeDirections.size() > 0)
			player.getSnake().setDirection(freeDirections.get(rng.nextInt(freeDirections.size())));
	}
}
