package ru.admirall.snake.network;

import ru.admirall.snake.IGame;
import ru.admirall.snake.SnakeGame;
import ru.admirall.snake.levels.Level;
import ru.admirall.snake.players.Player;
import ru.admirall.snake.snake.Snake;

import java.io.IOException;
import java.util.List;

public class GameClient implements IGame {
    private GameConnection connection;
    private SnakeGame remoteGame = null;
    private boolean readFailed;

    public GameClient(GameConnection connection) throws IOException {
        this.connection = connection;
    }

    private SnakeGame getRemoteGame(){
        if (remoteGame == null)
            turn();
        return remoteGame;
    }

    @Override
    public List<Player> getPlayers() {
        return getRemoteGame().getPlayers();
    }

    @Override
    public Level getLevel() {
        return getRemoteGame().getLevel();
    }

    @Override
    public boolean isEnded() {
        return getRemoteGame().isEnded() || readFailed;
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
