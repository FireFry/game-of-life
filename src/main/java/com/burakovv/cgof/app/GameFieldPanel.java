package com.burakovv.cgof.app;

import com.burakovv.cgof.GameField;

import javax.swing.*;
import java.awt.*;

public class GameFieldPanel extends JPanel {
    private int cellSize = 1;
    private int cellMargin = 0;
    private GameField field;

    public GameFieldPanel() {}

    public GameFieldPanel(GameField field) {
        this();
        setField(field);
    }

    public void setField(GameField field) {
        this.field = field;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void setCellMargin(int cellMargin) {
        this.cellMargin = cellMargin;
    }

    public Dimension getPreferredSize() {
        return new Dimension(field.width() * (cellSize + cellMargin) + cellMargin, field.height() * (cellSize + cellMargin) + cellMargin);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (field == null) {
            return;
        }
        g.setColor(Color.BLACK);
        for (int x = 0; x < field.width(); x++) {
            for (int y = 0; y < field.height(); y++) {
                if (field.get(x, y)) {
                    g.fillRect(cellMargin + x * (cellMargin + cellSize), cellMargin + y * (cellMargin + cellSize), cellSize, cellSize);
                }
            }
        }
    }
}
