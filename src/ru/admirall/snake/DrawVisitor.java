package ru.admirall.snake;

import java.awt.*;

import javax.swing.ImageIcon;

public class DrawVisitor implements IVisitor {
	private int textureSize = 32;
	private Graphics graphics;
	
	public void setGraphics(Graphics g) {
		graphics = g;
        ((Graphics2D)graphics).setStroke(new BasicStroke(10));
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
	public void visit(Player player) {
		for (SnakePart part : player.getSnake()){
			Color oldColor = graphics.getColor();
			graphics.setColor(player.getColor());
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

	@Override
	public void visit(NextLevelDoor door) {
		Color oldColor = graphics.getColor();
        graphics.setColor(new Color(102, 51, 0));
		if (door.isOpened())
		    drawCell(door.getLocation());
		else
			fillCell(door.getLocation());
		graphics.setColor(oldColor);
	}

	private void fillCell(Location location){
		graphics.fillRect(location.getX() * textureSize, location.getY() * textureSize, textureSize, textureSize);
	}

	private void drawCell(Location location){
		graphics.drawRect(location.getX() * textureSize, location.getY() * textureSize, textureSize, textureSize);
	}
}
