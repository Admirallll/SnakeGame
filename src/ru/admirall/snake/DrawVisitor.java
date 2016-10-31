package ru.admirall.snake;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class DrawVisitor implements IVisitor {
	private int textureSize = 32;
	private Graphics graphics;
	
	public void setGraphics(Graphics g) {
		graphics = g;
	}
	
	public Graphics getGraphics() {
		return graphics;
	}
	
	@Override
	public void visit(Apple obj) {
		drawTexture("apple.png", obj.getLocation());
	}

	@Override
	public void visit(Wall obj) {
		drawTexture("wall.png", obj.getLocation());
	}

	@Override
	public void visit(Snake snake) {
		for (SnakePart part : snake){
			Color oldColor = graphics.getColor();
			//drawTexture("snake.jpg", part.getLocation());
			graphics.setColor(snake.getColor());
			graphics.fillRect(part.getLocation().getX() * textureSize, part.getLocation().getY() * textureSize, textureSize, textureSize);	
			graphics.setColor(oldColor);
		}
	}
	
	private void drawTexture(String filename, int x, int y) {
        String folder = "pics\\";
        graphics.drawImage(new ImageIcon(folder + filename).getImage(), x * textureSize, y * textureSize, null);
    }
	
	private void drawTexture(String filename, Location loc) {
		drawTexture(filename, loc.getX(), loc.getY());
	}
}
