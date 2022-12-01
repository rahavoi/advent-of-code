package com.advent.of.code._2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Day
{
    static Map<Long, Long> memory = new HashMap<>();
    public static void main(String[] args) throws Exception {
        List<String> lines = lines("src/main/resources/2020/Day14.txt");

        List<Instruction> instructions = new ArrayList<>();
        Instruction instruction = null;
        for(int i = 0 ; i < lines.size(); i++){
            String[] parts = lines.get(i).split(" = ");
            if(parts[0].startsWith("mask")){
                instruction = new Instruction();
                instruction.mask = parts[1];
                instructions.add(instruction);
            } else {
                Long memory = Long.parseLong(parts[0].replace("mem[", "").replace("]",""));
                Long value = Long.parseLong(parts[1]);
                instruction.commands.add(new Command(memory, value));
            }
        }

        instructions.forEach(instr -> {
            String mask = instr.mask;
            instr.commands.forEach(command -> {
                //System.out.println(mask);
                String temp = Long.toBinaryString(command.memory);
                while (temp.length() < 36){
                    temp = "0" + temp;
                }
                String binaryMemory = temp;

                char[] result = new char[binaryMemory.length()];

                for(int i = 0 ; i < binaryMemory.length(); i++){
                    result[i] = mask.charAt(i) == 'X' ?
                        mask.charAt(i) : mask.charAt(i) == '1' ? mask.charAt(i) : binaryMemory.charAt(i);
                }

                String maskedResult = new String(result);
                //System.out.println(maskedResult);


                int countMasked = 0;
                for (char c : result){
                    if(c == 'X'){
                        countMasked++;
                    }
                }

                //Possible permutations of floating bits:
                List<String> permutations = new ArrayList<>();
                generatePermutations("", countMasked, permutations);

                //For each permutation: replace X with a 0 or 1
                for(String permutation : permutations){

                    String mem = maskedResult;

                    for(char c : permutation.toCharArray()){
                        char[] arr = {c};
                        mem = mem.replaceFirst("(?:X)", new String(arr));
                    }
                    //System.out.println(mem);

                    memory.put(Long.parseLong(mem, 2), command.value);
                    //System.out.println("Put : " + command.value + " TO : " + Long.parseLong(mem, 2));
                }

            });
        });

        //System.out.println();
        System.out.println(memory.values().stream().mapToLong(i -> i).sum());
    }

    public static void generatePermutations(String soFar, int iterations, List<String> permutations) {
        if(iterations == 0) {
            permutations.add(soFar);
        }
        else {
            generatePermutations(soFar + "0", iterations - 1, permutations);
            generatePermutations(soFar + "1", iterations - 1, permutations);
        }
    }

    static class Instruction{
        String mask;
        List<Command> commands = new ArrayList<>();
    }

    static class Command{
        Long memory;
        Long value;

        public Command(Long memory, Long value)
        {
            this.memory = memory;
            this.value = value;
        }
    }
}
