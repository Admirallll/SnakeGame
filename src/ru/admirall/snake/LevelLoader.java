package ru.admirall.snake;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class LevelInfo {
    public int width;
    public int height;
    public Location snakeStart;
    public List<GameObject> objects;
    public boolean borders;

    public LevelInfo() {
        objects = new ArrayList<GameObject>();
    }
}

public class LevelLoader {
	public static Level loadLevelFromFile(String filename) {
	    // TODO remove levelInfo?
		LevelInfo levelInfo = new LevelInfo();
		String line;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			while((line = reader.readLine()) != null){
			    parseLine(levelInfo, line);
			}
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {

		}
		if (levelInfo.borders)
			placeBorders(levelInfo);
		return new Level(levelInfo.width, levelInfo.height, levelInfo.objects);
	}
	
	private static void parseLine(LevelInfo info, String line) {
		String name = line.split(":")[0];
		String value = line.split(":")[1];
		
		switch (name) {
			case "width":
				info.width = parseInt(value);
				break;
			case "height":
				info.height = parseInt(value);
				break;
			case "snake":
				info.snakeStart = parseLocation(value);
				break;
			case "apple":
				info.objects.add(new Apple(parseLocation(value)));
				break;
			case "wall":
				info.objects.add(new Wall(parseLocation(value)));
				break;
			case "borders":
				info.borders = parseBoolean(value);
				break;
		}
	}
	
	private static boolean parseBoolean(String string) {
		return Boolean.parseBoolean(string);
	}
	
	private static int parseInt(String string) {
		return Integer.parseInt(string);
	}
	
	private static Location parseLocation(String string) {
		String[] coords = string.split(",");
//		if (coords.length != 2)
//			throw new Exception("Wrong file");
		return new Location(parseInt(coords[0]), parseInt(coords[1]));
	}

	private static void placeBorders(LevelInfo info) {
		for (int x = 0; x < info.width; x++)
			for (int y = 0; y < info.height; y++)
				if (x == 0 || y == 0 || x == info.width-1 || y == info.height-1)
					info.objects.add(new Wall(new Location(x, y)));
	}
}
