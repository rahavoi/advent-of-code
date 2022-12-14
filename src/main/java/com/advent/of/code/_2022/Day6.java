package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Day6 {
    public static void main(String[] args) throws Exception {
        String input = Files.readString(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day6.txt"));

        for(int i = 13; i < input.length(); i++){
            Set<Character> unique = new HashSet<>();
            for(int j = 0; j < 14; j++){
                unique.add(input.charAt(i - j));
            }

            if(unique.size() == 14){
                System.out.println(i + 1);
                break;
            }
        }
    }
}
