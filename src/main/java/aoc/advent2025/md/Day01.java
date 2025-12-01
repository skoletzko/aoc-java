package aoc.advent2025.md;

import java.util.List;
import aoc.framework.Puzzle;

public final class Day01 implements Puzzle {

    @Override
    public String solvePart1(List<String> input){
        int zeroValueCount = Day01.zeroCount(input);
        return Integer.toString(zeroValueCount);
    }

    private static int zeroCount(List<String> input) {
        int current = 50;
        int zero = 0;
        for (int i = 0; i < input.size(); i++){
            String e = input.get(i);
            String turn = e.substring(0,1);
            String value = e.substring(1);
            
                       
            // left turn
            if (turn.equals("L")) {
                current = current - (Integer.parseInt(value) % 100);
                if (current < 0) {
                    current = 100 - Math.abs(current);
                }
            } else {
                current = current + (Integer.parseInt(value) % 100);
                
                if (current > 100) {
                    current = current - 100;
                }
            }

            if (current % 100 == 0) {
                current = 0;
                zero += 1;
            }
        }
        return zero;
    }

    @Override
    public String solvePart2(List<String> input){
        
        return "1";
    }

}