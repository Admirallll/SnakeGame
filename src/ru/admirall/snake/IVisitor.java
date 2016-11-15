package ru.admirall.snake;

import ru.admirall.snake.gameobjects.Apple;
import ru.admirall.snake.gameobjects.NextLevelDoor;
import ru.admirall.snake.gameobjects.Wall;
import ru.admirall.snake.players.Player;

public interface IVisitor {
	public void visit(Apple obj);
	public void visit(Wall obj);
	public void visit(Player player);
	public void visit(NextLevelDoor nextLevelDoor);
}
