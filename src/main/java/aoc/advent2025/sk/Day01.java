package aoc.advent2025.sk;

import java.util.List;
import aoc.framework.Puzzle;

public final class Day01 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {

        SafeDial safeDial = new SafeDial(100, 50);
        int result = 0;

        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            String direction = s.substring(0,1); // "L" | "R"
            int count = Integer.parseInt(s.substring(1));
            if (direction.equals("L")) {
                count = (-1)*count;
            }

            safeDial.rotate(count);
            if (safeDial.getCurrentPosition() == 0) {
                result += 1;
            }
        }

        return Integer.toString(result);
    }

    @Override
    public String solvePart2(List<String> input) {
        SafeDial safeDial = new SafeDial(100, 50);

        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            String direction = s.substring(0,1); // "L" | "R"
            int count = Integer.parseInt(s.substring(1));
            if (direction.equals("L")) {
                count = (-1)*count;
            }

            safeDial.rotateCounting(count);
        }
        return Integer.toString(safeDial.getZeroTicks());
    }
}

class SafeDial {
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
