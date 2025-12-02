package aoc.advent2025.sk;
import java.util.List;
import java.util.ArrayList;

public final class Day02IdValidator {
    public static List<String> generateFromNumberRange(String numberRange) {
        String[] parts = numberRange.split("-");
        long from = Long.parseLong(parts[0]);
        long to = Long.parseLong(parts[1]);

        List<String> result = new ArrayList<String>();

        for (long i = from; i <= to; i++) {
            result.add(String.valueOf(i));
        }
        return result;
    }

    public static boolean isSequenceOfFixedPatternLength(String str, int patternLength) {
        int strlen = str.length();
        // patternLength must divide str length
        if (strlen % patternLength != 0) {
            return false;
        }

        String sequence = repeatPattern(
            str.substring(0, patternLength),
            strlen / patternLength
        );

        return str.equals(sequence);
    }

    public static boolean isSequenceOfAnyPatternLength(String str) {
        int maxPatternLength = str.length() / 2;
        for (int patternLength = 1; patternLength <= maxPatternLength; patternLength++) {
            if (isSequenceOfFixedPatternLength(str, patternLength)) {
                return true;
            }
        }
        return false;
    }

    // i.e. repeatPattern("12", 3) -> "121212"
    private static String repeatPattern(String pattern, int times) {
        StringBuilder sb = new StringBuilder(pattern.length() * times);

        for (int i = 0; i < times; i++) {
            sb.append(pattern);
        }
        return sb.toString();
    }
}

