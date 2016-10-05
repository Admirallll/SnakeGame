package ru.admirall.snake;

public interface IVisitor {
	public String visit(Apple obj);
	public String visit(Wall obj);
	public String visit(Snake obj);
}
