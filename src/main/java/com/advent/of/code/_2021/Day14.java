package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Day14 {
    private static Map<String, String> rules = new HashMap<>();
    private static Map<Character, Long> charOccurrences = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day14.txt"))
            .forEach(l ->{
                String[] parts = l.split(" -> ");
                rules.put(parts[0], parts[1]);
            });

        String input = "NCOPHKVONVPNSKSHBNPF";

        for(char c : input.toCharArray()){
            charOccurrences.put(c, charOccurrences.getOrDefault(c, 0L) + 1);
        }


        Map<String, Long> combinations = new HashMap<>();

        for(int i = 0; i < input.length() - 1; i++){
            StringBuilder temp = new StringBuilder();
            temp.append(input.charAt(i));
            temp.append(input.charAt(i + 1));

            combinations.put(temp.toString(), combinations.getOrDefault(temp.toString(), 0L) + 1);
        }

        for(int i = 0; i < 40; i++){
            combinations = polymerize(combinations);

            //Part1:
            if(i + 1 == 10){
                printDiffBetweenMaxAndMin();
            }
        }

        printDiffBetweenMaxAndMin();
        //naivePart1(rules, input);
    }

    private static void printDiffBetweenMaxAndMin() {
        long min = charOccurrences.entrySet().stream().mapToLong(Map.Entry::getValue).min().getAsLong();
        long max = charOccurrences.entrySet().stream().mapToLong(Map.Entry::getValue).max().getAsLong();
        System.out.println(max - min);
    }

    private static Map<String, Long> polymerize(Map<String, Long> input){
        Map<String, Long> polymer = new HashMap<>();

        input.entrySet().forEach(e -> {
            String combo = e.getKey();
            Long times = e.getValue();

            String reactionResult = rules.get(combo);
            charOccurrences.put(reactionResult.charAt(0), charOccurrences.getOrDefault(reactionResult.charAt(0), 0L) + times);
            String one = new StringBuilder().append(combo.charAt(0)).append(reactionResult).toString();
            String two = new StringBuilder().append(reactionResult).append(combo.charAt(1)).toString();;

            polymer.put(one, polymer.getOrDefault(one, 0L) + times);
            polymer.put(two, polymer.getOrDefault(two, 0L) + times);

        });

        return polymer;
    }

    private static void naivePart1(Map<String, String> rules, String input) {
        for(int i = 0; i < 10; i++){
            System.out.println("Iteration: " + (i + 1));
            input = naivePolymerize(rules, input);
        }

        Map<Character, Long> occurrences = new HashMap<>();

        for(char c : input.toCharArray()){
            occurrences.put(c, occurrences.getOrDefault(c, 0L) + 1);
        }

        long max = occurrences.entrySet().stream().mapToLong(Map.Entry::getValue).max().getAsLong();
        long min = occurrences.entrySet().stream().mapToLong(Map.Entry::getValue).min().getAsLong();

        System.out.println("Min: " + min);
        System.out.println("Max: " + max);

        System.out.println(max - min);
    }

    private static String naivePolymerize(Map<String, String> rules, String input) {
        StringBuilder result = new StringBuilder();
        result.append(input.charAt(0));

        for(int i = 0; i < input.length() - 1; i++){
            StringBuilder temp = new StringBuilder();
            temp.append(input.charAt(i));
            temp.append(input.charAt(i + 1));

            String reactionResult = rules.get(temp.toString());
            result.append(reactionResult);
            result.append(input.charAt(i + 1));
        }
        return result.toString();
    }
}
