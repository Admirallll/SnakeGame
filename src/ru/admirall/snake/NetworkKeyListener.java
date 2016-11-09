package ru.admirall.snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class NetworkKeyListener extends KeyListener {

	private GameConnection serverConnection;
	private Map<Integer, ActionCode> keyToAction; 
	
	public NetworkKeyListener(GameConnection serverConnection, Map<Integer, ActionCode> keyToAction) throws IOException {
		this.serverConnection = serverConnection;
		this.keyToAction = keyToAction;
	}
	
	public void sendKeyToServer(int key) throws IOException {
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
