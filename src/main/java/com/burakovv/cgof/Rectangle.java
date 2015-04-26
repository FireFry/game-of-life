package com.burakovv.cgof;

public final class Rectangle {
    public final int offsetX;
    public final int offsetY;
    public final int boundX;
    public final int boundY;

    private Rectangle(int offsetX, int offsetY, int boundX, int boundY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.boundX = boundX;
        this.boundY = boundY;
    }

    public static Rectangle create(int offsetX, int offsetY, int boundX, int boundY) {
        return new Rectangle(offsetX, offsetY, boundX, boundY);
    }
}
