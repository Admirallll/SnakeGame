package ru.admirall.snake;

import ru.admirall.snake.levels.Level;
import ru.admirall.snake.players.Player;

import java.util.List;

public interface IGame {
    List<Player> getPlayers();
    Level getLevel();
    boolean isEnded();
    void turn();
}
