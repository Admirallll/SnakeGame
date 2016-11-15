package ru.admirall.snake.ui;

import java.awt.*;

public class MainMenuWindow extends SnakeGameWindowTemplate {

	public MainMenuWindow(Rectangle bounds) {
		super("Main menu", bounds);
		placeButton("Single game", actionEvent -> singlegameAction());
		placeButton("Muptiplayer", actionEvent -> mupliplayerAction());
	}

	private void singlegameAction() {
		new SinglegameWindow(getBounds()).setVisible(true);
		setVisible(false);
	}

	private void mupliplayerAction() {
		new MultiplayerWindow(getBounds()).setVisible(true);
		setVisible(false);
	}
}
