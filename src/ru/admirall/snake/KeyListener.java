package ru.admirall.snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyListener extends KeyAdapter
{
	private List<PlayerController> controllers;
	
	public KeyListener() {
		super();
		controllers = new ArrayList<PlayerController>(); 
	}
	
	public void addListener(PlayerController controller) {
		controllers.add(controller);
	}
	
    public void keyPressed(KeyEvent keyEvent)
    {
        for (PlayerController controller: controllers)
			controller.listenKey(keyEvent.getKeyCode());
    }
}
