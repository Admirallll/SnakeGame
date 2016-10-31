package ru.admirall.snake;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.awt.image.BufferedImage;

public class MainWindow extends JFrame implements ActionListener
{
    private final int speed = 5;
    private final int textureSize = 32;
    private SnakeGame game;
    private Timer timer = new Timer(1000 / speed, this);
    private IVisitor drawVisitor;
    
    private BufferedImage image;
    private Graphics bufferedGraphics;
    
    public DrawVisitor getDrawVisitor() {
    	return (DrawVisitor) drawVisitor;
    }

    public MainWindow(LevelInfo levelInfo)
    {
    	super("Snake game");
        game = new SnakeGame(levelInfo, createPlayers());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        drawVisitor = new DrawVisitor();
        setSize(game.getWidth() * textureSize, game.getHeight() * textureSize);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
        bufferedGraphics = image.createGraphics();
        bufferedGraphics.setColor(Color.LIGHT_GRAY);
        timer.start();
    }
    
    public ArrayList<Player> createPlayers() {
    	ArrayList<Player> players = new ArrayList<Player>();
        Player player1 = new Player(new PlayerController(getPlayer1Keys()), new Location(5, 5), Color.GREEN);
        Player player2 = new Player(new PlayerController(getPlayer2Keys()), new Location(1, 1), Color.RED);
        Player botPlayer1 = new Player(new BotController(), new Location(10, 10), Color.BLUE);
        players.add(player1);
        players.add(botPlayer1);
        players.add(player2);
        KeyListener keyListener = new KeyListener();
        keyListener.addListener(player1.getController());
        keyListener.addListener(player2.getController());
        addKeyListener(keyListener);
        return players;
    }
    
    public Map<Integer, ControllerAction> getPlayer1Keys() {
    	Map<Integer, ControllerAction> keys = new HashMap<Integer, ControllerAction>();
    	keys.put(KeyEvent.VK_W, (player) -> player.getSnake().setDirection(Direction.North));
    	keys.put(KeyEvent.VK_S, (player) -> player.getSnake().setDirection(Direction.South));
    	keys.put(KeyEvent.VK_A, (player) -> player.getSnake().setDirection(Direction.West));
    	keys.put(KeyEvent.VK_D,(player) ->  player.getSnake().setDirection(Direction.East));
    	keys.put(KeyEvent.VK_SPACE, (player) -> game.changeSnakeColor(player.getSnake()));
    	return keys;
    }
    
    public Map<Integer, ControllerAction> getPlayer2Keys() {
    	Map<Integer, ControllerAction> keys = new HashMap<Integer, ControllerAction>();
    	keys.put(KeyEvent.VK_UP, (player) -> player.getSnake().setDirection(Direction.North));
    	keys.put(KeyEvent.VK_DOWN, (player) -> player.getSnake().setDirection(Direction.South));
    	keys.put(KeyEvent.VK_LEFT, (player) -> player.getSnake().setDirection(Direction.West));
    	keys.put(KeyEvent.VK_RIGHT,(player) ->  player.getSnake().setDirection(Direction.East));
    	keys.put(KeyEvent.VK_ENTER, (player) -> game.changeSnakeColor(player.getSnake()));
    	return keys;
    }


    public void paint(Graphics g)
    {
        bufferedGraphics.fill3DRect(0, 0, getWidth(), getHeight(), true);
    	for (Player player : game.getPlayers())
    		if (player.isAlive())
    			player.getSnake().accept(drawVisitor);
        List<GameObject> objects = game.getObjects();
        drawObjects(objects);        
        g.drawImage(image, 0, 0, getSize().width, getSize().height, null);
    }
    
    public void drawObjects(List<GameObject> objects) {
    	for(GameObject object : objects)
            object.visit(drawVisitor);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        game.gameTurn();
        if (game.isEnded())
        	System.exit(0);
        repaint();
    }
    
    public Graphics getBufferedGraphics() {
    	return bufferedGraphics;
    }
    
    public static void main(String [] args)
    {
        LevelInfo levelInfo = LevelLoader.loadLevelFromFile("level.txt");
        JFrame frame = new MainWindow(levelInfo);
        frame.setVisible(true);
        MainWindow mainWindow = (MainWindow) frame;
        mainWindow.getDrawVisitor().setGraphics(mainWindow.getBufferedGraphics());
    }
}