package com.burakovv.cgof;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class GameField {
    private final int width;
    private final int height;
    private final AtomicIntegerArray data;

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new AtomicIntegerArray(dataIndex(bitIndex(width, height)) + 1);
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
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(getClass().getSimpleName());
        b.append(':');
        b.append(System.lineSeparator());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                b.append(get(x, y) ? '0' : '.');
            }
            b.append(System.lineSeparator());
        }
        return b.toString();
    }
}
