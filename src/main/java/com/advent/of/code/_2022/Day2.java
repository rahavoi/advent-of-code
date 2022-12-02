package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 {
    public static void main(String[] args) throws Exception{
        List<String> lines = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day2.txt"));

        Map<Character, Integer> weights = new HashMap<>();
        weights.put('A', 1);
        weights.put('B', 2);
        weights.put('C', 3);

        Map<Character, Character> winningOptions = new HashMap<>();
        winningOptions.put('A', 'B');
        winningOptions.put('B', 'C');
        winningOptions.put('C', 'A');

        Map<Character, Character> losingOptions = new HashMap<>();
        losingOptions.put('A', 'C');
        losingOptions.put('B', 'A');
        losingOptions.put('C', 'B');

        int total = lines.stream().mapToInt(l -> {
            int score;
            char p1 = l.charAt(0);
            char p2 = l.charAt(2);

            switch (p2){
                case 'Z': //win
                    score = weights.get(winningOptions.get(p1));
                    score+=6;
                    break;
                case 'Y': //draw
                    score = (weights.get(p1) + 3);
                    break;
                default: //lose
                    score = weights.get(losingOptions.get(p1));
                    break;
            }
            return score;
        }).sum();

        System.out.println(total);
    }
}
