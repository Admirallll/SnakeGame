package ru.admirall.snake;

import ru.admirall.snake.players.KeyboardPlayerController;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyListener extends KeyAdapter
{
	private List<KeyboardPlayerController> controllers;
	
	public KeyListener() {
		super();
		controllers = new ArrayList<>();
	}
	
	public void addListener(KeyboardPlayerController controller) {
		controllers.add(controller);
	}
	
    public void keyPressed(KeyEvent keyEvent)
    {
        for (KeyboardPlayerController controller: controllers)
			controller.listenKey(keyEvent.getKeyCode());
    }
}
