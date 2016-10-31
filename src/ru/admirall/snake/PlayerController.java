package ru.admirall.snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class PlayerController implements IPlayerController {
	private Map<Integer, ControllerAction> keyToAction;
	private ArrayList<ControllerAction> actions = new ArrayList<ControllerAction>();
	
	public PlayerController(Map<Integer, ControllerAction> keysToActions) {
		this.keyToAction = keysToActions;
	}
	
	public void listenKey(int keyCode) {
		if (keyToAction != null && keyToAction.containsKey(keyCode))
			actions.add(keyToAction.get(keyCode));
	}

	public void controlSnake(SnakeGame game, Player player) {
		for (ControllerAction act : actions)
			act.action(player);
		actions.clear();
	}
}
