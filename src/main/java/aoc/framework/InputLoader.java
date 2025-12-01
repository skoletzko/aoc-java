package aoc.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public final class InputLoader {
    private InputLoader() {
    }

    public static List<String> read(int year, int day) {
        String resourcePath = String.format("inputs/%d/day%02d.txt", year, day);
        InputStream stream = InputLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Missing input file: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read input file: " + resourcePath, e);
        }
    }
}
