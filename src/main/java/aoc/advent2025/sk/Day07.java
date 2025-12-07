package aoc.advent2025.sk;

import aoc.framework.Puzzle;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public final class Day07 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        int result = 0;
        int rows = input.size();
        int cols = input.get(0).length();
        LaserGrid grid = new LaserGrid(rows, cols);

        for (int i = 0; i < input.size(); i++) {
            grid.setLine(i, input.get(i));
        }
        grid.print();
        for (int k = 0; k < input.size()-1; k++) {
            grid.nextTick();
        }
        grid.print();

        return Integer.toString(grid.getSplits());
    }

    @Override
    public String solvePart2(List<String> input) {
        long result = 0;
        return Long.toString(result);
    }
}

class LaserGrid {
    private int rows;
    private int cols;
    private int currentRow;
    private int splits;
    private Entity[][] grid;

    public LaserGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.currentRow = 0;
        this.grid = new Entity[rows][cols];
    }

    public void setLine(int rowIdx, String line) {
        for (int colIdx = 0; colIdx < line.length(); colIdx++) {
            this.setEntity(rowIdx, colIdx, line.charAt(colIdx));
        }
    }

    public void print() {
        for (int i = 0; i < this.rows; i++) {
            for (int k = 0; k < this.cols; k++) {
                System.out.print(this.grid[i][k]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void nextTick() {
        this.currentRow++;
        // check if there is a beam in each column above
        // 'pull' the beam down to the current column
        for (int i = 0; i < this.cols; i++) {
            if (this.getCol(i) == Entity.BEAM || this.getCol(i) == Entity.SPLITTER) {
                continue;
            }
            Entity oneAbove = this.getColAbove(i, 1);
            if (oneAbove == Entity.BEAM) {
                this.setEntity(this.currentRow, i, '|');
            }
            if (oneAbove == Entity.SPLITTER) {
                if (this.getColAbove(i, 2) == Entity.BEAM) {
                    this.splits++;
                    this.setEntity(this.currentRow, i-1, '|');
                    this.setEntity(this.currentRow, i+1, '|');
                }
            }
        }
    }
    public int getSplits() {
        return this.splits;
    }

    private Entity getCol(int col) {
        return this.grid[this.currentRow][col];
    }

    private Entity getColAbove(int col, int offset) {
        return this.grid[this.currentRow-offset][col];
    }


    private void setEntity(int row, int col, char c) {
        if (c == 'S') {
            c = '|';
        }
        this.grid[row][col] = Entity.fromSymbol(c);
    }
}

enum Entity {
    SPACE('.'),
    BEAM('|'),
    SPLITTER('^');

    private final char symbol;

    Entity(char symbol) {
        this.symbol = symbol;
    }

    public static Entity fromSymbol(char c) {
        for (Entity t : values()) {
            if (t.symbol == c) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown symbol: " + c);
    }

    public char getSymbol() {
        return symbol;
    }

    public String toString() {
        return "" + this.getSymbol();
    }
}

