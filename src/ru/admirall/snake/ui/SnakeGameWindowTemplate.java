package ru.admirall.snake.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SnakeGameWindowTemplate extends JFrame {

    protected JPanel panel;

    public SnakeGameWindowTemplate(String name, Rectangle bounds) {
        super(name);
        setBounds(bounds);
        panel = new JPanel();
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void placeButton(String name, ActionListener onClick) {
        JButton button = new JButton(name);
        panel.add(button);
        button.addActionListener(onClick);
    }
}
