package ru.admirall.snake;

public class Offset {
	
	private int xOffset;
	private int yOffset;
	
	public Offset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}
	
	public Offset reverseOffset() {
		return new Offset(xOffset * -1, yOffset * -1);
	}
	
	public Offset summOffset(Offset otherOffset) {
		return new Offset(xOffset + otherOffset.xOffset, yOffset + otherOffset.yOffset);
	}
}
