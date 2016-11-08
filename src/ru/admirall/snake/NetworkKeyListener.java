package ru.admirall.snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;

public class NetworkKeyListener extends KeyAdapter {

	private GameConnection serverConnection;
	
	public NetworkKeyListener(String serverIp, int serverPort) throws IOException {
		serverConnection = new GameConnection(new Socket(serverIp, serverPort));
	}
	
	public void sendKeyToServer(int key) throws IOException {
		//serverConnection.sendActionCode(key);
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
