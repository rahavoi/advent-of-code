package com.advent.of.code._2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;

public class Day1 {
    public static void main(String[] args){
        try {
            LinkedHashSet<Integer> frequencies = new LinkedHashSet<>();
            frequencies.add(0);

            while(true){
                Files.readAllLines(Paths.get("src/main/resources/2018/Day1.txt"))
                        .stream()
                        .map(Integer::parseInt)
                        .forEach(num -> {

                            int previous = (frequencies.size() > 0 ) ?
                                    (Integer) frequencies.toArray()[frequencies.size() -1] : 0;
                            int current = previous + num;

                            if(!frequencies.add(current)){
                                System.out.println("Same: " + current);
                                System.exit(1);
                            }


                        });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}