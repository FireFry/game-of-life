package com.burakovv.cgof;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameOfLifeExecutor {
    private static final int AREA_SIDE = 64;

    private final ExecutorService executor;

    public static GameOfLifeExecutor create() {
        return createFixedThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
    }

    public static GameOfLifeExecutor createFixedThreadPoolExecutor(int nThreads) {
        return new GameOfLifeExecutor(Executors.newFixedThreadPool(nThreads));
    }

    public GameOfLifeExecutor(ExecutorService executorService) {
        executor = executorService;
    }

    public void progress(GameField src, GameField target) throws InterruptedException {
        int dxSize = (src.width() + AREA_SIDE - 1) / AREA_SIDE;
        int dySize = (src.height() + AREA_SIDE - 1) / AREA_SIDE;
        CountDownLatch countDownLatch = new CountDownLatch(dxSize * dySize);
        for (int dx = 0; dx < dxSize; dx++) {
            for (int dy = 0; dy < dySize; dy++) {
                final int offsetX = dx * AREA_SIDE;
                final int offsetY = dy * AREA_SIDE;
                executor.submit(() -> {
                    GameOfLife.progress(src, target, offsetX, offsetY, AREA_SIDE, AREA_SIDE);
                    countDownLatch.countDown();
                });
            }
        }
        countDownLatch.await();
    }

    public void stop() {
        executor.shutdown();
    }
}
