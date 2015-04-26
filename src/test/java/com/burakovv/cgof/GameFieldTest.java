package com.burakovv.cgof;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class GameFieldTest {
    private static final Random random = new Random(75319539);

    @Test
    public void randomTesting() {
        for (int width = 0; width <= Integer.SIZE; width++) {
            for (int height = 0; height <= Integer.SIZE; height++) {
                boolean[][] expected = new boolean[width][height];
                GameField actual = new GameField(width, height);
                assertEquals(width, actual.area().boundX);
                assertEquals(height, actual.area().boundY);
                for (int i = 0; i < width * height; i++) {
                    int x = random.nextInt(width);
                    int y = random.nextInt(height);
                    boolean value = random.nextBoolean();
                    expected[x][y] = value;
                    actual.set(x, y, value);
                    for (int xx = 0; xx < width; xx++) {
                        for (int yy = 0; yy < height; yy++) {
                            assertEquals(expected[xx][yy], actual.get(xx, yy));
                        }
                    }
                }
            }
        }
    }
}