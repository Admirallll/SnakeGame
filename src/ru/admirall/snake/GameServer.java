package ru.admirall.snake;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameServer {
    private SnakeGame game;
    private final int turnPeriod = 700;

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
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getGame().turn();
            }
        }, turnPeriod, turnPeriod);
    }
    
    private SnakeGame createGame(List<GameConnection> connections) {
    	LevelInfo[] levels = GameCreator.loadLevels();
    	//ArrayList<Player> localPlayers = createLocalPlayers();
    	List<Player> remotePlayers = GameCreator.createRemotePlayers(connections);
    	
    	ArrayList<Player> players = new ArrayList<>();
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
}
