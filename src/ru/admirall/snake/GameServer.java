package ru.admirall.snake;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameServer {
    private SnakeGame game;

    public GameServer(List<GameConnection> connections){
        this.game = createGame(connections);
        start();
    }

    public void start(){
        List<RemotePlayer> remotePlayers = game.getPlayers()
                .stream()
                .filter(player -> player instanceof RemotePlayer)
                .map(player -> (RemotePlayer) player)
                .collect(Collectors.toList());
        for (RemotePlayer remotePlayer : remotePlayers){
            new Thread(()->processClient(remotePlayer)).start();
        }
    }
    
    private SnakeGame createGame(List<GameConnection> connections) {
    	LevelInfo[] levels = loadLevels();
    	ArrayList<Player> localPlayers = createLocalPlayers();
    	List<Player> remotePlayers = createRemotePlayers(connections);
    	
    	ArrayList<Player> players = new ArrayList<>(localPlayers);
    	players.addAll(remotePlayers);
    	return new SnakeGame(levels, players);
    }

    private void processClient(RemotePlayer remotePlayer){
        while (true){
            try {
                switch (remotePlayer.connection.readMessage()){
                    case GameRequest:
                        remotePlayer.connection.sendGame(getGame());
                        break;
                    case GameAction:
                        remotePlayer.getController().scheduleAction(remotePlayer.connection.readActionCode());
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    
    

    private synchronized SnakeGame getGame(){
        return game;
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
