package ru.admirall.snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SinglegameWindow extends JFrame {

	
	public SinglegameWindow() {
		super();
		setSize(500, 500);
		JButton startButton = new JButton("Start");
		JPanel buttonsPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		add(buttonsPanel);
		buttonsPanel.add(startButton);
		buttonsPanel.add(cancelButton);
		startButton.addActionListener(new StartButtonAction(this));
		cancelButton.addActionListener(new CancelButtonAction(this));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
	}
	
	private class StartButtonAction extends AbstractAction {
		private JFrame window;
		public StartButtonAction(JFrame window){
			this.window = window;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LevelInfo[] levels = GameCreator.loadLevels();
			ArrayList<Player> players = GameCreator.createLocalPlayers();
			KeyListener keyListener = GameCreator.createKeyListener(players);
			new GameWindow(new SnakeGame(levels, players), keyListener).setVisible(true);
			window.setVisible(false);
		}
	}
	
	private class CancelButtonAction extends AbstractAction {
		
		private JFrame window;
		public  CancelButtonAction(JFrame window) {
			this.window = window;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			new MainMenuWindow().setVisible(true);
			window.setVisible(false);
		}
	}
}
