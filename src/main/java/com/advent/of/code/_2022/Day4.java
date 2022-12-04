package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day4 {
    public static void main(String[] args)throws Exception{
        List<String> lines = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day4.txt"));

        int count = lines.stream().mapToInt(l -> {
            String[] parts = l.split(",");
            List<Integer> elf1 = parseAssignment(parts[0]);
            List<Integer> elf2 = parseAssignment(parts[1]);
            //part 1:
            //return elf1.containsAll(elf2) || elf2.containsAll(elf1) ? 1 :0;
            //part 2:
            return Collections.disjoint(parseAssignment(parts[0]), parseAssignment(parts[1])) ? 0 : 1;
        }).sum();
        System.out.println(count);
    }

    private static List<Integer> parseAssignment(String assignment){
        List<Integer> result = new ArrayList<>();
        String[] parts = assignment.split("-");
        int from = Integer.parseInt(parts[0]);
        int to = Integer.parseInt(parts[1]);

        for(int i = from; i <= to; i++){
            result.add(i);
        }

        return result;
    }
}
