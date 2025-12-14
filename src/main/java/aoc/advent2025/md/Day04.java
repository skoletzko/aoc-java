package aoc.advent2025.md;

import java.util.List;
import java.util.ArrayList;
import aoc.framework.Puzzle;

public final class Day04 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        int x = 0;

        for (int i = 0; i < input.size(); i++) {
            String e = input.get(i).substring(0, 1);
            System.out.println(e);
        }
        return "Part1";
    }

    @Override
    public String solvePart2(List<String> input) {
        return "Part2";
    }
}
