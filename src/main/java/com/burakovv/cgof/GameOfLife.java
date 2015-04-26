package com.burakovv.cgof;

public class GameOfLife {
    private static final int[] NEIGHBOUR_DX = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] NEIGHBOUR_DY = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static final int NEIGHBOURS = NEIGHBOUR_DX.length;

    private static final boolean[] NEXT_ALIVE = {false, false, true, true, false, false, false, false, false};
    private static final boolean[] NEXT_DEAD = {false, false, false, true, false, false, false, false, false};

    private GameOfLife() {}

    public static void progress(GameField src, GameField target) {
        for (int x = 0; x < src.width(); x++) {
            for (int y = 0; y < src.height(); y++) {
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
}
