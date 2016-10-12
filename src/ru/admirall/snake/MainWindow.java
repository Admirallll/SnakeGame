package ru.admirall.snake;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame implements ActionListener
{
    private final int speed = 5;
    private final int textureSize = 32;
    private SnakeGame game;
    private Timer timer = new Timer(1000 / speed, this);
    private IVisitor drawVisitor = new DrawVisitor();

    private MainWindow(LevelInfo levelInfo)
    {
    	super("Snake game");
        game = new SnakeGame(levelInfo, createPlayers());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(game.getWidth() * textureSize, game.getHeight() * textureSize);
        setResizable(false);
        setLocationRelativeTo(null);
        timer.start();
    }
    
    public ArrayList<Player> createPlayers() {
    	ArrayList<Player> players = new ArrayList<Player>();
        Player player1 = new Player(new PlayerController(getPlayer1Keys()), new Location(5, 5));
        Player player2 = new Player(new PlayerController(getPlayer2Keys()), new Location(10, 10));
        players.add(player1);
        players.add(player2);
        KeyListener keyListener = new KeyListener();
        keyListener.addListener(player1.getController());
        keyListener.addListener(player2.getController());
        addKeyListener(keyListener);
        return players;
    }
    
    public Map<Integer, Direction> getPlayer1Keys() {
    	Map<Integer, Direction> keys = new HashMap<Integer, Direction>();
    	keys.put(KeyEvent.VK_W, Direction.North);
    	keys.put(KeyEvent.VK_S, Direction.South);
    	keys.put(KeyEvent.VK_A, Direction.West);
    	keys.put(KeyEvent.VK_D, Direction.East);
    	return keys;
    }
    
    public Map<Integer, Direction> getPlayer2Keys() {
    	Map<Integer, Direction> keys = new HashMap<Integer, Direction>();
    	keys.put(KeyEvent.VK_UP, Direction.North);
    	keys.put(KeyEvent.VK_DOWN, Direction.South);
    	keys.put(KeyEvent.VK_LEFT, Direction.West);
    	keys.put(KeyEvent.VK_RIGHT, Direction.East);
    	return keys;
    }
    
    public void drawSnake(Graphics g, Snake snake) {
    	for(SnakePart snakePart : snake)
        {
            int x = snakePart.getLocation().getX();
            int y = snakePart.getLocation().getY();
            drawTexture(g, snake.visit(drawVisitor), x, y);
        }
    }

    public void paint(Graphics g)
    {
    	g.clearRect(0, 0, game.getWidth() * textureSize, game.getHeight() * textureSize);
    	for (Player player : game.getPlayers())
    		drawSnake(g, player.getSnake());
        List<GameObject> objects = game.getObjects();
        drawObjects(g, objects);

        
    }
    
    public void drawObjects(Graphics g, List<GameObject> objects) {
    	for(GameObject object : objects)
        {
            int x = object.getLocation().getX();
            int y = object.getLocation().getY();
            drawTexture(g, object.visit(drawVisitor), x, y);
        }
    }

    private void drawTexture(Graphics g, String filename, int x, int y)
    {
        String folder = "pics\\";
        g.drawImage(new ImageIcon(folder + filename).getImage(), x * textureSize, y * textureSize, null);
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
    	for (Player player : game.getPlayers())
    		player.getSnake().setDirection(player.getController().getDirection());
        game.gameTurn();
        if (game.isEnded())
        	setVisible(false);
        repaint();
    }

    public static void main(String [] args)
    {
        LevelInfo levelInfo = LevelLoader.loadLevelFromFile("level.txt");
        System.out.println(levelInfo);
        JFrame frame = new MainWindow(levelInfo);
        frame.setVisible(true);
    }
}