package aoc.runner;

import aoc.framework.Puzzle;
import aoc.framework.PuzzleRegistry;
import java.util.List;
import java.util.function.Supplier;
import aoc.framework.InputLoader;


public final class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            System.exit(1);
        }

        int year = parseNumber(args[0], "year");
        int day = parseNumber(args[1], "day");
        String part = args.length >= 3 ? args[2].toLowerCase() : "both";

        Puzzle puzzle = PuzzleRegistry.createDefault()
                .find(year, day)
                .orElseThrow(() -> new IllegalArgumentException("No puzzle registered for %d day %d".formatted(year, day)));

        List<String> input = InputLoader.read(year, day);

        switch (part) {
            case "1", "part1" -> runPart("Part 1", () -> puzzle.solvePart1(input));
            case "2", "part2" -> runPart("Part 2", () -> puzzle.solvePart2(input));
            case "both" -> {
                runPart("Part 1", () -> puzzle.solvePart1(input));
                runPart("Part 2", () -> puzzle.solvePart2(input));
            }
            default -> {
                System.err.println("Unknown part: " + part);
                printUsage();
                System.exit(1);
            }
        }
    }

    private static void runPart(String label, Supplier<String> supplier) {
        String result = supplier.get();
        System.out.println(label + ": " + result);
    }

    private static int parseNumber(String raw, String field) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid %s: %s".formatted(field, raw), e);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar advent-of-java.jar <year> <day> [part]");
        System.out.println("  part can be 1, 2, or omitted (run both).");
    }
}
