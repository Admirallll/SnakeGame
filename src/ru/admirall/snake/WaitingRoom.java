package ru.admirall.snake;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class WaitingRoom implements Runnable {

    private ServerSocket socket;
    private boolean isStopped = false;
    private List<GameConnection> connections;

    public WaitingRoom(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (!isStopped()){
            Socket clientSocket;
            try {
                clientSocket = this.socket.accept();
                connections.add(new GameConnection(clientSocket));
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

    public synchronized List<GameConnection> getConnections(){
        return connections;
    }
}
