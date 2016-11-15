package ru.admirall.snake.ui;

import ru.admirall.snake.IGame;
import ru.admirall.snake.IVisitor;
import ru.admirall.snake.KeyListener;
import ru.admirall.snake.gameobjects.GameObject;
import ru.admirall.snake.players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class GameWindow extends SnakeGameWindowTemplate implements ActionListener
{
    public static final int timerPeriod = 100;
    public static final int textureSize = 32;
    private Timer timer = new Timer(timerPeriod, this);

    private IGame game;

    private IVisitor drawVisitor;
    private BufferedImage image;
    private Graphics bufferedGraphics;

    public GameWindow(IGame game, KeyListener keyListener, Rectangle bounds)
    {
        super("Snake game", bounds);
        this.game = game;
        addKeyListener(keyListener);
        drawVisitor = new DrawVisitor();
        initializeWindow();
        ((DrawVisitor)drawVisitor).setGraphics(bufferedGraphics);
        timer.start();
    }

    private void initializeWindow() {
        //setUndecorated(true);
        setSize(game.getLevel().width * textureSize, game.getLevel().height * textureSize);
        setResizable(false);
        setLocationRelativeTo(null);
        image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
        bufferedGraphics = image.createGraphics();
        bufferedGraphics.setColor(Color.LIGHT_GRAY);
    }

    public void paint(Graphics g)
    {
        System.out.println("PAINT2");
        bufferedGraphics.fill3DRect(0, 0, getWidth(), getHeight(), true);

    	for (Player player : game.getPlayers())
            player.accept(drawVisitor);
        drawObjects(game.getLevel());

        g.drawImage(image, 0, 0, getSize().width, getSize().height, null);
    }
    
    private void drawObjects(Iterable<GameObject> objects) {
    	for(GameObject object : objects)
            object.accept(drawVisitor);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        System.out.println("PAINT");
        repaint();
    }
}