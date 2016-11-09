package ru.admirall.snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WaitingRoomWindow extends JFrame {
	private Thread waitingRoomThread;
	private WaitingRoom waitingRoom;
	
	
	public WaitingRoomWindow() {
		super();
		setSize(500, 500);
		JButton startGameButton = new JButton("Start");
		JPanel buttonsPanel = new JPanel();
		JButton cancelGameButton = new JButton("Cancel");
		add(buttonsPanel);
		buttonsPanel.add(startGameButton);
		buttonsPanel.add(cancelGameButton);
		startGameButton.addActionListener(new StartGameButtonAction(this));
		cancelGameButton.addActionListener(new CancelGameButtonAction(this));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
		
		try {
			waitingRoom = new WaitingRoom(45654);
		    waitingRoomThread = new Thread(waitingRoom);
		    waitingRoomThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class StartGameButtonAction extends AbstractAction {
		private JFrame window;
		public StartGameButtonAction(JFrame window){
			this.window = window;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
		    waitingRoom.stop();
		    try {
				waitingRoomThread.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		    new GameServer(waitingRoom.getConnections());
		    try {
		    	GameConnection connection = new GameConnection(new Socket("127.0.0.1", 45654));
				new GameWindow(new GameClient(connection), new NetworkKeyListener(connection, GameCreator.getPlayer1Keys())).setVisible(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			window.setVisible(false);
		}
	}
	
	private class CancelGameButtonAction extends AbstractAction {
		
		private JFrame window;
		public  CancelGameButtonAction(JFrame window) {
			this.window = window;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.print("Multi");
		}
	}
}
