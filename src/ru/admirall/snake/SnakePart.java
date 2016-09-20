package ru.admirall.snake;

public class SnakePart {
	
	private Offset localOffset;
	private SnakePart nextPart;
	private SnakePart prevPart;
	
	public SnakePart(Offset localOffset) {
		this.localOffset = localOffset;
	}
	
	public SnakePart getNextPart() {
		return nextPart;
	}
	
	public SnakePart getPrevPart() {
		return prevPart;
	}
	
	public Offset getLocalOffset() {
		return localOffset;
	}
	
	public void setLocalOffset(Offset newOffset) {
		localOffset = newOffset;
	}
	
	public void setNextPart(SnakePart nextPart) {
		this.nextPart = nextPart;
	}
	
	public void setPrevPart(SnakePart prevPart) {
		this.prevPart = prevPart;
	}
}
