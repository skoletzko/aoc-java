package aoc.advent2025.sk;

import java.util.List;
import aoc.framework.Puzzle;
import aoc.advent2025.sk.SafeDial;

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

