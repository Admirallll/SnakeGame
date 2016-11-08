package ru.admirall.snake;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

enum ApplicationMode{
    Client, Server
}

public class Main {
    private static final int port = 45654;

    public static void main(String [] args) throws IOException, InterruptedException {

        // TODO: окошко с настройками режима игры

        ApplicationMode mode = args.length > 0 ? ApplicationMode.Client : ApplicationMode.Server;

        IGame game;
        List<Player> localPlayers = null;

        if (mode == ApplicationMode.Server) {
            WaitingRoom waitingRoom = new WaitingRoom(port);

            Thread waitingRoomThread = new Thread(waitingRoom);
            waitingRoomThread.start();
            // FIXME сделать кнопку
            Thread.sleep(5000);
            waitingRoom.stop();
            waitingRoomThread.join();


            LevelInfo[] levels = loadLevels();
            localPlayers = createLocalPlayers();
            List<Player> remotePlayers = createRemotePlayers(waitingRoom.getConnections());

            ArrayList<Player> players = new ArrayList<>(localPlayers);
            players.addAll(remotePlayers);

            game = new SnakeGame(levels, players);
            new GameServer((SnakeGame)game).start();
        } else {
            // FIXME данные из интерфейса
            game = new GameClient(args[0], Integer.parseInt(args[1]));
            // FIXME передача нажатий кнопок
        }

        JFrame frame = new GameWindow(game);
        if (mode == ApplicationMode.Server)
            frame.addKeyListener(createKeyListener(localPlayers));
        frame.setVisible(true);
    }

    private static LevelInfo[] loadLevels() {
        return findLevelFiles("levels").stream()
                .map(LevelLoader::loadLevelFromFile)
                .toArray(LevelInfo[]::new);
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

    private static ArrayList<Player> createLocalPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player(new KeyboardPlayerController(getPlayer1Keys()));
        Player player2 = new Player(new KeyboardPlayerController(getPlayer2Keys()));
        Player botPlayer1 = new Player(new BotController());
        players.add(player1);
        players.add(botPlayer1);
        players.add(player2);
        return players;
    }

    private static List<Player> createRemotePlayers(List<GameConnection> connections) {
        return connections.stream()
                .map(RemotePlayer::new)
                .collect(Collectors.toList());
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

    private static Map<Integer, ActionCode> getPlayer1Keys() {
        Map<Integer, ActionCode> keys = new HashMap<>();
        keys.put(KeyEvent.VK_W, ActionCode.TurnUp);
        keys.put(KeyEvent.VK_S, ActionCode.TurnDown);
        keys.put(KeyEvent.VK_A, ActionCode.TurnLeft);
        keys.put(KeyEvent.VK_D, ActionCode.TurnRight);
        keys.put(KeyEvent.VK_SPACE, ActionCode.ChangeColor);
        return keys;
    }

    private static Map<Integer, ActionCode> getPlayer2Keys() {
        Map<Integer, ActionCode> keys = new HashMap<>();
        keys.put(KeyEvent.VK_UP, ActionCode.TurnUp);
        keys.put(KeyEvent.VK_DOWN, ActionCode.TurnDown);
        keys.put(KeyEvent.VK_LEFT, ActionCode.TurnLeft);
        keys.put(KeyEvent.VK_RIGHT, ActionCode.TurnRight);
        keys.put(KeyEvent.VK_ENTER, ActionCode.ChangeColor);
        return keys;
    }
}
