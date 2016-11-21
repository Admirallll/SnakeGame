package ru.admirall.snake.ui;

import ru.admirall.snake.GameCreator;
import ru.admirall.snake.KeyListener;
import ru.admirall.snake.SnakeGame;
import ru.admirall.snake.levels.LevelInfo;
import ru.admirall.snake.players.Player;

import java.awt.*;
import java.util.ArrayList;

public class SinglegameWindow extends SnakeGameWindowTemplate {
    public SinglegameWindow(Rectangle bounds) {
        super("Single game", bounds);
        placeButton("Start", actionEvent -> startAction());
        placeButton("Cancel", actionEvent -> cancelAction());
    }

    private void startAction() {
        LevelInfo[] levels = GameCreator.loadLevels();
        ArrayList<Player> players = GameCreator.createLocalPlayers();
        KeyListener keyListener = GameCreator.createKeyListener(players);
        //new GameWindow(new SnakeGame(levels, players), keyListener, getBounds()).setVisible(true);
        setVisible(false);
    }

    private void cancelAction() {
        new MainMenuWindow(getBounds()).setVisible(true);
        setVisible(false);
    }
}
