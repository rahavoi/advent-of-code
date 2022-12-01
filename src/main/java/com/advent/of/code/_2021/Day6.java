package com.advent.of.code._2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day6 {
    public static void main(String[] args) throws Exception {
        //7 days for each cycle
        // 9 days for first cycle
        String input = "3,5,3,1,4,4,5,5,2,1,4,3,5,1,3,5,3,2,4,3,5,3,1,1,2,1,4,5,3,1,4,5,4,3,3,4,3,1,1,2,2,4,1,1,4,3,4,4,2,4,3,1,5,1,2,3,2,4,4,1,1,1,3,3,5,1,4,5,5,2,5,3,3,1,1,2,3,3,3,1,4,1,5,1,5,3,3,1,5,3,4,3,1,4,1,1,1,2,1,2,3,2,2,4,3,5,5,4,5,3,1,4,4,2,4,4,5,1,5,3,3,5,5,4,4,1,3,2,3,1,2,4,5,3,3,5,4,1,1,5,2,5,1,5,5,4,1,1,1,1,5,3,3,4,4,2,2,1,5,1,1,1,4,4,2,2,2,2,2,5,5,2,4,4,4,1,2,5,4,5,2,5,4,3,1,1,5,4,5,3,2,3,4,1,4,1,1,3,5,1,2,5,1,1,1,5,1,1,4,2,3,4,1,3,3,2,3,1,1,4,4,3,2,1,2,1,4,2,5,4,2,5,3,2,3,3,4,1,3,5,5,1,3,4,5,1,1,3,1,2,1,1,1,1,5,1,1,2,1,4,5,2,1,5,4,2,2,5,5,1,5,1,2,1,5,2,4,3,2,3,1,1,1,2,3,1,4,3,1,2,3,2,1,3,3,2,1,2,5,2";

        List<Integer> fish = Arrays.stream(input.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        Map<Integer, Long> daysToCount = new HashMap<>();

        for(Integer f : fish){
            daysToCount.put(f, daysToCount.getOrDefault(f,0L) + 1);
        }

        for(int i = 0; i < 256; i++){
            Map<Integer, Long> newMap = new HashMap<>();

            for(Integer key : daysToCount.keySet()){
                Long total = daysToCount.get(key);

                if(key > 0){
                    newMap.put( key - 1, newMap.getOrDefault(key - 1, 0L) + total);
                } else {
                    newMap.put(6, total);
                    newMap.put(8, total);
                }
            }

            daysToCount = newMap;
        }

        System.out.println(daysToCount.values().stream().mapToLong(v -> v).sum());
    }
}
