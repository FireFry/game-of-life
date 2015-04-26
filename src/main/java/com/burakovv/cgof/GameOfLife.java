package com.burakovv.cgof;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameOfLife {
    private static final int[] NEIGHBOUR_DX = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] NEIGHBOUR_DY = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static final int NEIGHBOURS = NEIGHBOUR_DX.length;

    private static final boolean[] NEXT_ALIVE = {false, false, true, true, false, false, false, false, false};
    private static final boolean[] NEXT_DEAD = {false, false, false, true, false, false, false, false, false};

    private GameOfLife() {}

    public static void progress(GameField src, GameField target) {
        progress(src, target, 0, 0, src.width(), src.height());
    }

    public static void progress(GameField src, GameField target, int offsetX, int offsetY, int width, int height) {
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < height; dy++) {
                int x = offsetX + dx;
                int y = offsetY + dy;
                if (src.contains(x, y) && target.contains(x, y)) {
                    target.set(x, y, isAliveOnNextStep(src, x, y));
                }
            }
        }
    }

    public static boolean isAliveOnNextStep(GameField src, int x, int y) {
        return isAliveOnNextStep(src.get(x, y), countNeighbours(src, x, y));
    }

    public static boolean isAliveOnNextStep(boolean isAlive, int neighbours) {
        return isAlive ? NEXT_ALIVE[neighbours] : NEXT_DEAD[neighbours];
    }

    public static int countNeighbours(GameField src, int x, int y) {
        int count = 0;
        for (int d = 0; d < NEIGHBOURS; d++) {
            int nx = x + NEIGHBOUR_DX[d];
            int ny = y + NEIGHBOUR_DY[d];
            if (src.contains(nx, ny) && src.get(nx, ny)) {
                count++;
            }
        }
        return count;
    }

    public static void randomize(GameField gameField) {
        randomize(gameField, ThreadLocalRandom.current());
    }

    public static void randomize(GameField gameField, Random random) {
        for (int x = 0; x < gameField.width(); x++) {
            for (int y = 0; y < gameField.height(); y++) {
                gameField.set(x, y, random.nextBoolean());
            }
        }
    }
}
