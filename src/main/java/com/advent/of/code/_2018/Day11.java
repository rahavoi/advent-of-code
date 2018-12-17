package com.advent.of.code._2018;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Day11 {
    private static final long puzzleInput = 6042;
    private static final int matrixSize = 300;

    Map<Pair<Integer, Integer>, Pair<Integer,Long>> regionsPower = new HashMap<>();

    long[][] matrix;

    public static void main(String[] args){
        Day11 day11 = new Day11();

        day11.task1();
    }

    private void task1(){
        initMatrix();

        //Traverse matrix Using NxN regions:
        int regionSize = 3;


        Map.Entry<Pair<Integer, Integer>, Pair<Integer,Long>> globalMax = null;

        while(regionSize < 300){
            for(int y = 0; y < matrix.length - (regionSize); y++){
                for(int x = regionSize - 1; x < matrix.length; x++){
                    long total = 0;
                    for(int i = x; i > x - regionSize; i--){
                        for(int j = y; j < y + regionSize; j++){
                            total += matrix[i][j];
                        }
                    }

                    Pair<Integer, Integer> key = new Pair<>(x - regionSize + 1, y);
                    Pair<Integer, Long> value = regionsPower.get(key);

                    if(value == null || value.getValue().compareTo(total) < 0){
                        regionsPower.put(key, new Pair<>(regionSize, total));
                    }
                }
            }
            Map.Entry<Pair<Integer, Integer>, Pair<Integer, Long>> localMax = regionsPower.entrySet().stream().max(Comparator.comparing(e -> e.getValue().getValue())).get();


            if(globalMax == null || globalMax.getValue().getValue().compareTo(localMax.getValue().getValue()) < 0){
                System.out.println("New Max: " + (localMax.getKey().getKey() + 1) + "," + (localMax.getKey().getValue() + 1));
                System.out.println("Size: " + localMax.getValue().getKey() + ", Total: " + localMax.getValue().getValue());
                globalMax = localMax;
            }

            regionSize++;
        }
    }

    private void initMatrix(){
        matrix = new long[matrixSize][matrixSize];
        for(int y = 0; y < matrixSize; y++){
            for(int x = 0; x < matrixSize; x++){
                long powerLevel = getPowerLevel(x +1, y + 1, puzzleInput);
                matrix[x][y] = powerLevel;
            }
        }
    }

    private long getPowerLevel(int x, int y, long serialNum){
        //Find the fuel cell's rack ID, which is its X coordinate plus 10.
        long rackID = x + 10;

        //Begin with a power level of the rack ID times the Y coordinate
        Long powerLevel = rackID * y;

        //Increase the power level by the value of the grid serial number (your puzzle input).
        powerLevel = powerLevel + serialNum;

        //Set the power level to itself multiplied by the rack ID
        powerLevel = powerLevel * rackID;

        String pwrLvlStr = powerLevel.toString();

        //Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
        powerLevel = pwrLvlStr.length() >= 3 ? Long.valueOf(pwrLvlStr.substring(pwrLvlStr.length() - 3, pwrLvlStr.length() - 2)) : 0;

        //Subtract 5 from the power level
        powerLevel = powerLevel - 5;

        return powerLevel;

    }
}
