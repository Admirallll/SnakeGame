package ru.admirall.snake.network;

import ru.admirall.snake.IGame;
import ru.admirall.snake.SnakeGame;
import ru.admirall.snake.levels.Level;
import ru.admirall.snake.players.Player;
import ru.admirall.snake.ui.GameWindow;

import java.io.IOException;
import java.util.List;

public class GameClient implements IGame {
    private GameConnection connection;
    private SnakeGame remoteGame = null;
    private boolean readFailed;
    private GameWindow window;
    public GameClient(GameConnection connection) throws IOException {
        this.connection = connection;
//        turn();
    }

    private SnakeGame getRemoteGame() {
        if (remoteGame == null)
            remoteGame = getGameFromServer();
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

    public SnakeGame getGameFromServer() {
        SnakeGame game = null;
        System.out.println("GETGAME");
        try {
            game = connection.readGame();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return game;
    }

    @Override
    public void turn() {
        while (true) {
            System.out.println("LOOOOP");
            remoteGame = getGameFromServer();
        }
    }
}