package com.burakovv.cgof.app;

import com.burakovv.cgof.GameField;
import com.burakovv.cgof.GameOfLife;
import com.burakovv.cgof.GameOfLifeExecutor;

import javax.swing.*;

public class GameOfLifeApp {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 350;
    private static GameField[] field;
    private static int current;
    private static GameFieldPanel gameFieldPanel;
    private static GameOfLifeExecutor executor = GameOfLifeExecutor.create();

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(GameOfLifeApp::createAndShowGUI);
        while (true) {
            SwingUtilities.invokeAndWait(GameOfLifeApp::update);
        }
    }

    private static void update() {
        try {
            executor.progress(field[current], field[1 - current]);
            current = 1 - current;
            gameFieldPanel.setField(field[current]);
            gameFieldPanel.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Conway's game of life");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        field = new GameField[] {new GameField(WIDTH, HEIGHT), new GameField(WIDTH, HEIGHT)};
        gameFieldPanel = new GameFieldPanel(field[current]);
        gameFieldPanel.setCellSize(1);
        gameFieldPanel.setCellMargin(0);
        frame.add(gameFieldPanel);
        frame.setResizable(false);
        GameOfLife.randomize(field[current]);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
