package ru.admirall.snake.network;

import ru.admirall.snake.IGameState;

import java.io.IOException;

/**
 * Created by Stas on 21.11.2016.
 */
public interface IGameClient {
    public IGameState getGameState() throws IOException;
    public void startLoop();
}
