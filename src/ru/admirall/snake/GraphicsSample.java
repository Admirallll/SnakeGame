package ru.admirall.snake;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GraphicsSample extends JFrame implements ActionListener
{
    private final int speed = 5;
    private final int textureSize = 32;
    private SnakeGame game;
    private Timer timer = new Timer(1000 / speed, this);
    private Direction direction =  Direction.East;
    private IVisitor imageVisitor = new ImageVisitor();

    private GraphicsSample(LevelInfo levelInfo)
    {
    	super("Snake game");
        game = new SnakeGame(levelInfo);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(game.getWidth() * textureSize, game.getHeight() * textureSize);
        setResizable(false);
        addKeyListener(new KeyListener());
        setLocationRelativeTo(null);
        timer.start();
    }
    
    public void drawSnake(Graphics g, Snake snake) {
    	for(SnakePart snakePart : snake)
        {
            int x = snakePart.getLocation().getX();
            int y = snakePart.getLocation().getY();
            drawTexture(g, snake.visit(imageVisitor), x, y);
        }
    }

    public void paint(Graphics g)
    {
    	g.clearRect(0, 0, game.getWidth() * textureSize, game.getHeight() * textureSize);
        drawSnake(g, game.getSnake());
        List<Object> objects = game.getObjects();
        drawObjects(g, objects);

        
    }
    
    public void drawObjects(Graphics g, List<Object> objects) {
    	for(Object object : objects)
        {
            int x = object.getLocation().getX();
            int y = object.getLocation().getY();
            drawTexture(g, object.visit(imageVisitor), x, y);
        }
    }

    // visitor двойная диспетчеризация

    private void drawTexture(Graphics g, String filename, int x, int y)
    {
        String folder = "pics\\";
        g.drawImage(new ImageIcon(folder + filename).getImage(), x * textureSize, y * textureSize, null);
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
    	game.changeDirection(direction);
        game.gameTurn();
        if (game.isEnded())
        	setVisible(false);
        repaint();
    }

    public static void main(String [] args)
    {
        LevelInfo levelInfo = LevelLoader.loadLevelFromFile("level.txt");
        JFrame frame = new GraphicsSample(levelInfo);
        frame.setVisible(true);

    }

    private class KeyListener extends KeyAdapter
    {
        public void keyPressed(KeyEvent keyEvent)
        {
            int key = keyEvent.getKeyCode();
            if (key == KeyEvent.VK_RIGHT && game.getCurrentDirection() != Direction.West)
                direction = Direction.East;
            else if (key == KeyEvent.VK_LEFT && game.getCurrentDirection() != Direction.East)
                direction = Direction.West;
            else if (key == KeyEvent.VK_DOWN && game.getCurrentDirection() != Direction.North)
                direction = Direction.South;
            else if (key == KeyEvent.VK_UP && game.getCurrentDirection() != Direction.South)
                direction = Direction.North;
        }
    }
}