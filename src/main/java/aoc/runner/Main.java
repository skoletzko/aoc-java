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

        ParsedArguments parsed;
        try {
            parsed = parseArguments(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            printUsage();
            System.exit(1);
            return;
        }

        Puzzle puzzle = PuzzleRegistry.createDefault()
                .find(parsed.year(), parsed.solver(), parsed.day())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No puzzle registered for %d day %d (%s)".formatted(parsed.year(), parsed.day(), parsed.solver())));

        List<String> input = InputLoader.read(parsed.year(), parsed.day());

        switch (parsed.part()) {
            case "1", "part1" -> runPart("Part 1", () -> puzzle.solvePart1(input));
            case "2", "part2" -> runPart("Part 2", () -> puzzle.solvePart2(input));
            case "both" -> {
                runPart("Part 1", () -> puzzle.solvePart1(input));
                runPart("Part 2", () -> puzzle.solvePart2(input));
            }
            default -> {
                System.err.println("Unknown part: " + parsed.part());
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

    private static ParsedArguments parseArguments(String[] args) {
        int year = parseNumber(args[0], "year");

        String solver;
        int dayIndex;
        if (args.length >= 3 && !isNumber(args[1])) {
            solver = args[1];
            dayIndex = 2;
        } else {
            solver = PuzzleRegistry.DEFAULT_SOLVER;
            dayIndex = 1;
        }

        if (args.length <= dayIndex) {
            throw new IllegalArgumentException("Missing day argument");
        }

        int day = parseNumber(args[dayIndex], "day");
        int partIndex = dayIndex + 1;
        String part = args.length > partIndex ? args[partIndex].toLowerCase() : "both";

        return new ParsedArguments(year, solver, day, part);
    }

    private static boolean isNumber(String raw) {
        if (raw == null || raw.isBlank()) {
            return false;
        }
        for (int i = 0; i < raw.length(); i++) {
            if (!Character.isDigit(raw.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private record ParsedArguments(int year, String solver, int day, String part) {}

    private static void printUsage() {
        System.out.println("Usage: java -jar advent-of-java.jar <year> [solver] <day> [part]");
        System.out.println("  solver is optional and defaults to '" + PuzzleRegistry.DEFAULT_SOLVER + "'.");
        System.out.println("  part can be 1, 2, or omitted (run both).");
    }
}
