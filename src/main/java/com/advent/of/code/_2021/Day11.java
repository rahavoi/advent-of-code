package com.advent.of.code._2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.advent.of.code._2021.Day9.Pair;

public class Day11 {
    static int totalFlashes = 0;
    public static void main(String[] args) throws Exception {
        int[][] data = parseData();

        for(int i = 0; i < Integer.MAX_VALUE; i++){
            Set<Pair> flashed = step(data);

            if(i == 99){
                System.out.println("Part 1. Total flashes after 100 iterations: " + totalFlashes);
            }

            if(flashed.size() == data.length * data[0].length){
                System.out.println("Part 2. All flashed after " + (i + 1) + " iterations");
                break;
            }

            flashed.forEach(p -> data[p.i][p.j] = 0);
        }
    }

    private static int[][] parseData() throws IOException {
        List<String> input = Files.readAllLines(Paths.get(
            "/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day11.txt"));

        int[][] data = new int[input.size()][input.get(0).length()];

        for(int i = 0; i < input.size(); i++){
            for(int j = 0; j < input.get(0).length(); j++){
                data[i][j] = Character.getNumericValue(input.get(i).charAt(j));
            }
        }
        return data;
    }

    public static Set<Pair> step(int[][] data){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = data[i][j] + 1;
            }
        }

        Set<Pair> flashed = new HashSet<>();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if(data[i][j] > 9 && !flashed.contains(new Pair(i, j))){
                    //flash!
                    totalFlashes++;
                    flashed.add(new Pair(i, j));
                    increaseNeighbors(i, j, data, flashed);
                }
            }
        }

        return flashed;
    }

    public static void increaseNeighbors(int i, int j, int[][] data, Set<Pair> flashed){
        increaseNeighbor(i - 1, j, data, flashed);
        increaseNeighbor(i - 1, j + 1, data, flashed);
        increaseNeighbor(i, j + 1, data, flashed);
        increaseNeighbor(i + 1, j + 1, data, flashed);
        increaseNeighbor(i + 1, j, data, flashed);
        increaseNeighbor(i + 1, j - 1, data, flashed);
        increaseNeighbor(i, j - 1, data, flashed);
        increaseNeighbor(i - 1, j - 1, data, flashed);
    }

    private static void increaseNeighbor(int i, int j, int[][] data, Set<Pair> flashed){
        Pair p = new Pair(i, j);

        if(!flashed.contains(p)){
            if(i >= 0 && i < data.length && j >= 0 && j < data[0].length && !flashed.contains(p)){
                data[i][j] = data[i][j] + 1;

                if(data[i][j] > 9 && !flashed.contains(p)){
                    totalFlashes++;
                    flashed.add(p);
                    increaseNeighbors(i, j, data, flashed);
                }
            }
        }
    }
}
