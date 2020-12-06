package com.advent.of.code._2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6
{
    public static void main(String[] args) throws Exception{
        List<List<Set<Character>>> groups = getAnswerGroups();
        int part1 = groups.stream().mapToInt(g -> g.stream().flatMap(s -> s.stream())
            .collect(Collectors.toSet()).size()).sum();

        int part2 = groups.stream().mapToInt(g -> {
                Set<Character> common = g.get(0);

                for(int i = 1; i < g.size(); i++){
                    common = common.stream().filter(g.get(i)::contains)
                        .collect(Collectors.toSet());
                }

                return common.size();
            }
        ).sum();

        System.out.println(part1);
        System.out.println(part2);
    }

    private static List<List<Set<Character>>> getAnswerGroups() throws IOException
    {
        List<List<Set<Character>>> allGroups = new ArrayList<>();
        List<Set<Character>> group = new ArrayList<>();

        for(String line : Files.readAllLines(Paths.get("src/main/resources/2020/Day6.txt"))){
            if(line.trim().isEmpty()){
                allGroups.add(group);
                group = new ArrayList<>();
            } else {
                Set<Character> answer = new HashSet<>();
                for(char c : line.toCharArray()){
                    answer.add(c);
                }

                group.add(answer);
            }
        }

        allGroups.add(group);
        return allGroups;
    }
}
