package aoc.advent2025.sk;

import aoc.framework.Puzzle;
import java.util.List;
import java.util.ArrayList;

public final class Day09 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        long result = 0;
        Tile[] tiles = new Tile[input.size()];
        for (int i = 0; i < input.size(); i++) {
            Tile tile = new Tile(input.get(i));
            tiles[i] = tile;
        }

        for (int i = 0; i < tiles.length - 1; i++) {
            for (int k = i + 1; k < tiles.length; k++) {
                long area = tiles[i].calculateRectangle(tiles[k]);
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
        return Long.toString(result);
    }
}

class Tile {
    public long x;
    public long y;

    public Tile(String s) {
        String[] parts = s.split(",");
        this.x = Long.parseLong(parts[0]);
        this.y = Long.parseLong(parts[1]);
    }
    public String toString() {
        return this.x + "," + this.y;
    }

    // the position of tile itself counts as a 1x1 rectangle
    public long calculateRectangle(Tile t) {
        return (Math.abs(this.x - t.x) + 1)*(Math.abs(this.y - t.y) + 1);
    }
}
