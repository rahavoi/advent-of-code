package com.advent.of.code._2018;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 {
    int generationsUntilStableIncrement = 102;
    private char hasPlant = '#';
    private char empty = '.';

    private int leftMostIndex = 0;

    Map<Integer, Character> currentGenerationPot = new HashMap<>();
    Map<Integer, Character> nextGenerationPot = new HashMap<>();

    public static void main(String[] args) throws Exception {
        new Day12().task1();
    }

    private void task1() throws Exception {
        char[] initialState = "#.##.##.##.##.......###..####..#....#...#.##...##.#.####...#..##..###...##.#..#.##.#.#.#.#..####..#".toCharArray();

        for(int i = 0; i < initialState.length; i++){
            currentGenerationPot.put(i, initialState[i]);
        }

        padLeftAndRight();


        List<String> input = Files.readAllLines(Paths.get("src/main/resources/2018/Day12.txt"));

        for(int i = 0; i < generationsUntilStableIncrement; i++){
            List<Integer> indices = new ArrayList<>(currentGenerationPot.keySet());
            Collections.sort(indices);

            indices.forEach(idx -> {
                String matchingPattern = input.stream().filter(pattern -> isConditionsMet(idx, pattern)).findFirst().get();
                char result = getConditionResult(matchingPattern);
                nextGenerationPot.put(idx, result);
            });

            printSum(i);
            currentGenerationPot = nextGenerationPot;
            nextGenerationPot = new HashMap<>();
            padLeftAndRight();
        }

        Long generationsLeft = 50000000000l - generationsUntilStableIncrement;
        Long stableIncrement = 69l;
        Long sumToAdd = generationsLeft * stableIncrement;

        System.out.println("Figured out emprirically that after 102 iterations the increment is constant: 69");
        System.out.println("After 5^10 generations: " + (sumToAdd + getGenerationSum(currentGenerationPot)));
    }

    private void printSum(int generationNum) {
        Integer currentSum = getGenerationSum(currentGenerationPot);
        Integer nextSum  = getGenerationSum(nextGenerationPot);
        System.out.println("Iteration " + (generationNum + 1) + ": Current: " + currentSum + ", Next: " + nextSum + ". Diff: " + (Math.abs(currentSum - nextSum)));
    }

    private Integer getGenerationSum(Map<Integer, Character> generation){
        return generation.entrySet().stream().filter(e -> e.getValue().equals(hasPlant))
                .map(Map.Entry::getKey)
                .reduce((num1, num2) -> num1 + num2).get();
    }

    private void padLeftAndRight() {
        Integer minIndex = currentGenerationPot.entrySet().stream().min(Comparator.comparing(Map.Entry::getKey)).get().getKey();
        Integer maxIndex = currentGenerationPot.entrySet().stream().max(Comparator.comparing(Map.Entry::getKey)).get().getKey();

        //Padding left:
        currentGenerationPot.put(minIndex -1, empty);
        currentGenerationPot.put(minIndex -2, empty);
        currentGenerationPot.put(minIndex -3, empty);
        currentGenerationPot.put(minIndex -4, empty);

        //Padding right:
        currentGenerationPot.put(maxIndex +1, empty);
        currentGenerationPot.put(maxIndex +2, empty);
        currentGenerationPot.put(maxIndex +3, empty);
        currentGenerationPot.put(maxIndex +4, empty);
    }

    private boolean isConditionsMet(int index, String pattern){
        char[] conditions = pattern.toCharArray();
        Character currentPotCondition = conditions[2];
        Character firstLeftCondition = conditions[1];
        Character secondLeftCondition = conditions[0];
        Character firstRightCondition = conditions[3];
        Character secondRightCondition = conditions[4];

        return getPot(index) == currentPotCondition &&
            getPot(index - 1 ) == firstLeftCondition &&
            getPot(index - 2 ) == secondLeftCondition &&
            getPot(index + 1 ) == firstRightCondition &&
            getPot(index + 2 ) == secondRightCondition;
    }

    private char getConditionResult(String input){
        return input.toCharArray()[input.length() -1];
    }

    private void printCurrentGeneration(){
        List<Integer> indeces = new ArrayList<>(currentGenerationPot.keySet());
        Collections.sort(indeces);

        System.out.println();
        indeces.forEach(idx -> System.out.print(currentGenerationPot.get(idx)));
        System.out.println();

    }

    private Character getPot(int index){
        Character pot = currentGenerationPot.get(index);

        if(pot == null){
            pot = empty;
            nextGenerationPot.put(index, empty);
        }

        return  pot;
    }
}
