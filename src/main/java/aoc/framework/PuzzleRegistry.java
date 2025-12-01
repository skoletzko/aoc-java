package aoc.framework;

import aoc.advent2023.Day01;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class PuzzleRegistry {
    private final Map<String, Supplier<Puzzle>> registry = new ConcurrentHashMap<>();

    public PuzzleRegistry register(int year, int day, Supplier<Puzzle> creator) {
        registry.put(key(year, day), creator);
        return this;
    }

    public Optional<Puzzle> find(int year, int day) {
        return Optional.ofNullable(registry.get(key(year, day))).map(Supplier::get);
    }

    private static String key(int year, int day) {
        return year + ":" + day;
    }

    public static PuzzleRegistry createDefault() {
        PuzzleRegistry registry = new PuzzleRegistry();
        registry.register(2023, 1, Day01::new);
        return registry;
    }
}
