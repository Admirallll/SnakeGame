package ru.admirall.snake;

import ru.admirall.snake.ui.MainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static final int port = 45654;

    public static void main(String [] args) throws IOException, InterruptedException {
        JFrame mainMenu = new MainMenuWindow(new Rectangle(100, 100, 500, 500));
        mainMenu.setVisible(true);
    }

}