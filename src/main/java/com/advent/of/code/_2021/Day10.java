package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day10.txt"));


        List<Long> result = input.stream().filter(line -> {
           char[] chars = line.toCharArray();

            Stack<Character> stack = new Stack<>();

            for(char c : chars){
                if(c == '(' || c == '[' || c == '{' || c == '<'){
                    stack.push(c);
                } else {
                    Character open = stack.pop();

                    switch (c){
                        case ')':
                            if (open != '(') return false;
                            break;
                        case ']':
                            if (open != '[') return false;
                            break;
                        case '}':
                            if (open != '{') return false;
                            break;
                        case '>':
                            if (open != '<') return false;
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }

                }
            }

            return true;

        }).map(l -> {
            char[] chars = l.toCharArray();

            Stack<Character> stack = new Stack<>();

            for(char c : chars) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    stack.push(c);
                } else {
                    stack.pop();
                }
            }

            long totalScore = 0;

            while(!stack.isEmpty()){
                char c = stack.pop();

                totalScore *= 5;
                totalScore += getCloseErrorCostPart2(c);
            }

            return totalScore;


        }).sorted().collect(Collectors.toList());



        int middle = result.size() / 2;

        result.forEach(System.out::println);

        System.out.println(result.get(middle));


    }

    public static int getCloseErrorCostPart2(Character c){
        switch (c){
            case '(':
                return 1;
            case '[':
                return 2;
            case '{':
                return 3;
            case '<':
                return 4;
            default:
                throw new IllegalArgumentException();
        }
    }



    public static int getCloseErrorCost(Character c){
        switch (c){
            case ')':
                return 3;
            case ']':
                return 57;
            case '}':
                return 1197;
            case '>':
                return 25137;
            default:
                throw new IllegalArgumentException();
        }
    }
}
