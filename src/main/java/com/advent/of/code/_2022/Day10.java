package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 {
    static final String NOOP = "noop";
    static final String ADD = "addx";
    static int cycleCount = 0;
    static int X = 1;
    static char[][] screen = new char[6][40];
    static List<Integer> values = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day10.txt"));
        Map<String, Integer> opsCycles = new HashMap<>();
        opsCycles.put(NOOP, 1);
        opsCycles.put(ADD, 2);

        for(String l : input){
            String[] parts = l.split(" ");
            String op = parts[0];

            switch (op){
                case NOOP:
                    cycleCount++;
                    process();
                    break;
                case ADD:
                    cycleCount++;
                    process();
                    cycleCount++;
                    process();
                    X+= Integer.parseInt(parts[1]);
                    break;
            }
        }

        long result = 0;
        for(int v : values){
            result += v;
        }

        System.out.println(result);
        for(int y = 0; y < screen.length; y++){
            for(int x = 0; x < screen[0].length; x++){
                System.out.print(screen[y][x]);
            }
            System.out.println();
        }
    }

    private static void process(){
        int line;

        if(cycleCount < 41){
            line = 0;
        } else if(cycleCount < 81){
            line = 1;
        } else if(cycleCount < 121){
            line = 2;
        } else if(cycleCount < 161){
            line = 3;
        } else if(cycleCount < 201){
            line = 4;
        } else {
            line = 5;
        }

        char[] row = screen[line];
        int pos = cycleCount - (40 * line) - 1;
        if(pos == X || pos == X - 1 || pos == X + 1){
            row[pos] = '#';
        } else {
            row[pos] = ' ';
        }

        //20th, 60th, 100th, 140th, 180th
        if(cycleCount == 20){
            values.add(X * 20);
        }

        if(cycleCount == 60){
            values.add(X * 60);
        }

        if(cycleCount == 100){
            values.add(X * 100);
        }

        if(cycleCount == 140){
            values.add(X * 140);
        }

        if(cycleCount == 180){
            values.add(X * 180);
        }

        if(cycleCount == 220){
            values.add(X * 220);
        }
    }
}
