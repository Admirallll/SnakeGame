package ru.admirall.snake.ui;

import ru.admirall.snake.*;
import ru.admirall.snake.network.*;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WaitingRoomWindow extends JFrame {
	private WaitingRoom waitingRoom;

	public WaitingRoomWindow(){
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
			waitingRoom = new WaitingRoom(Main.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		waitingRoom.start();
	}
	
	private class StartGameButtonAction extends AbstractAction {
		private JFrame window;
		public StartGameButtonAction(JFrame window){
			this.window = window;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
            GameConnection localConnection;
            GameClient gameClient;
            try {
				localConnection = new GameConnection(new Socket("127.0.0.1", Main.port));
                gameClient = new GameClient(localConnection);
            } catch (IOException e1) {
                e1.printStackTrace();
				return;
            }
			waitingRoom.stop();
			new GameServer(waitingRoom.getConnections()).start();
            KeyListener keyListener = new NetworkKeyListener(localConnection, GameCreator.getPlayer1Keys());
            new GameWindow(gameClient, keyListener).setVisible(true);
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
