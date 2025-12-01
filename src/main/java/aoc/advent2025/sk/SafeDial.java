package aoc.advent2025.sk;

public final class SafeDial {
    private int currentPosition;
    private int size;
    private int zeroTicks;

    public SafeDial(int size, int initialPosition) {
        this.size = size;
        this.currentPosition = initialPosition;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }
    public int getZeroTicks() {
        return this.zeroTicks;
    }

    // use positive number for clockwise turning
    public void rotate(int steps) {
        this.currentPosition += steps;
        this.currentPosition = this.currentPosition % this.size;
    }

    public void rotateCounting(int steps) {
        while (steps != 0) {
            if (steps > 0) {
                this.currentPosition -= 1;
                steps -= 1;
            } else {
                this.currentPosition += 1;
                steps += 1;
            }

            if (this.currentPosition % this.size == 0) {
                this.currentPosition = 0;
                this.zeroTicks += 1;
            }
        }
    }
}
