package com.advent.of.code._2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 extends Day {
    public static void main(String[] args) throws IOException {
        int[] n = {9,3,1,0,8,4};

        long start = System.currentTimeMillis();
        Map<Integer, Integer> numToAge = new HashMap<>();


        List<Integer> nums = new ArrayList<>(30000000);

        for(int i = 0; i < n.length;i++){
            nums.add(n[i]);
            numToAge.put(n[i], i + 1);
        }

        numToAge.remove(nums.get(nums.size() - 1));

        //30million iterations? pfft.. It takes 6 seconds.. Too lazy to look for optimal solution at 1am. Maybe later :)
        int iterations = 30000000;
        for(int i = nums.size() - 1; i < iterations; i++){
            int curr = nums.get(i);
            if(!numToAge.containsKey(curr)){
                numToAge.put(curr, i + 1);

                nums.add(0);
            } else{
                int next = i + 1 - numToAge.get(curr);

                if(i == 2020 - 2){
                    System.out.println("Part1: " + next);
                }

                if(i > iterations - 2){
                    System.out.println("Part2: " + next);
                }

                numToAge.put(curr, i + 1);
                nums.add(next);
            }
        }

        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");
    }
}
