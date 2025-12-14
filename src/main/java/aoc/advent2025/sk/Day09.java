package aoc.advent2025.sk;

import aoc.framework.Puzzle;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ArrayDeque;

public final class Day09 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        long result = 0;
        Tile[] tiles = parseInput(input);

        for (int i = 0; i < tiles.length - 1; i++) {
            for (int k = i + 1; k < tiles.length; k++) {
                long area = Rectangle.calculateRectangle(tiles[i], tiles[k]);
                if (area > result) {
                    result = area;
                }
            }
        }
        return Long.toString(result);
    }

    @Override
    public String solvePart2(List<String> input) {
        long result = 0;
        Tile[] tiles = parseInput(input);
        // we can build a hashset of all the tiles that are 'inside' the structure
        // after that, for each reactangle we need to check if the structure is
        // a superset of that rectangle
        Structure struct = new Structure();
        for (int i = 0; i < tiles.length; i++) {
            struct.addCorner(tiles[i]);
            if (i > 0) {
                struct.addWall(tiles[i-1], tiles[i]);
            }
        }
        // connect first and last corner
        struct.addWall(tiles[0], tiles[tiles.length-1]);
        System.out.println("Finished calculating walls");

        struct.calculateTiles();
        System.out.println("Finished calculating tiles");

        for (int i = 0; i < tiles.length - 1; i++) {
            for (int k = i + 1; k < tiles.length; k++) {
                // System.out.print("Checking tiles");
                Rectangle rect = new Rectangle(tiles[i], tiles[k]);
                long area = rect.tiles.size();
                if (area <= result) {
                    continue;
                }
                if (struct.tiles.containsAll(rect.tiles)) {
                    result = area;
                }
            }
        }

        return Long.toString(result);
    }

    private Tile[] parseInput(List<String> input) {
        Tile[] tiles = new Tile[input.size()];
        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            String[] parts = s.split(",");
            Tile tile = new Tile(
                Long.parseLong(parts[0]),
                Long.parseLong(parts[1])
            );
            tiles[i] = tile;
        }
        return tiles;
    }
}

record Tile(long x, long y) {}

class Rectangle {

    public HashSet<Tile> tiles = new HashSet<Tile>();

    public static long calculateRectangle(Tile a, Tile b) {
        return (Math.abs(a.x() - b.x()) + 1)*(Math.abs(a.y() - b.y()) + 1);
    }

    public Rectangle (Tile a, Tile b) {
        long minX = Math.min(a.x(), b.x());
        long minY = Math.min(a.y(), b.y());
        long maxX = Math.max(a.x(), b.x());
        long maxY = Math.max(a.y(), b.y());

        for (long x = minX; x <= maxX; x++) {
            for (long y = minY; y <= maxY; y++) {
                this.tiles.add(new Tile(x, y));
            }
        }
    }
}

class Structure {
    public HashSet<Tile> corners = new HashSet<Tile>();
    public HashSet<Tile> walls = new HashSet<Tile>();
    public HashSet<Tile> tiles = new HashSet<Tile>();
    public HashSet<Tile> outside = new HashSet<Tile>();
    public long minX = -1;
    public long minY = -1;
    public long maxX = -1;
    public long maxY = -1;

    public void addCorner(Tile t) {
        this.corners.add(t);
    }

    public void addWall(Tile first, Tile second) {
        // vertical or horizontal wall
        if (first.x() == second.x()) {
            for (long y = Math.min(first.y(), second.y()); y <= Math.max(first.y(), second.y()); y++) {
                walls.add(new Tile(first.x(), y));
            }
        } else {
            for (long x = Math.min(first.x(), second.x()); x <= Math.max(first.x(), second.x()); x++) {
                walls.add(new Tile(x, first.y()));
            }
        }
    }

    // 'corners', 'walls', 'tiles'
    public void print(String whatToPrint) {
        if (this.minX == -1) {
            this.calculateBoundingBox();
        }
        for (long y = minY; y <= maxY; y++) {
            for (long x = minX; x <= maxX; x++) {
                Tile t = new Tile(x, y);
                switch (whatToPrint) {
                    case "corners":
                        if (corners.contains(t)) {
                            System.out.print("#");
                        } else {
                            System.out.print(".");
                        }
                        break;
                    case "walls":
                        if (walls.contains(t)) {
                            System.out.print("#");
                        } else {
                            System.out.print(".");
                        }
                        break;
                    case "tiles":
                        if (tiles.contains(t)) {
                            System.out.print("#");
                        } else {
                            System.out.print(".");
                        }
                        break;
                    case "outside":
                        if (outside.contains(t)) {
                            System.out.print("#");
                        } else {
                            System.out.print(".");
                        }
                }
            }
            System.out.println();
        }
    }

    // we'll start from the top left corner
    private void calculateOutside(Tile start) {
        long[][] directions = {
            { 1L,  0L},
            {-1L,  0L},
            { 0L,  1L},
            { 0L, -1L},
        };

        ArrayDeque<Tile> q = new ArrayDeque<>();
        q.add(start);

        while (!q.isEmpty()) {
            Tile t = q.removeFirst();

            this.outside.add(t);

            for (long[] d : directions) {
                long nx = t.x() + d[0];
                long ny = t.y() + d[1];

                if (nx < minX || nx > maxX || ny < minY || ny > maxY) continue;

                Tile next = new Tile(nx, ny);
                if (this.walls.contains(next) || this.outside.contains(next)) continue;

                q.addLast(next);
            }
        }
    }

    public void calculateTiles() {
        if (this.minX == -1) {
            this.calculateBoundingBox();
        }
        this.calculateOutside(new Tile(minX, minY));

        for (long y = minY; y <= maxY; y++) {
            for (long x = minX; x <= maxX; x++) {
                Tile t = new Tile(x, y);
                if (!this.outside.contains(t)) {
                    this.tiles.add(t);
                }
            }
        }
    }

    // moving outwards from the tile must hit a wall for every direction
    private boolean isInsideWalls(Tile t) {
        if (this.minX == -1) {
            this.calculateBoundingBox();
        }

        long[][] directions = {
            {1L, 0L},
            {-1L, 0L},
            {0L, 1L},
            {0L, -1L},
        };

        for (long[] d : directions) {
            Tile next = new Tile(t.x(), t.y());
            do {
                next = new Tile(
                    next.x() + d[0],
                    next.y() + d[1]
                );
                // check if we are already out of bounds
                if (next.x() < minX || next.x() > maxX || next.y() < minY || next.y() > maxY) {
                    return false;
                }
            } while (!this.walls.contains(next));
        }
        return true;
    }

    // smallest rectangle which encloses the structure
    // with a buffer of 1 tile on each side (to enable outer flood fill)
    public void calculateBoundingBox() {
         this.minX = this.corners.stream()
        .mapToLong(Tile::x)
        .min()
        .orElseThrow() - 1;

         this.maxX = this.corners.stream()
        .mapToLong(Tile::x)
        .max()
        .orElseThrow() + 1;

         this.minY = this.corners.stream()
        .mapToLong(Tile::y)
        .min()
        .orElseThrow() - 1;

         this.maxY = this.corners.stream()
        .mapToLong(Tile::y)
        .max()
        .orElseThrow() + 1;
    }
}

