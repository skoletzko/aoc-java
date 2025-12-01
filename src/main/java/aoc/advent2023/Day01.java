package aoc.advent2023;

import java.util.ArrayList;
import java.util.List;
import aoc.framework.Puzzle;


public final class Day01 implements Puzzle {
    private static final String[] SPELLED_DIGITS = {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };

    @Override
    public String solvePart1(List<String> input) {
        int sum = input.stream().mapToInt(Day01::digitsOnlyValue).sum();
        return Integer.toString(sum);
    }

    @Override
    public String solvePart2(List<String> input) {
        int sum = input.stream().mapToInt(Day01::digitsOrWordsValue).sum();
        return Integer.toString(sum);
    }

    private static int digitsOnlyValue(String line) {
        int first = -1;
        int last = -1;
        for (char c : line.toCharArray()) {
            if (Character.isDigit(c)) {
                int digit = Character.digit(c, 10);
                if (first < 0) {
                    first = digit;
                }
                last = digit;
            }
        }
        if (first < 0) {
            throw new IllegalArgumentException("Line without digits: " + line);
        }
        return first * 10 + last;
    }

    private static int digitsOrWordsValue(String line) {
        List<Integer> digits = new ArrayList<>();
        char[] chars = line.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isDigit(c)) {
                digits.add(Character.digit(c, 10));
                continue;
            }
            for (int digit = 0; digit < SPELLED_DIGITS.length; digit++) {
                String word = SPELLED_DIGITS[digit];
                if (line.regionMatches(i, word, 0, word.length())) {
                    digits.add(digit);
                    break;
                }
            }
        }
        if (digits.isEmpty()) {
            throw new IllegalArgumentException("Line without digits: " + line);
        }
        int first = digits.get(0);
        int last = digits.get(digits.size() - 1);
        return first * 10 + last;
    }
}
