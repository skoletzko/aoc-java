package aoc.advent2025.sk;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import aoc.advent2025.sk.IdValidator;

class Day02Test {
    @Test
    void demoTestShouldFail() {
        assertFalse(1 == 2);
    }

    @Test
    void testNumberRangeParsing() {
        List<String> ids = IdValidator.generateFromNumberRange("10-14");
        assertIterableEquals(
            List.of( "10", "11", "12", "13", "14"),
            ids
        );
    }

    @Test
    void IdValidatorTest() {
        assertEquals(
            true,
            IdValidator.isSequenceOfFixedPatternLength("11111", 1)
        );
        assertEquals(
            true,
            IdValidator.isSequenceOfFixedPatternLength("1212", 2)
        );
        assertEquals(
            false,
            IdValidator.isSequenceOfFixedPatternLength("1212", 1)
        );
    }
}
