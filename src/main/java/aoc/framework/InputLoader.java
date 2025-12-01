package aoc.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class InputLoader {
    private InputLoader() {
    }

    public static List<String> read(int year, int day) {
        return read(year, PuzzleRegistry.DEFAULT_SOLVER, day);
    }

    public static List<String> read(int year, String solver, int day) {
        String normalizedSolver = PuzzleRegistry.normalizeSolver(solver);

        String solverPath = String.format("inputs/%d/%s/day%02d.txt", year, normalizedSolver, day);
        Optional<InputStream> stream = tryLoad(solverPath);
        if (stream.isPresent()) {
            return readAll(stream.get(), solverPath);
        }

        String legacyPath = String.format("inputs/%d/day%02d.txt", year, day);
        stream = tryLoad(legacyPath);
        if (stream.isPresent()) {
            return readAll(stream.get(), legacyPath);
        }

        throw new IllegalArgumentException(
                "Missing input file for %d day %02d (%s)".formatted(year, day, normalizedSolver));
    }

    private static Optional<InputStream> tryLoad(String resourcePath) {
        return Optional.ofNullable(InputLoader.class.getClassLoader().getResourceAsStream(resourcePath));
    }

    private static List<String> readAll(InputStream stream, String resourcePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read input file: " + resourcePath, e);
        }
    }
}
