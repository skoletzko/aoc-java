package aoc.advent2025.sk;

public final class SafeDial {
    private int currentPosition;
    private int size;

    public SafeDial(int size, int initialPosition) {
        this.size = size;
        this.currentPosition = initialPosition;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    // use positive number for clockwise turning
    public void rotate(int steps) {
        this.currentPosition += steps;
        this.currentPosition = this.currentPosition % this.size;
    }
}
