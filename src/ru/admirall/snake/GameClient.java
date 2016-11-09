package ru.admirall.snake;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class GameClient implements IGame {
    private GameConnection connection;
    private SnakeGame remoteGame;
    private boolean readFailed;

    public GameClient(GameConnection connection) throws IOException {
        this.connection = connection;
        turn();
    }

    @Override
    public List<Player> getPlayers() {
        return remoteGame.getPlayers();
    }

    @Override
    public Level getLevel() {
        return remoteGame.getLevel();
    }

    @Override
    public boolean isEnded() {
        return remoteGame.isEnded() || readFailed;
    }

    @Override
    public void turn() {
        try {
            remoteGame = connection.readGame();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            readFailed = true;
        }
    }
}
