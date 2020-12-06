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
        part1();
        part2();
    }

    private static void part1() throws IOException
    {
        List<List<Set<Character>>> allGroups = getAnswerGroups();

        int sum = 0;
        for(List<Set<Character>> group : allGroups){

            Set<Character> superSet = group.get(0);

            for(int i = 1; i < group.size(); i++){
                superSet.addAll(group.get(i));
            }

            sum += superSet.size();
        }

        System.out.println(sum);
    }

    private static void part2() throws IOException{
        List<List<Set<Character>>> allGroups = getAnswerGroups();

        int sum = 0;
        for(List<Set<Character>> gr : allGroups){
            Set<Character> common = gr.get(0);

            for(int i = 1; i < gr.size(); i++){
                common = common.stream()
                    .filter(gr.get(i)::contains)
                    .collect(Collectors.toSet());
            }

            sum += common.size();
        }

        System.out.println(sum);
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
