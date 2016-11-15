package ru.admirall.snake.players;

import ru.admirall.snake.*;
import ru.admirall.snake.primitives.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerController implements IPlayerController {
    private static Map<ActionCode, ControllerAction> codeToAction = new HashMap<ActionCode, ControllerAction>(){{
        put(ActionCode.TurnUp, (game, player) -> player.getSnake().setDirection(Direction.North));
        put(ActionCode.TurnDown, (game, player) -> player.getSnake().setDirection(Direction.South));
        put(ActionCode.TurnLeft, (game, player) -> player.getSnake().setDirection(Direction.West));
        put(ActionCode.TurnRight,(game, player) ->  player.getSnake().setDirection(Direction.East));
        put(ActionCode.ChangeColor, SnakeGame::changePlayerColor);
    }};

    private ArrayList<ControllerAction> actions = new ArrayList<>();

    public PlayerController() {

    }

    public void scheduleAction(ActionCode code) {
        actions.add(codeToAction.get(code));
    }

    public void controlSnake(SnakeGame game, Player player) {
        for (ControllerAction act : actions)
            act.action(game, player);
        actions.clear();
    }
}
