package com.advent.of.code._2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Day2 {
    public static void main(String[] args){
        new Day2().task1();
        new Day2().task2();
    }

    public  void task2(){
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2018/Day2.txt"));

            for(int i = 0; i < lines.size(); i++){
                for(int j = 0; j < lines.size(); j++){
                    if(i == j){
                        continue;
                    }

                    isDifferentByExactlyOneCharAtSamePosition(lines.get(i), lines.get(j));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isDifferentByExactlyOneCharAtSamePosition(String s1, String s2){
        int at = 0;
        int diff = 0;
        for(int i = 0 ; i < s1.length(); i++){
            if(s1.charAt(i) != s2.charAt(i)){
                diff++;
                at = i;
            }

            if(diff > 1){
                return false;
            }
        }

        System.out.println(s1 + " -> " + s2 + " (at: " + at + ")");
        System.exit(1);

        return true;

    }

    public  void task1(){
        try {
            TwosAndThrees twosAndThrees = new TwosAndThrees();


            Files.readAllLines(Paths.get("src/main/resources/2018/Day2.txt"))
                    .forEach(line -> {
                        Map<Character, Integer> charValues = new HashMap<>();
                        char[] chars = line.toCharArray();

                        for(char c : chars){
                            Integer value = charValues.get(c);
                            if(value == null){
                                value = 0;
                            }

                            charValues.put(c, value + 1);
                        }


                        Optional<Integer> twos = charValues.values().stream().filter(i -> i == 2).findAny();
                        Optional<Integer> threes = charValues.values().stream().filter(i -> i == 3).findAny();

                        if(twos.isPresent()){
                            twosAndThrees.setTwos(twosAndThrees.getTwos() + 1);
                        }

                        if(threes.isPresent()){
                            twosAndThrees.setThrees(twosAndThrees.getThrees() + 1);
                        }

                    });

            System.out.println(twosAndThrees.getTwos() * twosAndThrees.getThrees());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class TwosAndThrees{
        private int twos;
        private int threes;

        public int getTwos() {
            return twos;
        }

        public void setTwos(int twos) {
            this.twos = twos;
        }

        public int getThrees() {
            return threes;
        }

        public void setThrees(int threes) {
            this.threes = threes;
        }
    }
}