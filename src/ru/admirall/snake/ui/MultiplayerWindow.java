package ru.admirall.snake.ui;

import ru.admirall.snake.*;
import ru.admirall.snake.network.GameClient;
import ru.admirall.snake.network.GameConnection;
import ru.admirall.snake.network.NetworkKeyListener;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiplayerWindow extends JFrame {
	
	private JTextField joinIpField;
	
	public MultiplayerWindow() {
		super();
		setSize(500, 500);
		JButton createButton = new JButton("Create");
		JPanel buttonsPanel = new JPanel();
		JButton joinButton = new JButton("Join");
		add(buttonsPanel);
		buttonsPanel.add(createButton);
		buttonsPanel.add(joinButton);
		joinIpField = new JTextField("127.0.0.1");
		joinIpField.setSize(50, 15);
		buttonsPanel.add(joinIpField);
		createButton.addActionListener(new CreateButtonAction(this));
		joinButton.addActionListener(new JoinButtonAction(this));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
	}
	
	private class CreateButtonAction extends AbstractAction {
		private JFrame window;
		public CreateButtonAction(JFrame window){
			this.window = window;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new WaitingRoomWindow().setVisible(true);
			window.setVisible(false);
		}
	}
	
	private class JoinButtonAction extends AbstractAction {
		
		private JFrame window;
		public  JoinButtonAction(JFrame window) {
			this.window = window;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			IGame gameClient;
			KeyListener keyListener;
			try {
				GameConnection connection = new GameConnection(new Socket(joinIpField.getText(), Main.port));
				gameClient = new GameClient(connection);
				keyListener = new NetworkKeyListener(connection, GameCreator.getPlayer1Keys());
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			new GameWindow(gameClient, keyListener).setVisible(true);
			window.setVisible(false);
		}
	}
}
