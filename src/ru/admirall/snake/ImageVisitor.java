package ru.admirall.snake;

public class ImageVisitor implements IVisitor {

	@Override
	public String visit(Apple obj) {
		return "apple.png";
	}

	@Override
	public String visit(Wall obj) {
		return "wall.png";
	}

	@Override
	public String visit(Snake obj) {
		return "snake.jpg";
	}

}
