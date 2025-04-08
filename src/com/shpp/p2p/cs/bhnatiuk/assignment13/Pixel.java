package com.shpp.p2p.cs.bhnatiuk.assignment13;

/**
 * Class for storage pixel's coordinates, for easier and more safe access to them
 */
public class Pixel {
    /** Pixel's coordinates that are stored inside the object */
    private final int x;
    private final int y;

    /**
     * Constructor of new Pixel object with defined coordinates
     */
    Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
