package ru.admirall.snake.ui;

import ru.admirall.snake.GameCreator;
import ru.admirall.snake.KeyListener;
import ru.admirall.snake.Main;
import ru.admirall.snake.network.*;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class WaitingRoomWindow extends SnakeGameWindowTemplate {
    private WaitingRoom waitingRoom;

    public WaitingRoomWindow(Rectangle bounds) {
        super("Waiting room", bounds);
        placeButton("Start", actionEvent -> startAction());
        placeButton("Cancel", actionEvent -> cancelAction());
        try {
            waitingRoom = new WaitingRoom(Main.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		waitingRoom.start();
	}

    private void startAction() {
        GameConnection localConnection;
        GameClient gameClient;
        try {
            localConnection = new GameConnection(new Socket("127.0.0.1", Main.port));
            waitingRoom.stop();
            new GameServer(waitingRoom.getConnections()).start();
            gameClient = new GameClient(localConnection);
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        KeyListener keyListener = new NetworkKeyListener(localConnection, GameCreator.getPlayer1Keys());
        gameClient.startLoop();
        new GameWindow(gameClient, keyListener, getBounds()).setVisible(true);

        setVisible(false);
    }

    private void cancelAction() {
        new MainMenuWindow(getBounds()).setVisible(true);
        setVisible(false);
    }
}
