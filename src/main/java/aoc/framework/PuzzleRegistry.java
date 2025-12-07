package aoc.framework;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.Map;

public final class PuzzleRegistry {
    public static final String DEFAULT_SOLVER = "sample";

    private final Map<String, Supplier<Puzzle>> registry = new ConcurrentHashMap<>();

    public PuzzleRegistry register(int year, int day, Supplier<Puzzle> creator) {
        return register(year, DEFAULT_SOLVER, day, creator);
    }

    public PuzzleRegistry register(int year, String solver, int day, Supplier<Puzzle> creator) {
        registry.put(key(year, solver, day), creator);
        return this;
    }

    public Optional<Puzzle> find(int year, int day) {
        return find(year, DEFAULT_SOLVER, day);
    }

    public Optional<Puzzle> find(int year, String solver, int day) {
        return Optional.ofNullable(registry.get(key(year, solver, day))).map(Supplier::get);
    }

    private static String key(int year, String solver, int day) {
        return year + ":" + normalizeSolver(solver) + ":" + day;
    }

    static String normalizeSolver(String solver) {
        return solver == null ? DEFAULT_SOLVER : solver.trim().toLowerCase();
    }

    public static PuzzleRegistry createDefault() {
        PuzzleRegistry registry = new PuzzleRegistry();
        registry.register(2023, "sample", 1, aoc.advent2023.sample.Day01::new);
        registry.register(2025, "md", 1, aoc.advent2025.md.Day01::new);
        registry.register(2025, "md", 2, aoc.advent2025.md.Day02::new);
        registry.register(2025, "md", 3, aoc.advent2025.md.Day03::new);
        registry.register(2025, "sk", 1, aoc.advent2025.sk.Day01::new);
        registry.register(2025, "sk", 2, aoc.advent2025.sk.Day02::new);
        registry.register(2025, "sk", 3, aoc.advent2025.sk.Day03::new);
        registry.register(2025, "sk", 4, aoc.advent2025.sk.Day04::new);
        registry.register(2025, "sk", 5, aoc.advent2025.sk.Day05::new);
        registry.register(2025, "sk", 6, aoc.advent2025.sk.Day06::new);
        registry.register(2025, "sk", 7, aoc.advent2025.sk.Day07::new);

        return registry;
    }
}
