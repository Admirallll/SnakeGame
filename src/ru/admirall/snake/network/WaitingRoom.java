package ru.admirall.snake.network;

import ru.admirall.snake.network.GameConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WaitingRoom {
    private ServerSocket socket;
    private boolean isStopped = false;
    private List<GameConnection> connections;
    private Thread thread;

    public WaitingRoom(int port) throws IOException {
        this.socket = new ServerSocket(port);
        connections = new ArrayList<>();
        thread = new Thread(this::acceptClients);
    }

    private void acceptClients() {
        while (!isStopped()){
            Socket clientSocket;
            try {
                clientSocket = socket.accept();
                addConnection(clientSocket);
            } catch (IOException ignored) {
                return;
            }
        }
}

    private synchronized void addConnection(Socket clientSocket) {
        connections.add(new GameConnection(clientSocket));
    }

    public void start(){
        thread.start();
    }

    private synchronized boolean isStopped() {
        return isStopped;
    }

    private synchronized void setStopped(boolean value) {isStopped = value; }

    public void stop(){
        setStopped(true);
//        try {
//            //this.socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }
    }

    public synchronized List<GameConnection> getConnections(){
        return new ArrayList<>(connections);
    }
}
