package ru.admirall.snake;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {

    private ServerSocket socket;
    private boolean isStopped = false;
    private SnakeGame game;

    public GameServer(int port, SnakeGame game) throws IOException {
        this.socket = new ServerSocket(port);
        this.game = game;
    }

    @Override
    public void run() {
        while (!isStopped()){
            Socket clientSocket;
            try {
                clientSocket = this.socket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            new Thread(() -> processClient(clientSocket)).start();
        }
    }

    private void processClient(Socket client){
        GameConnection connection = new GameConnection(client);
        while (true){
            try {
                switch (connection.readMessage()){
                    case GameRequest:
                        connection.sendGame(getGame());
                        break;
                    case KeyPressed:
                        // TODO
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private synchronized boolean isStopped() {
        return isStopped;
    }

    public synchronized void stop(){
        isStopped = true;
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized SnakeGame getGame(){
        return game;
    }
}
