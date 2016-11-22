package ru.admirall.snake.network;

import ru.admirall.snake.GameCreator;
import ru.admirall.snake.IGameState;
import ru.admirall.snake.SnakeGame;
import ru.admirall.snake.players.RemotePlayer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Stas on 21.11.2016.
 */
public class OfflineGameClient implements IGameClient {
    private SnakeGame game;
    private final int turnPeriod = 500;

    public OfflineGameClient(SnakeGame game)
    {
        this.game = game;
    }

    public IGameState getGameState() throws IOException {
        return game;
    }

    public void startLoop(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                    game.turn();
            }
        }, turnPeriod, turnPeriod);
    }
}
