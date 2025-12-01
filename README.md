# Advent of Java

This repository provides a ready-to-use Java setup for solving [Advent of Code](https://adventofcode.com/) puzzles. Everything runs inside Docker so you do not need to install a JDK, Gradle, or other Java tooling locally.

## Project layout

```
src/
  main/java/aoc/framework     // puzzle abstractions + registry
  main/java/aoc/runner        // CLI entry point
  main/java/aoc/advent2023    // yearly puzzle packages (sample Day 01 included)
  main/resources/inputs       // puzzle input files (organized by year/day)
```

Add new puzzles under `src/main/java/aoc/advent<year>/` and register them inside `aoc.framework.PuzzleRegistry`.

## Dockerized Gradle wrapper

The `gradlew` script in this repo proxies all commands through `docker compose`, using the image defined in the `Dockerfile`. You only need Docker/Compose available locally.

First build the container image:

```bash
docker compose build
```

Run Gradle tasks via the wrapper:

```bash
./gradlew clean
./gradlew test
./gradlew run --args "2023 1"           # run both parts
./gradlew run --args "2023 1 2"        # run part 2 only
```

Open a shell inside the dev container if you want an interactive session:

```bash
./gradlew shell
```

Gradle caches are stored in the named volume `gradle-cache`, so builds remain fast between runs without polluting your host machine.

## Extending for more puzzles

1. Create a new class (for example `aoc.advent2023.Day02`) that implements `aoc.framework.Puzzle`.
2. Add the input file under `src/main/resources/inputs/<year>/dayXX.txt`.
3. Register the puzzle inside `PuzzleRegistry#createDefault()`.
4. Execute it with `./gradlew run --args "<year> <day> [part]"`.
