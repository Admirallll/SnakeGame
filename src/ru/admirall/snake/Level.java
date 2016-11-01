package ru.admirall.snake;

import java.util.*;
import java.util.stream.Collectors;

public class Level implements Iterable<GameObject> {
    public final int width;
    public final int height;
    private List<GameObject> objects;
    private int playersPoints;

    public Level(LevelInfo levelInfo, ArrayList<Player> players){
        this.width = levelInfo.width;
        this.height = levelInfo.height;
        objects = new ArrayList<>(levelInfo.objects);
        for (int i = 0; i < players.size(); i++)
        	players.get(i).createSnake(levelInfo.snakeStarts.get(i));
        if (levelInfo.borders)
        	placeBorders(levelInfo.width, levelInfo.height);
    }
    
    public int getPlayersPoints() {
    	return playersPoints;
    }
    
    public void addPlayersPoints(int bonusPoints) {
    	playersPoints += bonusPoints;
    }
    
	private void placeBorders(int width, int height) {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (x == 0 || y == 0 || x == width-1 || y == height-1)
					objects.add(new Wall(new Location(x, y)));
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
