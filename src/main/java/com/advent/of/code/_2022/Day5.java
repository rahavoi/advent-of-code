package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Day5 {
    public static void main(String[] args) throws Exception {
        //Too lazy to parse stacks input. Did it by hand :)
        Stack<Character> one = new Stack<>();
        one.push('R');
        one.push('G');
        one.push('J');
        one.push('B');
        one.push('T');
        one.push('V');
        one.push('Z');


        Stack<Character> two = new Stack<>();
        two.push('J');
        two.push('R');
        two.push('V');
        two.push('L');

        Stack<Character> three = new Stack<>();
        three.push('S');
        three.push('Q');
        three.push('F');

        Stack<Character> four = new Stack<>();
        four.push('Z');
        four.push('H');
        four.push('N');
        four.push('L');
        four.push('F');
        four.push('V');
        four.push('Q');
        four.push('G');

        Stack<Character> five = new Stack<>();
        five.push('R');
        five.push('Q');
        five.push('T');
        five.push('J');
        five.push('C');
        five.push('S');
        five.push('M');
        five.push('W');


        Stack<Character> six = new Stack<>();
        six.push('S');
        six.push('W');
        six.push('T');
        six.push('C');
        six.push('H');
        six.push('F');

        Stack<Character> seven = new Stack<>();
        seven.push('D');
        seven.push('Z');
        seven.push('C');
        seven.push('V');
        seven.push('F');
        seven.push('N');
        seven.push('J');

        Stack<Character> eight = new Stack<>();
        eight.push('L');
        eight.push('G');
        eight.push('Z');
        eight.push('D');
        eight.push('W');
        eight.push('R');
        eight.push('F');
        eight.push('Q');

        Stack<Character> nine = new Stack<>();
        nine.push('J');
        nine.push('B');
        nine.push('W');
        nine.push('V');
        nine.push('p');


        List<Stack<Character>> stacks = new ArrayList<>();
        stacks.add(one);
        stacks.add(two);
        stacks.add(three);
        stacks.add(four);
        stacks.add(five);
        stacks.add(six);
        stacks.add(seven);
        stacks.add(eight);
        stacks.add(nine);

        List<String> lines = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day5.txt"));

        for(String l : lines){
            String[] parts = l.split(" ");
            int howMany = Integer.parseInt(parts[1]);
            int from = Integer.parseInt(parts[3]);
            int to = Integer.parseInt(parts[5]);

            Stack<Character> fromStack = stacks.get(from - 1);
            Stack<Character> toStack = stacks.get(to - 1);

            List<Character> crates = new ArrayList<>();

            for(int i = 0; i < howMany; i++){
                crates.add(fromStack.pop());
            }

            Collections.reverse(crates);

            for(Character c : crates){
                toStack.push(c);
            }
        }

        stacks.forEach(s -> System.out.print(s.pop()));
    }
}
