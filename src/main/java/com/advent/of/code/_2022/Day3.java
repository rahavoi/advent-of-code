package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day3 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day3.txt"));

        int part1 = lines.stream().mapToInt( l -> {
            String second = l.substring(l.length() / 2);
            String first = l.replace(second, "");
            for(char c : first.toCharArray()){
                if(second.contains(Character.toString(c))){
                    return getPriority(c);
                }
            }
            return 0;
        }).sum();

        System.out.println(part1);

        int part2 = 0;
        for(int i = 0; i < lines.size(); i++){
            String first = lines.get(i);
            String second = lines.get(++i);
            String third = lines.get(++i);

            for(char c : first.toCharArray()){
                if(second.contains(Character.toString(c)) && third.contains(Character.toString(c))){
                    part2 += getPriority(c);
                    break;
                }
            }
        }

        System.out.println(part2);
    }

    private static int getPriority(char c){
        return  c - ((c < 91) ? 38 : 96);
    }
}
