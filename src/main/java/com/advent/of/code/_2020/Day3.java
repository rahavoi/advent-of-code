package com.advent.of.code._2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day3
{
    public static void main(String[] args) throws Exception {
        Day3 day3 = new Day3();
        char[][] map = day3.getMap();
        int[][] slopes = {{1,1},{3,1},{5,1},{7,1},{1,2}};
        int result = 1;

        for(int[] slope : slopes){
            result *= day3.getTreeCount(map, slope[0], slope[1]);
        }

        System.out.println(result);
    }

    private char[][] getMap() throws IOException {
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/2020/Day3.txt"));
        char[][] map = new char[input.size()][input.get(0).length()];

        for(int i = 0; i < input.size(); i++){
            map[i] = input.get(i).toCharArray();
        }

        return map;
    }

    private int getTreeCount(char[][] map, int stepsRight, int stepsDown) {
        int x = 0;
        int y = 0;
        int treeCount = 0;

        while(y < map.length - 1){
            x+= stepsRight;
            y += stepsDown;

            x = x % map[0].length;

            if(map[y][x] == '#'){
                treeCount++;
            }
        }

        return treeCount;
    }
}
