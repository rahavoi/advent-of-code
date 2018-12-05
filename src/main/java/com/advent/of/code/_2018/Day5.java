package com.advent.of.code._2018;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
    public static void main(String[] args) throws Exception {
        new Day5().task1();
        new Day5().task2();
    }

    public void task1() throws Exception {
        List<Character> chars = Files.readAllLines(Paths.get("src/main/resources/2018/Day5.txt")).get(0)
                .chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        outer: while (true){
            for(int i = 0; i < chars.size() - 1; i++){
                if(react(chars.get(i), chars.get(i + 1))){
                    chars.set(i, null);
                    chars.set(i + 1, null);

                    while (chars.remove(null));
                    continue outer;
                }
            }
            break;
        }

        System.out.println("Size of polymer after reacting: " + chars.size());
    }

    public void task2() throws Exception {
        List<Integer> result = new ArrayList<>();
        for(char current = 'a'; current <='z'; current++ )
        {
            List<Character> chars = Files.readAllLines(Paths.get("src/main/resources/2018/Day5.txt")).get(0)
                    .chars().mapToObj(c -> (char) c).collect(Collectors.toList());

            while (chars.remove((Character) current)  || chars.remove((Character) Character.toUpperCase(current)));

            outer: while (true){
                for(int i = 0; i < chars.size() - 1; i++){
                    if(react(chars.get(i), chars.get(i + 1))){
                        List<Integer> indicesToRemove = new ArrayList<>();
                        indicesToRemove.add(i);
                        indicesToRemove.add(i + 1);

                        indicesToRemove.forEach(idx -> chars.set(idx, null));
                        while (chars.remove(null));

                        continue outer;
                    }
                }
                break ;
            }

            result.add(chars.size());
        }

        System.out.println("Best result: " + result.stream().mapToInt(Integer::intValue).min().getAsInt());
    }

    private boolean react(char a, char b){
        return a != b && ( Character.toLowerCase(a) == b || Character.toUpperCase(a) == b);
    }
}
