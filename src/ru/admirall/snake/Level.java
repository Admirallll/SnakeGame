package ru.admirall.snake;

import java.util.*;
import java.util.stream.Collectors;

public class Level implements Iterable<GameObject> {
    public final int width;
    public final int height;
    private List<GameObject> objects;

    public Level(int width, int height, List<GameObject> objects){
        this.width = width;
        this.height = height;
        this.objects = objects;
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
