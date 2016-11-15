package ru.admirall.snake.players;

import ru.admirall.snake.ActionCode;

import java.util.Map;

public class KeyboardPlayerController extends PlayerController {
	private Map<Integer, ActionCode> keyToAction;

	public KeyboardPlayerController(Map<Integer, ActionCode> keysToActions) {
		this.keyToAction = keysToActions;
	}
	
	public void listenKey(int keyCode) {
		if (keyToAction != null && keyToAction.containsKey(keyCode))
			scheduleAction(keyToAction.get(keyCode));
	}
}
