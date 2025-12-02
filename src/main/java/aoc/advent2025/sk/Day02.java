package aoc.advent2025.sk;

import java.util.List;
import aoc.framework.Puzzle;
import aoc.advent2025.sk.Day02IdValidator;

public final class Day02 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        long result = 0;

        for (String id : parseInput(input)) {
            if (id.length() % 2 == 1) continue;

            if (Day02IdValidator.isSequenceOfFixedPatternLength(id, id.length() / 2)) {
                result += Long.parseLong(id);
            }
        }
        return Long.toString(result);
    }

    @Override
    public String solvePart2(List<String> input) {
        long result = 0;

        for (String id : parseInput(input)) {
            if (Day02IdValidator.isSequenceOfAnyPatternLength(id)) {
                result += Long.parseLong(id);
            }
        }
        return Long.toString(result);
    }

    // returns list of ids
    private List<String> parseInput(List<String> input) {
        String s = input.get(0); // input contains exactly one line
        String[] numberRanges = s.split(",");

        List<String> ids = new java.util.ArrayList<>();
        for (String numberRange : numberRanges) {
            ids.addAll(Day02IdValidator.generateFromNumberRange(numberRange));
        }
        return ids;
    }
}

