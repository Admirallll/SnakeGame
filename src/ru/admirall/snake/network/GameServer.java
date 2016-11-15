package ru.admirall.snake.network;

import ru.admirall.snake.GameCreator;
import ru.admirall.snake.SnakeGame;
import ru.admirall.snake.players.RemotePlayer;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class GameServer {
    private final SnakeGame game;
    private final int turnPeriod = 500;

    public GameServer(List<GameConnection> connections){
        this.game = GameCreator.createServerGame(connections);
    }

    public void start(){
        for (RemotePlayer remotePlayer : getRemotePlayers()){
            new Thread(()->processClient(remotePlayer)).start();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (GameServer.this) {
                    getGame().turn();
                }
            }
        }, turnPeriod, turnPeriod);
    }

    private List<RemotePlayer> getRemotePlayers() {
        return game.getPlayers()
                    .stream()
                    .filter(player -> player instanceof RemotePlayer)
                    .map(player -> (RemotePlayer) player)
                    .collect(Collectors.toList());
    }

    private void processClient(RemotePlayer remotePlayer){
        while (true){
            try {
                GameConnectionMessage message = remotePlayer.connection.readMessage();
                synchronized (this){
                    switch (message) {
                        case GameRequest:
                            remotePlayer.connection.sendGame(getGame());
                            break;
                        case GameAction:
                            remotePlayer.getController().scheduleAction(remotePlayer.connection.readActionCode());
                            break;
                    }
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
