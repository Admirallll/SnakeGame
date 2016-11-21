package ru.admirall.snake.network;

import ru.admirall.snake.IGameState;
import ru.admirall.snake.SnakeGame;
import ru.admirall.snake.levels.Level;
import ru.admirall.snake.players.Player;
import ru.admirall.snake.ui.GameWindow;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Exchanger;

public class GameClient {
    private GameConnection connection;
    private SnakeGame remoteGame = null;
    private boolean readFailed;
    public GameClient(GameConnection connection) throws IOException {
        this.connection = connection;
    }

    public IGameState getGameState() throws IOException {
        if (readFailed)
            throw new IOException("Cannot read state");
        synchronized (this) {
            return remoteGame;
        }
    }

    private SnakeGame getGameFromServer() {
        SnakeGame game = null;
        System.out.println("GETGAME");
        try {
            game = connection.readGame();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("READ FAILED");
            e.printStackTrace();
            readFailed = true;
        }
        return game;
    }

    public void startLoop() {
        new Thread(() -> {
            while (true) {
                System.out.println("LOOOOP");
                SnakeGame remoteGame = null;
                remoteGame = getGameFromServer();
                synchronized (this) {
                    this.remoteGame = remoteGame;
                }
            }
        }).start();
    }
}