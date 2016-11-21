package ru.admirall.snake.ui;

import ru.admirall.snake.IGameState;
import ru.admirall.snake.IVisitor;
import ru.admirall.snake.KeyListener;
import ru.admirall.snake.gameobjects.GameObject;
import ru.admirall.snake.network.GameClient;
import ru.admirall.snake.network.IGameClient;
import ru.admirall.snake.players.Player;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class GameWindow extends SnakeGameWindowTemplate implements ActionListener
{
    public static final int timerPeriod = 100;
    public static final int textureSize = 32;
    private Timer timer = new Timer(timerPeriod, this);

    private IGameClient gameClient;
    private IGameState gameState;

    private IVisitor drawVisitor;
    private BufferedImage image;
    private Graphics bufferedGraphics;

    public GameWindow(IGameClient gameClient, KeyListener keyListener, Rectangle bounds)
    {
        super("Snake game", bounds);
        this.gameClient = gameClient;
            addKeyListener(keyListener);
        drawVisitor = new DrawVisitor();
        initializeWindow();
        timer.start();
    }

    private void initializeWindow() {
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void paint(Graphics g)
    {
        if (gameState == null)
            return;
        bufferedGraphics.fill3DRect(0, 0, getSize().width, getSize().height, true);

    	for (Player player : gameState.getPlayers())
            player.accept(drawVisitor);
        drawObjects(gameState.getLevel());

        g.drawImage(image, 0, 0, getSize().width, getSize().height, null);
    }
    
    private void drawObjects(Iterable<GameObject> objects) {
    	for(GameObject object : objects)
            object.accept(drawVisitor);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        System.out.println("PAINT");
        try {
            gameState = gameClient.getGameState();
        } catch (IOException e) {
            System.exit(0);
        }
        if (gameState == null)
            return;
        setSize(gameState.getLevel().width * textureSize, gameState.getLevel().height * textureSize);

        if (image == null) {
            image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
            bufferedGraphics = image.createGraphics();
            bufferedGraphics.setColor(Color.LIGHT_GRAY);
            ((DrawVisitor)drawVisitor).setGraphics(bufferedGraphics);
        }
        repaint();
    }
}