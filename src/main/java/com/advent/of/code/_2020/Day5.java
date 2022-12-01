package com.advent.of.code._2020;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;

public class Day5
{
    public static void main(String[] args) throws Exception{
        int max = 0;
        PriorityQueue<Integer> results = new PriorityQueue<>();
        for(String line : Files.readAllLines(Paths.get("src/main/resources/2020/Day5.txt"))){
            int current = calculateId(line);
            max = Math.max(max, current);
            results.add(current);
        }
        System.out.println("Part1: " + max);

        int prev = results.poll();

        while(!results.isEmpty()){
            int cur = results.poll();
            if(cur - 1 != prev){
                System.out.println("Part2: " + (cur -1));
            }
            prev = cur;
        }
    }

    private static int  calculateId(String input){
        char[] chars = input.toCharArray();

        int low = 0;
        int high = 127;

        for(int i = 0; i < 7; i++){
            if(chars[i] == 'F'){
                high = (high + low) / 2;
            } else {
                low = (high + low) / 2 + 1;
            }
        }

        int row = high;

        low = 0;
        high = 7;

        for(int i = 7; i < chars.length; i++){
            if(chars[i] == 'L'){
                high = (high + low) / 2;
            } else {
                low = (high + low) / 2 + 1;
            }
        }

        int col = high;
        return row * 8 + col;
    }
}
