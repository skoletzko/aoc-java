package aoc.framework;

import aoc.advent2023.sample.Day01;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

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

    private static String normalizeSolver(String solver) {
        return solver == null ? DEFAULT_SOLVER : solver.trim().toLowerCase();
    }

    public static PuzzleRegistry createDefault() {
        PuzzleRegistry registry = new PuzzleRegistry();
        registry.register(2023, "sample", 1, Day01::new);
        return registry;
    }
}
