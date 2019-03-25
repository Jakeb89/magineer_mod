package magineer.util;

public class IntPair {
    public int x;
    public int y;

    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntPair(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
    }
}