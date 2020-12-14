package com.advent.of.code._2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 extends Day {
    public static void main(String[] args) throws Exception {
        List<String> lines = lines("src/main/resources/2020/Day13.txt");

        System.out.println("Part1: " + part1(lines));

        long start = System.currentTimeMillis();
        System.out.println("The earliest time all buses depart at offsets matching their positions is " + part2(lines));
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start) + " ms");
    }

    private static long part2(List<String> lines) {
        Map<Integer, Long> delayToBusId = new HashMap<>();

        String[] parts = lines.get(1).split(",");

        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].equals("x"))
            {
                delayToBusId.put(i + 1, Long.parseLong(parts[i]));
            }
        }

        List<Integer> orderedDelays = new ArrayList<>(delayToBusId.keySet());
        Collections.sort(orderedDelays);

        long increment = 1;
        long multiplier = 1;

        Long previousIterations = null;

        int matchAtLeast = 2;
        //TODO: There must be a better way to solve it.  ¯\_(ツ)_/¯
        outer:
        while (matchAtLeast < orderedDelays.size() + 1) {
            Long first = delayToBusId.get(orderedDelays.get(0)) * multiplier;

            if (first % delayToBusId.get(orderedDelays.get(0)) == 0) {
                for (int i = 1; i < orderedDelays.size(); i++) {
                    if (!((orderedDelays.get(i) + first) % delayToBusId.get(orderedDelays.get(i)) == 1)) {
                        multiplier += increment;
                        continue outer;
                    } else if (i == matchAtLeast - 1) {

                        if (previousIterations == null) {
                            previousIterations = multiplier;
                        }
                        else {
                            //Speed up increment
                            increment = multiplier - previousIterations;
                            matchAtLeast++;
                            previousIterations = null;
                            System.out.println(
                                i + 1 + " buses depart at matching offsets after " + multiplier + " iterations!");
                        }
                    }
                }

                return first;
            }
            else {
                multiplier += increment;
            }
        }

        throw new RuntimeException("Should never happen");
    }

    private static int part1(List<String> lines) {
        int timestamp = Integer.parseInt(lines.get(0));

        return Arrays.asList(lines.get(1)
            .split(","))
            .stream()
            .filter(s -> !s.equals("x"))
            .map(Integer::parseInt)
            .map(id -> {
                int result = id;
                while (result < timestamp)
                {
                    result += id;
                }
                Result r = new Result();
                r.id = id;
                r.timestamp = result;
                return r;
            })
            .min(Comparator.comparingInt(res -> res.timestamp))
            .map(best -> best.id * (best.timestamp - timestamp))
            .get();
    }

    static class Result
    {
        int id;
        int timestamp;
    }
}