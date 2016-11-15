package ru.admirall.snake.levels;

import ru.admirall.snake.primitives.Location;
import ru.admirall.snake.gameobjects.GameObject;
import ru.admirall.snake.players.Player;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Level implements Iterable<GameObject>, Serializable {
    public final int width;
    public final int height;
    private List<GameObject> objects;
    private int playersPoints;

    public Level(LevelInfo levelInfo, List<Player> players){
        this.width = levelInfo.width;
        this.height = levelInfo.height;
        objects = new ArrayList<>(levelInfo.objects);
        for (int i = 0; i < players.size(); i++)
        	players.get(i).createSnake(levelInfo.snakeStarts.get(i));
    }
    
    public int getPlayersPoints() {
    	return playersPoints;
    }
    
    public void addPlayersPoints(int bonusPoints) {
    	playersPoints += bonusPoints;
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public void deleteObject(GameObject obj) {
        objects.remove(obj);
    }

    public List<GameObject> getObjects(Location location){
        return objects.stream()
                .filter(obj -> obj.getLocation().equals(location))
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<GameObject> iterator() {
        return objects.iterator();
    }
}
