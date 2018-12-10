package com.advent.of.code._2015;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Day1 {
    public static void main(String[] args) throws Exception{
        String input = Files.readAllLines(Paths.get("src/main/resources/2015/Day1.txt")).get(0);

        int floor = 0;


        char[] chars = input.toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(chars[i] == '('){
                floor++;
            } else {
                floor--;
            }

            if(floor == -1){
                System.out.println(i + 1);
            }
        }

        System.out.println(floor);
    }
}
