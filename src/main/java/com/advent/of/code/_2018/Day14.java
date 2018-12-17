package com.advent.of.code._2018;

import java.util.ArrayList;
import java.util.List;

public class Day14 {

    public static void main(String[] args) {
        int elf1pos = 0;
        int elf2pos = 1;
        int iterations = 0;

        List<Integer> data = new ArrayList<>();
        data.add(3);
        data.add(7);

        while(iterations++ < 30000000) {
            int elf1Recipe = data.get(elf1pos);
            int elf2Recipe = data.get(elf2pos);

            String newRecipe = "" + (elf1Recipe + elf2Recipe);

            for(char c: newRecipe.toCharArray()){
                int num = Integer.parseInt("" + c);
                data.add(num);
            }

            elf1pos += elf1Recipe + 1;
            elf2pos += elf2Recipe + 1;
            elf1pos =  elf1pos < data.size() ? elf1pos : elf1pos % data.size();
            elf2pos =  elf2pos < data.size() ? elf2pos : elf2pos % data.size();
        }

        for(int i = 0; i < data.size(); i++ ){
            if(data.get(i).equals(0) &&
                    data.get(i + 1).equals(7) &&
                    data.get(i + 2).equals(4) &&
                    data.get(i + 3).equals(5) &&
                    data.get(i + 4).equals(0) &&
                    data.get(i + 5).equals(1)){
                System.out.println(i);
                break;
            }
        }
    }
}
