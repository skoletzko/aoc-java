package aoc.advent2025.md;

import java.util.List;
import java.util.ArrayList;
import aoc.framework.Puzzle;

public final class Day03 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        int result = 0;

        for (String line : input) {
            int first = Integer.parseInt(line.substring(0, 1));
            int large = 0;

            for (int i = 1; i < line.length(); i++) {
                String current = line.substring(i, i + 1);
                String largeTemp = String.valueOf(first) + String.valueOf(current);

                if (Integer.parseInt(largeTemp) > large) {
                    large = Integer.parseInt(largeTemp);
                }

                if (Integer.parseInt(current) > first) {
                    first = Integer.parseInt(current);
                }

            }
            result += large;
        }
        return String.valueOf(result);
    }

    @Override
    public String solvePart2(List<String> input) {
        return "Part2";
    }
}