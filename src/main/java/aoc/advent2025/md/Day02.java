package aoc.advent2025.md;

import java.util.List;
import java.util.ArrayList;
import aoc.framework.Puzzle;

public final class Day02 implements Puzzle {

    @Override
    public String solvePart1(List<String> input){
        long invalidID = 0;
        for(int i=0;i<input.size();i++){
            // generating commaSeparated list out of input string
            String [] commaSeparated = input.get(i).split(",");
            for (String s : commaSeparated) {
                // generating the number ranges from separated string
                String [] ranges = s.split("-");
                for (long r=Long.parseLong(ranges[0]); r<=Long.parseLong(ranges[1]); r++) {
                    // checking for uneven numbers
                    if (String.valueOf(r).length() % 2 != 0) {
                        continue;
                    }
                    int halfNumLength = String.valueOf(r).length()/2;
                    String firstHalf = Long.toString(r).substring(0, halfNumLength);
                    String secondHalf = Long.toString(r).substring(halfNumLength,String.valueOf(r).length());

                    if (firstHalf.equals(secondHalf)) {
                        invalidID += r;
                    }
                }
            }
            
        }
        return Long.toString(invalidID);
    }

    @Override
    public String solvePart2(List<String> input){
        return "Part2";
    }
}