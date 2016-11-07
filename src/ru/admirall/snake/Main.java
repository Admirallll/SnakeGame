package ru.admirall.snake;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum ApplicationMode{
    Client, Server
}

public class Main {
    private static final int port = 45654;

    public static void main(String [] args) throws IOException {

        // TODO: окошко с настройками режима игры

        ApplicationMode mode = args.length > 0 ? ApplicationMode.Client : ApplicationMode.Server;

        IGame game;
        List<Player> players = null;

        if (mode == ApplicationMode.Server) {
            LevelInfo[] levels = findLevelFiles("levels").stream()
                    .map(LevelLoader::loadLevelFromFile)
                    .toArray(LevelInfo[]::new);
            players = createPlayers();
            game = new SnakeGame(levels, players);
            new Thread(new GameServer(port, (SnakeGame)game)).start();
        } else {
            game = new GameClient(args[0], Integer.parseInt(args[1]));
        }

        JFrame frame = new GameWindow(game);
        if (mode == ApplicationMode.Server)
            frame.addKeyListener(createKeyListener(players));
        frame.setVisible(true);
    }

    private static List<String> findLevelFiles(String directory){
        List<String> files = new ArrayList<>();
        File f = new File(directory);
        for (String file : f.list()) {
            if (file.startsWith("level")) {
                files.add(directory + "/" + file);
            }
        }
        return files;
    }

    private static ArrayList<Player> createPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player(new KeyboardPlayerController(getPlayer1Keys()));
        Player player2 = new Player(new KeyboardPlayerController(getPlayer2Keys()));
        Player botPlayer1 = new Player(new BotController());
        players.add(player1);
        players.add(botPlayer1);
        players.add(player2);
        return players;
    }

    private static KeyListener createKeyListener(List<Player> players){
        KeyListener keyListener = new KeyListener();
        for (Player player : players){
            IPlayerController controller = player.getController();
            if (controller instanceof KeyboardPlayerController){
                keyListener.addListener((KeyboardPlayerController)controller);
            }
        }
        return keyListener;
    }

    private static Map<Integer, ControllerAction> getPlayer1Keys() {
        Map<Integer, ControllerAction> keys = new HashMap<>();
        keys.put(KeyEvent.VK_W, (game, player) -> player.getSnake().setDirection(Direction.North));
        keys.put(KeyEvent.VK_S, (game, player) -> player.getSnake().setDirection(Direction.South));
        keys.put(KeyEvent.VK_A, (game, player) -> player.getSnake().setDirection(Direction.West));
        keys.put(KeyEvent.VK_D,(game, player) ->  player.getSnake().setDirection(Direction.East));
        keys.put(KeyEvent.VK_SPACE, SnakeGame::changePlayerColor);
        return keys;
    }

    private static Map<Integer, ControllerAction> getPlayer2Keys() {
        Map<Integer, ControllerAction> keys = new HashMap<>();
        keys.put(KeyEvent.VK_UP, (game, player) -> player.getSnake().setDirection(Direction.North));
        keys.put(KeyEvent.VK_DOWN, (game, player) -> player.getSnake().setDirection(Direction.South));
        keys.put(KeyEvent.VK_LEFT, (game, player) -> player.getSnake().setDirection(Direction.West));
        keys.put(KeyEvent.VK_RIGHT,(game, player) ->  player.getSnake().setDirection(Direction.East));
        keys.put(KeyEvent.VK_ENTER, SnakeGame::changePlayerColor);
        return keys;
    }
}
