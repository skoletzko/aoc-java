package aoc.advent2025.sk;

import aoc.framework.Puzzle;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public final class Day03 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        long result = 0;
        List<int[]> banks = parseInput(input);
        for (int[] bank: banks) {
            result += this.calculateMaxBankJoltage(bank, 2);
        }

        return Long.toString(result);
    }

    @Override
    public String solvePart2(List<String> input) {
        long result = 0;
        List<int[]> banks = parseInput(input);
        for (int[] bank: banks) {
            result += this.calculateMaxBankJoltage(bank, 12);
        }

        return Long.toString(result);
    }

    private long calculateMaxBankJoltage(int[] bank, int totalNumber) {
        long result = 0;
        BatteryPosition battery = new BatteryPosition(-1,-1);
        int[] joltages = new int[totalNumber];
        for (int i = 0; i < totalNumber; i++) {
            // only consider relevant splice for next evaluation
            bank = Arrays.copyOfRange(
                bank,
                battery.getPosition() + 1,
                bank.length
            );
            battery = getNextOptimalBattery(
                bank,
                totalNumber-(i+1)
            );
            joltages[i] = battery.getJoltage();
        }

        for (int k = 0; k < totalNumber; k++) {
            result += Math.pow(10, totalNumber-(k+1)) * joltages[k];
        }
        return result;
    }

    private BatteryPosition getNextOptimalBattery(int[] bank, int remaining) {
        BatteryPosition battery = new BatteryPosition(-1, -1);
        for (int col = 0; col < bank.length - remaining; col++) {
            // we only consider the first largest value
            // choosing the battery greedy means leaving as much space
            // on the right side of the bank as possible
            if (bank[col] > battery.getJoltage()) {
                battery.set(bank[col], col);
            }
        }
        return battery;
    }

    private List<int[]> parseInput(List<String> input) {
        List<int[]> result = new ArrayList<int[]>();
        for (int i = 0; i < input.size(); i++) {
            int[] row = new int[input.get(i).length()];
            String str = input.get(i);
            for (int k = 0; k < str.length(); k++) {
                row[k] = Character.getNumericValue(str.charAt(k));
            }
            result.add(row);
        }
        return result;
    }
}

class BatteryPosition
{
    private int joltage;
    private int position;

    public BatteryPosition(int joltage, int position) {
        this.set(joltage, position);
    }
    public int getJoltage() {
        return this.joltage;
    }
    public int getPosition() {
        return this.position;
    }
    public void set(int joltage, int position) {
        this.joltage = joltage;
        this.position = position;
    }
}

