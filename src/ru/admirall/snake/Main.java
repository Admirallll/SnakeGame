package ru.admirall.snake;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String [] args) throws IOException, InterruptedException {
    	JFrame mainMenu = new MainMenuWindow();
    	mainMenu.setVisible(true);
    }

}