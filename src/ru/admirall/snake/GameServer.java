package ru.admirall.snake;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GameServer {
    private SnakeGame game;

    public GameServer(SnakeGame game){
        this.game = game;
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
