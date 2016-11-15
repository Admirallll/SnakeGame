package ru.admirall.snake.players;

import ru.admirall.snake.network.GameConnection;

public class RemotePlayer extends Player {
    transient public final GameConnection connection;

    public RemotePlayer(GameConnection connection){
        super(new PlayerController());
        this.connection = connection;
    }

    @Override
    public PlayerController getController() {
        return (PlayerController)super.getController();
    }
}
