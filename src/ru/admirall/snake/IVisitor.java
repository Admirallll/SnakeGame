package ru.admirall.snake;

public interface IVisitor {
	public void visit(Apple obj);
	public void visit(Wall obj);
	public void visit(Player player);
	public void visit(NextLevelDoor nextLevelDoor);
}
