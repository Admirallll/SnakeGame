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
//        if (key == KeyEvent.VK_RIGHT && game.getCurrentDirection() != Direction.West)
//            direction = Direction.East;
//        else if (key == KeyEvent.VK_LEFT && game.getCurrentDirection() != Direction.East)
//            direction = Direction.West;
//        else if (key == KeyEvent.VK_DOWN && game.getCurrentDirection() != Direction.North)
//            direction = Direction.South;
//        else if (key == KeyEvent.VK_UP && game.getCurrentDirection() != Direction.South)
//            direction = Direction.North;
    }
}
