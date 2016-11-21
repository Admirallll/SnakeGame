package ru.admirall.snake.ui;

import ru.admirall.snake.GameCreator;
import ru.admirall.snake.KeyListener;
import ru.admirall.snake.Main;
import ru.admirall.snake.network.GameClient;
import ru.admirall.snake.network.GameConnection;
import ru.admirall.snake.network.NetworkKeyListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class MultiplayerWindow extends SnakeGameWindowTemplate {

    private JTextField joinIpField;

    public MultiplayerWindow(Rectangle bounds) {
        super("Multiplayer", bounds);
        placeButton("Create", actionEvent -> createAction());
        placeButton("Join", actionEvent -> joinAction());
        joinIpField = new JTextField("127.0.0.1");
        joinIpField.setSize(50, 15);
        panel.add(joinIpField);
    }

    private void createAction() {
        new WaitingRoomWindow(getBounds()).setVisible(true);
        setVisible(false);
    }

    private void joinAction() {
        GameClient gameClient;
        KeyListener keyListener;
        try {
            GameConnection connection = new GameConnection(new Socket(joinIpField.getText(), Main.port));
            gameClient = new GameClient(connection);
            keyListener = new NetworkKeyListener(connection, GameCreator.getPlayer1Keys());
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        gameClient.startLoop();
        new GameWindow(gameClient, keyListener, getBounds()).setVisible(true);
        setVisible(false);
    }
}
