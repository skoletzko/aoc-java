package aoc.advent2025.sk;

import aoc.framework.Puzzle;
import java.util.List;

public final class Day04 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        long result = 0;
        Grid grid = parseInput(input);
        //grid.draw();
        result = countRemovableRolls(grid);

        return Long.toString(result);
    }

    @Override
    public String solvePart2(List<String> input) {
        long result = 0;
        Grid grid = parseInput(input);
        result = unpackRecoursive(grid);
        return Long.toString(result);
    }

    private int countRemovableRolls(Grid grid) {
        int result = 0;
        for (int i = 0; i < grid.getRows(); i++) {
            for (int k = 0; k < grid.getCols(); k++) {
                if (grid.get(i, k) == false) {
                    continue;
                }

                if (grid.countNeighbors(i, k) < 4) {
                    result++;
                }
            }
        }
        return result;
    }

    public int unpackRecoursive(Grid grid) {
        int result = 0;
        boolean reiterate = false;
        for (int i = 0; i < grid.getRows(); i++) {
            for (int k = 0; k < grid.getCols(); k++) {
                if (grid.get(i, k) == false) {
                    continue;
                }

                if (grid.countNeighbors(i, k) < 4) {
                    result++;
                    grid.set(i, k, false);
                    // removing one roll implies we need to check
                    // the grid again
                    reiterate = true;
                }
            }
        }
        if (reiterate) {
            result += unpackRecoursive(grid);
        }
        return result;
    }

    private Grid parseInput(List<String> input) {
        int rows = input.size();
        int cols = input.get(0).length();
        Grid grid = new Grid(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                String str = input.get(i);
                if (str.charAt(k) == '@') {
                    grid.set(i, k, true);
                }
            }
        }
        return grid;
    }
}

class Grid {
    private int rows;
    private int cols;

    private static final List<int[]> DIRS = List.of(
        new int[] {0, 1},
        new int[] {0, -1},
        new int[] {1, 0},
        new int[] {-1, 0},
        new int[] {1, 1},
        new int[] {1, -1},
        new int[] {-1, 1},
        new int[] {-1, -1}
    );

    private boolean[][] grid;

    public int getRows() {
        return this.rows;
    }
    public int getCols() {
        return this.cols;
    }

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new boolean[rows][cols];
        for (int i = 0; i < this.rows; i++) {
            for (int k = 0; k < this.cols; k++) {
                this.grid[i][k] = false;
            }
        }
    }

    public int countRolls() {
        int result = 0;
        for (int i = 0; i < this.rows; i++) {
            for (int k = 0; k < this.cols; k++) {
                if (this.get(i,k)) {
                    result++;
                }
            }
        }
        return result;
    }

    public void set(int rowIdx, int colIdx, boolean val) {
        this.grid[rowIdx][colIdx] = val;
    }

    public boolean get(int rowIdx, int colIdx) {
        return this.grid[rowIdx][colIdx];
    }

    public void draw() {
        for (int i = 0; i < this.rows; i++) {
            for (int k = 0; k < this.cols; k++) {
                String c = this.get(i,k) ? "@" : "." ;
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public int countNeighbors(int rowIdx, int colIdx) {
        int result = 0;
        for (int[] dir: DIRS) {
            int targetRow = rowIdx + dir[0];
            int targetCol = colIdx + dir[1];
            if (targetRow < 0 || targetCol < 0 || targetRow >= this.rows || targetCol >= this.cols) {
                continue;
            }
            if (this.get(targetRow, targetCol)) {
                result++;
            }
        }
        return result;
    }
}

