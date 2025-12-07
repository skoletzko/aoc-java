package aoc.advent2025.sk;

import aoc.framework.Puzzle;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public final class Day06 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        long result = 0;

        ProblemSet pset = new ProblemSet();
        String s = input.get(0);

        String[] problemParts = Arrays.stream(s.split(" "))
            .filter(str -> !str.isBlank())
            .toArray(String[]::new);

        for (String part : problemParts) {
            part = part.strip();

            Problem p = new Problem();
            p.addArg(Long.parseLong(part));
            pset.addProblem(p);
        }

        for (int l = 1; l < input.size() - 1; l++) {
            problemParts = Arrays.stream(input.get(l).split(" "))
                .filter(str -> !str.isBlank())
                .toArray(String[]::new);

            for (int i = 0; i < problemParts.length; i++) {
                String part = problemParts[i].strip();
                pset.getProblem(i).addArg(Long.parseLong(part));
            }
        }

        s = input.get(input.size() - 1);

        String[] operations = Arrays.stream(s.split(" "))
            .filter(str -> !str.isBlank())
            .toArray(String[]::new);

        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i].strip();
            if (operation.equals("+")) {
                pset.getProblem(i).setOp(Operation.ADD);
            } else {
                pset.getProblem(i).setOp(Operation.MUL);
            }
        }
        for (Problem p : pset.getProblems()) {
            long sol = p.calculateSolution();
            result += sol;
        }

        return Long.toString(result);
    }


    @Override
    public String solvePart2(List<String> input) {
        long result = 0;
        int rowLength = input.get(0).length();
        ProblemSet pset = new ProblemSet();
        Problem p = new Problem();
        for (int i = rowLength - 1; i > -1; i--) {
            String column = getColumnReverse(input, i);
            if (column.isBlank()) {
                pset.addProblem(p);
                p = new Problem();
                continue;
            }
            char c = column.charAt(0);
            if (c == '+' || c == '*') {
                column = column.substring(1);
                if (c == '+') {
                    p.setOp(Operation.ADD);
                } else {
                    p.setOp(Operation.MUL);
                }
            }
            String columnRev = new StringBuilder(column).reverse().toString();
            long arg = Long.parseLong(columnRev.strip());
            p.addArg(arg);
        }
        pset.addProblem(p);
        for (Problem _p : pset.getProblems()) {
            long sol = _p.calculateSolution();
            result += sol;
        }
        return Long.toString(result);
    }

    private String getColumnReverse(List<String> input, int colIdx) {
        StringBuilder sb = new StringBuilder(input.size());
        for (String row : input) {
            sb.append(row.charAt(colIdx));
        }
        return sb.reverse().toString();
    }

}

class ProblemSet {
    private List<Problem> problems;

    public ProblemSet() {
        this.problems = new ArrayList<Problem>();
    }

    public void addProblem(Problem p) {
        this.problems.add(p);
    }

    public Problem getProblem(int idx) {
        return this.problems.get(idx);
    }

    public List<Problem> getProblems() {
        return this.problems;
    }
}

class Problem {
    private List<Long> args;
    private Operation op;

    public Problem() {
        this.args = new ArrayList<Long>();
    }

    public String toString() {
        String s = "[";
        for (long arg : this.args) {
            s += arg + ",";
        }
        if (this.op == Operation.ADD) {
            s += "+";
        } else {
            s += "*";
        }
        s += "]";
        return s;
    }

    public void addArg(long arg) {
        this.args.add(arg);
    }
    public void setOp(Operation op) {
        this.op = op;
    }

    public long calculateSolution() {
        if (this.op == null) {
            throw new RuntimeException("Operation must be set for calculation");
        }
        long result = this.op == Operation.ADD ? 0 : 1;
        for (long arg : this.args) {
            if (this.op == Operation.ADD) {
                result += arg;
            } else {
                result *= arg;
            }
        }
        return result;
    }
}

enum Operation {
    ADD,
    MUL
}
