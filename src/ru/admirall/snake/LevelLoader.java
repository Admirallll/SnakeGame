package ru.admirall.snake;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LevelLoader {
	public static LevelInfo loadLevelFromFile(String filename) {
		LevelInfo levelInfo = new LevelInfo();
		String line = null;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			while((line = reader.readLine()) != null){
			    parseLine(levelInfo, line);
			}
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {

		}
		return levelInfo;
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
}
