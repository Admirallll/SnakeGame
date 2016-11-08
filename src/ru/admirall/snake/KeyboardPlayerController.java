package ru.admirall.snake;

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
