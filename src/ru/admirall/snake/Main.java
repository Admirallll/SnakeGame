package ru.admirall.snake;

import ru.admirall.snake.ui.MainMenuWindow;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static final int port = 45654;

    public static void main(String [] args) throws IOException, InterruptedException {
    	JFrame mainMenu = new MainMenuWindow();
    	mainMenu.setVisible(true);
    }

}