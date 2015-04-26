package com.burakovv.cgof;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class GameField {
    private final int width;
    private final int height;
    private final AtomicIntegerArray data;

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
        int dataSize = dataIndex(bitIndex(width, height)) + 1;
        this.data = new AtomicIntegerArray(dataSize);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public boolean get(int x, int y) {
        int bitIndex = bitIndex(x, y);
        return (data.get(dataIndex(bitIndex)) & bitMask(bitIndex)) != 0;
    }

    public void set(int x, int y, boolean isAlive) {
        int bitIndex = bitIndex(x, y);
        int dataIndex = dataIndex(bitIndex);
        int bitMask = bitMask(bitIndex);
        for (;;) {
            int dataValue = data.get(dataIndex);
            boolean wasAlive = (dataValue & bitMask) != 0;
            if (wasAlive == isAlive || data.compareAndSet(dataIndex, dataValue, dataValue ^ bitMask)) {
                break;
            }
        }
    }

    private int bitIndex(int x, int y) {
        return x + y * width;
    }

    private int dataIndex(int bitIndex) {
        return bitIndex / Integer.SIZE;
    }

    private int bitMask(int bitIndex) {
        return 1 << bitIndex % Integer.SIZE;
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }
}
