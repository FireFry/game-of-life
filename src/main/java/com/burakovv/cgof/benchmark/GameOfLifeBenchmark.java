package com.burakovv.cgof.benchmark;

import com.burakovv.cgof.GameField;
import com.burakovv.cgof.GameOfLife;
import com.burakovv.cgof.GameOfLifeExecutor;

import java.util.Random;

public class GameOfLifeBenchmark {
    public static final int STEPS = 1000;

    public static void main(String[] args) {
        Random random = new Random(5315395);
        GameField gameField = new GameField(256, 256);
        GameOfLife.randomize(gameField, random);

        measureSimpleTimePerCell(gameField);
        measureMultithreadedTimePerCell(gameField);

        double simpleTimePerCell = measureSimpleTimePerCell(gameField);
        double multithreadedCellTime = measureMultithreadedTimePerCell(gameField);

        System.out.format("Simple: %.2f cells per ms%s", 1d / simpleTimePerCell, System.lineSeparator());
        System.out.format("Multithreaded: %.2f cells per ms%s", 1d / multithreadedCellTime, System.lineSeparator());
        System.out.format("Multithreaded: %.2f times faster%s", simpleTimePerCell / multithreadedCellTime, System.lineSeparator());
    }

    private static double measureSimpleTimePerCell(GameField gameField) {
        return measureCellCalculationTime(gameField, SimpleCalculator.INSTANCE);
    }

    private static double measureMultithreadedTimePerCell(GameField gameField) {
        GameOfLifeExecutor executor = GameOfLifeExecutor.create();
        try {
            return measureCellCalculationTime(gameField, new MultithreadedCalculator(executor));
        } finally {
            executor.stop();
        }
    }

    private static double measureCellCalculationTime(GameField gameField, Calculator calculator) {
        System.out.println("New measurement started");
        GameField[] buffer = new GameField[2];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = new GameField(gameField.width(), gameField.height());
        }
        int current = 0;
        copy(gameField, buffer[current]);
        double startTime = System.currentTimeMillis();
        for (int step = 0; step < STEPS; step++) {
            calculator.calculate(buffer[current], buffer[1 - current]);
            current = 1 - current;
        }
        double endTime = System.currentTimeMillis();
        return (endTime - startTime) / (STEPS  * gameField.width() * gameField.height());
    }

    private static void copy(GameField src, GameField target) {
        for (int x = 0; x < src.width(); x++) {
            for (int y = 0; y < src.height(); y++) {
                target.set(x, y, src.get(x, y));
            }
        }
    }

    public interface Calculator {
        void calculate(GameField src, GameField target);
    }

    public enum SimpleCalculator implements Calculator {
        INSTANCE;

        @Override
        public void calculate(GameField src, GameField target) {
            GameOfLife.progress(src, target);
        }
    }

    private static class MultithreadedCalculator implements Calculator {
        final GameOfLifeExecutor executor;

        private MultithreadedCalculator(GameOfLifeExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void calculate(GameField src, GameField target) {
            try {
                executor.progress(src, target);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
