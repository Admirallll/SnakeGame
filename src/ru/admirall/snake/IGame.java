package ru.admirall.snake;

import java.util.List;

public interface IGame {
    List<Player> getPlayers();
    Level getLevel();
    boolean isEnded();
    void turn();
}
