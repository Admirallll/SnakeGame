package ru.admirall.snake;


import java.util.ArrayList;
import java.util.List;

public class LevelInfo {
	public int width;
	public int height;
	public Location snakeStart;
	public List<Object> objects;
	public boolean borders;
	
	public LevelInfo() {
		objects = new ArrayList<Object>();
	}
}
