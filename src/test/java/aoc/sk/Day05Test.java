package aoc.advent2025.sk;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import aoc.advent2025.sk.Range;
import aoc.advent2025.sk.Database;

class Day05Test {

    @Test
    void testRangeChecks() {
        Range r1_4 = new Range(1,4);
        Range r3_6 = new Range(3,6);
        Range r2_4 = new Range(2,4);
        Range r5_6 = new Range(5,6);

        assertFalse(r1_4.contains(r3_6));
        assertTrue(r1_4.contains(r2_4));

        assertTrue(r1_4.intersects(r3_6));
        assertTrue(r3_6.intersects(r1_4));

        assertFalse(r2_4.intersects(r5_6));
        assertFalse(r5_6.intersects(r2_4));
    }

    @Test
    void testIntersection() {
        Range r1_4 = new Range(1,4);
        Range r3_6 = new Range(3,6);

        Range i1 = r1_4.getIntersection(r3_6);
        System.out.println(i1);
        assertTrue(i1.equals(new Range(3,4)));

        Range i2 = r3_6.getIntersection(r1_4);
        System.out.println(i2);
        assertTrue(i2.equals(new Range(3,4)));
    }
}


