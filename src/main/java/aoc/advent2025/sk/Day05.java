package aoc.advent2025.sk;

import aoc.framework.Puzzle;
import java.util.List;
import java.util.ArrayList;

public final class Day05 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        Database db = new Database();
        for (String line : input) {
            if (line.equals("")) {
                continue;
            }
            if (line.contains("-")) {
                db.addRange(line);
            } else {
                db.addId(line);
            }
        }
        db.pruneRanges();
        return Long.toString(db.countFreshIds());
    }

    @Override
    public String solvePart2(List<String> input) {
        long result = 0;
        Database db = new Database();
        for (String line : input) {
            if (line.equals("")) {
                continue;
            }
            if (line.contains("-")) {
                db.addRange(line);
            }
        }
        db.pruneRanges();

        // we are overcounting the intersections
        for (Range range : db.getRanges()) {
            result += range.size();
        }

        // get all intersections and prune them
        Database intersectionDb = db.getIntersectionRanges();
        intersectionDb.pruneRanges();

        // subtract the overcounted intersections from the result
        for (Range rangeI : intersectionDb.getRanges()) {
            result -= rangeI.size();
        }

        return Long.toString(result);
    }
}

class Database {
    List<Range> ranges;
    List<Long> ids;

    public Database() {
        this.ranges = new ArrayList<Range>();
        this.ids = new ArrayList<Long>();
    }

    public void addRange(String sRange) {
        String[] parts = sRange.split("-");
        String from = parts[0];
        String to = parts[1];

        this.ranges.add(
            new Range(
                Long.parseLong(from),
                Long.parseLong(to)
            )
        );
    }

    public void addId(String sId) {
        this.ids.add(Long.parseLong(sId));
    }

    public List<Long> getIds() {
        return this.ids;
    }
    public List<Range> getRanges() {
        return this.ranges;
    }

    public long countFreshIds() {
        long result = 0;
        for (long id : this.ids) {
            if (this.isFresh(id)) {
                result++;
            }
        }
        return result;
    }

    // we need to splice the ranges such that there is no overlap
    // counting the elements inside the ranges is not feasible
    public long countMaxFreshIds() {
        long result = 0;
        for (Range range : this.ranges) {
        }
        return result;
    }

    public boolean isFresh(long id) {
        for (Range range : this.ranges) {
            if (range.isInRange(id)) {
                return true;
            }
        }
        return false;
    }

    // removes all ranges that are fully contained in another range
    public void pruneRanges() {
        for (int i = 0; i < this.ranges.size() - 1; i++) {
            Range r1 = this.ranges.get(i);
            for (int k = i + 1; k < this.ranges.size(); k++) {
                Range r2 = this.ranges.get(k);
                if (r1.contains(r2)) {
                    r2.flagAsSubRange();
                } else if (r2.contains(r1)) {
                    r1.flagAsSubRange();
                }
            }
        }
        this.ranges.removeIf(n -> n.isSubRange);
    }


    public Database getIntersectionRanges() {
        Database db = new Database();
        for (int i = 0; i < this.ranges.size() - 1; i++) {
            Range r1 = this.ranges.get(i);
            for (int k = i + 1; k < this.ranges.size(); k++) {
                Range r2 = this.ranges.get(k);
                if (r1.intersects(r2)) {
                    db.addRange(r1.getIntersection(r2).toString());
                }
            }
        }
        return db;
    }

    // DEBUG
    public void testForIntersectingRanges() {
        for (int i = 0; i < this.ranges.size() - 1; i++) {
            Range r1 = this.ranges.get(i);
            for (int k = i + 1; k < this.ranges.size(); k++) {
                Range r2 = this.ranges.get(k);
                if (r1.intersects(r2)) {
                    System.out.printf(
                        "[%d-%d] intersects [%d-%d]%n",
                        r1.getFrom(),
                        r1.getTo(),
                        r2.getFrom(),
                        r2.getTo()
                    );
                }
            }
        }
    }

    // DEBUG
    public void testForContainingRanges() {
        for (int i = 0; i < this.ranges.size() - 1; i++) {
            for (int k = i + 1; k < this.ranges.size(); k++) {
                Range r1 = this.ranges.get(i);
                Range r2 = this.ranges.get(k);
                if (r1.contains(r2)) {
                    System.out.printf(
                        "[%d-%d] contains [%d-%d]%n",
                        r1.getFrom(),
                        r1.getTo(),
                        r2.getFrom(),
                        r2.getTo()
                    );
                }
                if (r2.contains(r1)) {
                    System.out.printf(
                        "[%d-%d] contains [%d-%d]%n",
                        r2.getFrom(),
                        r2.getTo(),
                        r1.getFrom(),
                        r1.getTo()
                    );
                }
            }
        }
    }
}

class Range {
    private long from;
    private long to;
    // flag to indicate weather the Range is contained inside another
    // range, for pruning the list
    public boolean isSubRange = false;

    public Range(long a, long b) {
        if (a < b) {
            this.from = a;
            this.to = b;
        } else {
            this.from = b;
            this.to = a;
        }
    }

    public long size() {
        return this.to - this.from + 1;
    }

    public boolean equals(Range r) {
        return (this.getFrom() == r.getFrom() && this.getTo() == r.getTo());
    }
    public String toString() {
        return this.from + "-" + this.to;
    }

    public void flagAsSubRange() {
        this.isSubRange = true;
    }
    public long getFrom() {
        return this.from;
    }
    public long getTo() {
        return this.to;
    }

    public boolean contains(Range r) {
        return this.getFrom() <= r.getFrom() && this.getTo() >= r.getTo();
    }

    public boolean intersects(Range r) {
        long start = Math.max(this.getFrom(), r.getFrom());
        long end = Math.min(this.getTo(), r.getTo());
        return start <= end;
    }

    public Range getIntersection(Range r) {
        long start = Math.max(this.getFrom(), r.getFrom());
        long end = Math.min(this.getTo(), r.getTo());
        if (start <= end) {
            return new Range(start, end);
        }
        return null;
    }

    public boolean isInRange(long $candidate) {
        return $candidate >= this.from && $candidate <= this.to;
    }
}

