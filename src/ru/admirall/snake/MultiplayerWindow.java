package ru.admirall.snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
		joinIpField = new JTextField("IP");
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
			try {
				new GameWindow(new GameClient(joinIpField.getText(), 45654));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			window.setVisible(false);
		}
	}
}
