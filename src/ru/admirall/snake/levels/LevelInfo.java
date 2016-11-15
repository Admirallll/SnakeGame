package ru.admirall.snake.levels;


import ru.admirall.snake.primitives.Location;
import ru.admirall.snake.gameobjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class LevelInfo {
    public int width;
    public int height;
    public ArrayList<Location> snakeStarts;
    public List<GameObject> objects;
    public boolean borders;

    public LevelInfo() {
        objects = new ArrayList<>();
        snakeStarts = new ArrayList<>();
    }
}
