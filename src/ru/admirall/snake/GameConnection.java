package ru.admirall.snake;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

enum GameConnectionMessage {
    GameRequest, KeyPressed
}

public class GameConnection {
    private final Socket socket;

    public GameConnection(Socket socket){
        this.socket = socket;
    }

    private void sendObject(Object object) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(object);
    }

    private Object readObject() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return in.readObject();
    }

    private void sendInt(int value) throws IOException {
        socket.getOutputStream().write(value);
    }

    private int readInt() throws IOException {
        return socket.getInputStream().read();
    }

    private void sendMessage(GameConnectionMessage message) throws IOException {
        sendInt(message.ordinal());
    }

    public GameConnectionMessage readMessage() throws IOException {
        return GameConnectionMessage.values()[readInt()];
    }

    public void sendGame(SnakeGame game) throws IOException {
        sendObject(game);
    }

    public SnakeGame readGame() throws IOException, ClassNotFoundException {
        sendMessage(GameConnectionMessage.GameRequest);
        return (SnakeGame)readObject();
    }

    public void sendKey(Integer keyCode) throws IOException {
        sendMessage(GameConnectionMessage.KeyPressed);
        sendInt(keyCode);
    }

    public int readKey() throws IOException, ClassNotFoundException {
        return readInt();
    }
}
