package com.burakovv.cgof;

public class GameOfLife {
    private static final int[] NEIGHBOUR_DX = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] NEIGHBOUR_DY = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static final int NEIGHBOURS = NEIGHBOUR_DX.length;

    private static final boolean[] NEXT_ALIVE = {false, false, true, true, false, false, false, false, false};
    private static final boolean[] NEXT_DEAD = {false, false, false, true, false, false, false, false, false};

    private GameOfLife() {}

    public static void progress(
            GameField src,
            GameField target,
            int x,
            int y,
            int width,
            int height
    ) {
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < height; dy++) {
                int cx = x + dx;
                int cy = y + dy;
                if (src.contains(cx, cy) && target.contains(cx, cy)) {
                    target.set(cx, cy, isAliveOnNextStep(src, cx, cy));
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
