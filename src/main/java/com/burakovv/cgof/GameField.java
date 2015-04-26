package com.burakovv.cgof;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class GameField {
    private final Rectangle area;
    private final AtomicIntegerArray data;

    public GameField(int width, int height) {
        this.area = Rectangle.create(0, 0, width, height);
        this.data = new AtomicIntegerArray(dataIndex(bitIndex(width, height)) + 1);
    }

    public Rectangle area() {
        return area;
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
        return x + y * area.boundX;
    }

    private int dataIndex(int bitIndex) {
        return bitIndex / Integer.SIZE;
    }

    private int bitMask(int bitIndex) {
        return 1 << bitIndex % Integer.SIZE;
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < area().boundX && y >= 0 && y < area().boundY;
    }
}
