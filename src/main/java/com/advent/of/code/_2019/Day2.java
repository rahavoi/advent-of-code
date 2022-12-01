package com.advent.of.code._2019;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Day2
{
    public static void main(String[] args) throws Exception{
        String[] input = Files.readAllLines(Paths.get("src/main/resources/2019/day2.txt")).get(0).split(",");


        int[] program = new int[input.length];

        for(int i = 0; i < input.length; i++){
            program[i] = Integer.parseInt(input[i]);
        }

        //System.out.println(process(12, 2, program));

        for(int noun = 0; noun < 100; noun++){
            for(int verb = 0; verb < 100; verb++){
                int[] copy = new int[program.length];
                System.arraycopy(program, 0, copy, 0, program.length);
                int result = new IntComputer(copy).process(noun, verb);

                if(result == 19690720){
                    System.out.println("Found noun and verb: ");
                    System.out.println(100 * noun + verb);
                    System.exit(1);
                }
            }
        }

    }
}
