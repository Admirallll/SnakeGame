package ru.admirall.snake.network;

import ru.admirall.snake.ActionCode;
import ru.admirall.snake.KeyListener;
import ru.admirall.snake.network.GameConnection;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Map;

public class NetworkKeyListener extends KeyListener {

	private GameConnection serverConnection;
	private Map<Integer, ActionCode> keyToAction;
	
	public NetworkKeyListener(GameConnection serverConnection, Map<Integer, ActionCode> keyToAction){
		this.serverConnection = serverConnection;
		this.keyToAction = keyToAction;
	}
	
	public void sendKeyToServer(int key) throws IOException {
		if (keyToAction.containsKey(key))
			serverConnection.sendActionCode(keyToAction.get(key));
	}
	
	public void keyPressed(KeyEvent keyEvent)
    {
		try {
			sendKeyToServer(keyEvent.getKeyCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
