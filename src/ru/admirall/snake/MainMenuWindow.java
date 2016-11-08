package ru.admirall.snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenuWindow extends JFrame {
	
	public MainMenuWindow() {
		super();
		setSize(500, 500);
		JButton singleGameButton = new JButton("Single game");
		JPanel buttonsPanel = new JPanel();
		JButton multiplayerGameButton = new JButton("Mupliplayer game");
		add(buttonsPanel);
		buttonsPanel.add(singleGameButton);
		buttonsPanel.add(multiplayerGameButton);
		singleGameButton.addActionListener(new SingleGameButtonAction(this));
		multiplayerGameButton.addActionListener(new MultiplayerGameButtonAction(this));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
	}
	
	private class SingleGameButtonAction extends AbstractAction {
		private JFrame window;
		public SingleGameButtonAction(JFrame window){
			this.window = window;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			window.setVisible(false);
		}
	}
	
	private class MultiplayerGameButtonAction extends AbstractAction {
		
		private JFrame window;
		public  MultiplayerGameButtonAction(JFrame window) {
			this.window = window;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			new MultiplayerWindow().setVisible(true);
			window.setVisible(false);
		}
	}
}
