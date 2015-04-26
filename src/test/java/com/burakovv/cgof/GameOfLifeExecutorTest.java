package com.burakovv.cgof;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class GameOfLifeExecutorTest {

    @Test
    public void shouldMatchSingleThreadedVersion() throws InterruptedException {
        GameOfLifeExecutor executor = GameOfLifeExecutor.create();
        GameField gameField = new GameField(50, 50);
        Random random = new Random(531539753);
        for (int i = 0; i < 1000; i++) {
            GameOfLife.randomize(gameField, random);
            GameField expected = new GameField(gameField.width(), gameField.height());
            GameField actual = new GameField(gameField.width(), gameField.height());
            GameOfLife.progress(gameField, expected);
            executor.progress(gameField, actual);
            checkEquals(expected, actual);
        }
    }

    private void checkEquals(GameField expected, GameField actual) {
        assertEquals(expected.width(), actual.width());
        assertEquals(expected.height(), actual.height());
        for (int x = 0; x < expected.width(); x++) {
            for (int y = 0; y < expected.height(); y++) {
                assertEquals(expected.get(x, y), actual.get(x, y));
            }
        }
    }
}