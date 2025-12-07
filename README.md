# Advent of Java

This repository provides a ready-to-use Java setup for solving [Advent of Code](https://adventofcode.com/) puzzles. Everything runs inside Docker so you do not need to install a JDK, Gradle, or other Java tooling locally.

## Project layout

```
src/
  main/java/aoc/framework     // puzzle abstractions + registry
  main/java/aoc/runner        // CLI entry point
  main/java/aoc/advent2023    // yearly puzzle packages (teams organized per member)
    sample/                   // example teammate namespace
  main/resources/inputs       // puzzle input files (organized by year/solver/day)
```

Add new puzzles under `src/main/java/aoc/advent<year>/<solver>/` (one folder per contributor) and register them inside `aoc.framework.PuzzleRegistry`.

Run puzzles with:

```bash
./gradlew run --args "<year> [solver] <day> [part]"
```

If you omit `solver` the CLI falls back to the registry's default solver name (currently `sample`).

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
./gradlew run --args "2023 sample 1"    # run both parts for solver "sample"
./gradlew run --args "2023 sample 1 2" # run part 2 only
```

If you add the test data to a special txt file (e.g. day03-test.txt) you can add this command

```bash
./gradlew run --args "2025 sample 1 both true"
``` 
This will execute the command with the test data provided.

Open a shell inside the dev container if you want an interactive session:

```bash
./gradlew shell
```

Gradle caches are stored in the named volume `gradle-cache`, so builds remain fast between runs without polluting your host machine.

## Extending for more puzzles

1. Create a new class (for example `aoc.advent2023.alice.Day02`) that implements `aoc.framework.Puzzle`.
2. Add the input file under `src/main/resources/inputs/<year>/<solver>/dayXX.txt` (legacy `inputs/<year>/dayXX.txt` paths still load, but prefer solver folders).
3. Register the puzzle inside `PuzzleRegistry#createDefault()` using `registry.register(year, "alice", day, AliceDay::new)`.
4. Execute it with `./gradlew run --args "<year> alice <day> [part]"`.
