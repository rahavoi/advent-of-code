package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day1 {
    public static void main(String[] args) throws Exception{
        List<String> lines = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day1.txt"));
        long cur = 0;
        List<Long> calories = new ArrayList<>();

        for(String l : lines){
            if(l.trim().equals("")){
                calories.add(cur);
                cur = 0;
            } else {
              cur += Long.parseLong(l);
            }
        }
        System.out.println(calories.stream().mapToLong(l -> l).max().getAsLong());
        System.out.println(calories.stream().sorted(Comparator.reverseOrder()).limit(3).mapToLong(l -> l).sum());
    }
}
