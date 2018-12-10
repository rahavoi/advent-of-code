package com.advent.of.code._2017;

import java.util.HashMap;
import java.util.Map;

public class Day25 {
    public static void main(String[] args) {
        int steps = 12386363;
        String currentState = "A";

        int pos = 0;
        Map<Integer, Integer> tape = new HashMap<>();

        for (int i = 0; i < steps; i++) {
            switch (currentState) {
            case "A":
                if (tape.get(pos) == null || tape.get(pos) == 0) {
                    tape.put(pos, 1);
                    pos++;
                    currentState = "B";
                } else {
                    tape.put(pos, 0);
                    pos--;
                    currentState = "E";
                }
                break;
            case "B":
                if (tape.get(pos) == null || tape.get(pos) == 0) {
                    tape.put(pos, 1);
                    pos--;
                    currentState = "C";
                } else {
                    tape.put(pos, 0);
                    pos++;
                    currentState = "A";
                }
                break;
            case "C":
                if (tape.get(pos) == null || tape.get(pos) == 0) {
                    tape.put(pos, 1);
                    pos--;
                    currentState = "D";
                } else {
                    tape.put(pos, 0);
                    pos++;
                    currentState = "C";
                }
                break;
            case "D":
                if (tape.get(pos) == null || tape.get(pos) == 0) {
                    tape.put(pos, 1);
                    pos--;
                    currentState = "E";
                } else {
                    tape.put(pos, 0);
                    pos--;
                    currentState = "F";
                }
                break;
            case "E":
                if (tape.get(pos) == null || tape.get(pos) == 0) {
                    tape.put(pos, 1);
                    pos--;
                    currentState = "A";
                } else {
                    tape.put(pos, 1);
                    pos--;
                    currentState = "C";
                }
                break;
            case "F":
                if (tape.get(pos) == null || tape.get(pos) == 0) {
                    tape.put(pos, 1);
                    pos--;
                    currentState = "E";
                } else {
                    tape.put(pos, 1);
                    pos++;
                    currentState = "A";
                }
                break;

            default:
                throw new IllegalStateException("Turing machine is broken!");
            }
        }

        System.out.println("Checksum: " + tape.values().stream().mapToInt(i -> i).sum());
    }

}
