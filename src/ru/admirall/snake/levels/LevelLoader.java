package ru.admirall.snake.levels;


import ru.admirall.snake.primitives.Location;
import ru.admirall.snake.gameobjects.Apple;
import ru.admirall.snake.gameobjects.NextLevelDoor;
import ru.admirall.snake.gameobjects.Wall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LevelLoader {
	public static LevelInfo loadLevelFromFile(String filename) {
		LevelInfo levelInfo = new LevelInfo();
		String line;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			while((line = reader.readLine()) != null){
			    parseLine(levelInfo, line);
			}
		} catch (IOException e) {

		}
		if (levelInfo.borders)
		    placeBorders(levelInfo);
		return levelInfo;
	}
	
	private static void parseLine(LevelInfo info, String line) {
		String[] args = line.split(":");
		switch (args[0]) {
			case "width":
				info.width = parseInt(args[1]);
				break;
			case "height":
				info.height = parseInt(args[1]);
				break;
			case "snake":
				info.snakeStarts.add(parseLocation(args[1]));
				break;
			case "apple":
				info.objects.add(new Apple(parseLocation(args[1])));
				break;
			case "wall":
				info.objects.add(new Wall(parseLocation(args[1])));
				break;
			case "borders":
				info.borders = parseBoolean(args[1]);
				break;
			case "door":
				info.objects.add(new NextLevelDoor(parseLocation(args[1]), parseInt(args[2]), parseInt(args[3])));
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
