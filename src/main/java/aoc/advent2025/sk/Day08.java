package aoc.advent2025.sk;

import aoc.framework.Puzzle;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public final class Day08 implements Puzzle {

    @Override
    public String solvePart1(List<String> input) {
        long result = 0;
        List<JunctionBox> boxes = new ArrayList<JunctionBox>();
        for (String s : input) {
            JunctionBox box = new JunctionBox(s);
            boxes.add(box);
        }

        // calculate all distances between all pairs of boxes
        List<Pair> pairs = new ArrayList<Pair>();
        for (int i = 0; i < boxes.size() - 1; i++) {
            JunctionBox first = boxes.get(i);
            for (int k = i + 1; k < boxes.size(); k++) {
                JunctionBox second = boxes.get(k);
                pairs.add(new Pair(first, second));
            }
        }
        Pair[] pairsArray = pairs.toArray(new Pair[pairs.size()]);

        Arrays.sort(pairsArray, (p1, p2) -> {
            return Long.compare(p1.distance, p2.distance);
        });

        List<Circuit> circuits = new ArrayList<Circuit>();
        int pairsConnected = 0;
        for (Pair p : pairsArray) {
            if (pairsConnected == 1000) {
                break;
            }
            JunctionBox a = p.first;
            JunctionBox b = p.second;
            // System.out.printf(
            //     "Connecting %s to %s with distance %d%n",
            //     a,
            //     b,
            //     p.distance
            // );
            pairsConnected++;
            if (a.circuit != null && b.circuit != null) {
                if (a.circuit == b.circuit) {
                    continue;
                } else {
                    // merge circuits
                    a.circuit.merge(b.circuit);
                    continue;
                }
            }
            if (a.circuit == null && b.circuit == null) {
                Circuit circuit = new Circuit();
                circuit.addBox(a);
                circuit.addBox(b);
                circuits.add(circuit);
                continue;
            }
            if (a.circuit != null) {
                a.circuit.addBox(b);
            } else {
                b.circuit.addBox(a);
            }
        }

        Circuit[] circuitArray = circuits.toArray(new Circuit[circuits.size()]);
        Arrays.sort(circuitArray, (c1, c2) -> {
            return Integer.compare(c2.size(), c1.size());
        });

        return Integer.toString(circuitArray[0].size()*circuitArray[1].size()*circuitArray[2].size());
    }

    @Override
    public String solvePart2(List<String> input) {
        long result = 0;
        return Long.toString(result);
    }
}

class Pair {
    public JunctionBox first;
    public JunctionBox second;
    public Long distance;

    public Pair(JunctionBox first, JunctionBox second) {
        this.first = first;
        this.second = second;
        this.distance = first.calculateQuadraticDistance(second);
    }
    public String toString() {
        return distance + " " + first + "<->" + second + " ";
    }
}

class Circuit {
    HashMap<String, JunctionBox> circuit = new HashMap<String, JunctionBox>();

    public void addBox(JunctionBox b) {
        this.circuit.put(b.getId(), b);
        b.setCircuit(this);
    }
    public boolean hasBox(JunctionBox b) {
        return this.circuit.containsKey(b.getId());
    }
    public void print() {
        System.out.println(this.circuit.keySet());
        // for (String id: this.circuit.keySet()) {
        //     System.out.print(id + ", ");
        // }
    }
    public int size() {
        return this.circuit.size();
    }

    public void merge(Circuit c) {
        for (JunctionBox box : this.circuit.values()) {
            c.addBox(box);
            box.setCircuit(c);
        }
        this.circuit.clear();
    }
}

class JunctionBox {
    long x;
    long y;
    long z;
    String id;
    public Circuit circuit;

    public JunctionBox(String s) {
        String[] parts = s.split(",");

        this.x = Long.parseLong(parts[0]);
        this.y = Long.parseLong(parts[1]);
        this.z = Long.parseLong(parts[2]);

        this.id = this.x + ":" + this.y + ":" + this.z;
    }

    public String getId() {
        return this.id;
    }
    public String toString() {
        return this.id;
    }

    public void setCircuit(Circuit c) {
        this.circuit = c;
    }

    public long calculateQuadraticDistance(JunctionBox box) {
        long deltaX = this.x - box.x;
        long deltaY = this.y - box.y;
        long deltaZ = this.z - box.z;

        return deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ;
    }
}
