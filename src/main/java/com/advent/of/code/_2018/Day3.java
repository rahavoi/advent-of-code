package com.advent.of.code._2018;

import javafx.util.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) throws Exception{


        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Day3.txt"));
        Map<String, Pair<Integer, Integer>[]> elfPatches = new HashMap<>();
        Map<Pair<Integer, Integer>, Integer> pixelOccurrences = new HashMap<>();

        lines.forEach(line -> {
            String [] idAndValue =  line.split("@");
            String id = idAndValue[0].trim();

            String[] coordinatesAndSize = idAndValue[1].split(":");

            String[] coordinates = coordinatesAndSize[0].trim().split(",");
            String[] size = coordinatesAndSize[1].trim().split("x");

            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            int width = Integer.parseInt(size[0]);
            int height = Integer.parseInt(size[1]);

            Pair<Integer, Integer>[] pixels = new Pair[width * height];
            int pixelIndex = 0;

            for(int i = 0; i < width; i++){
                int currX = i + x;

                for(int j = 0; j < height; j++){
                    int currY = j + y;
                    Pair<Integer, Integer> currentPixel = new Pair<>(currX, currY);

                    Integer occurences = pixelOccurrences.get(currentPixel);

                    if(occurences == null){
                        occurences = 0;
                    }

                    pixelOccurrences.put(currentPixel, occurences + 1);

                    pixels[pixelIndex++] = new Pair<>(currX, currY);
                }
            }

            elfPatches.put(id, pixels);
        });

        System.out.println("Total ovelap: " + pixelOccurrences.values().stream().filter(value -> value > 1).count());

        List<Pair<Integer, Integer>> nonOvelapping = pixelOccurrences.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(entry -> entry.getKey()).collect(Collectors.toList());

        elfPatches.entrySet().forEach(entry -> {
            if(nonOvelapping.containsAll(Arrays.asList(entry.getValue()))){
                System.out.println("Ideal non-overlappin Patch: " + entry.getKey());
            }
        });

    }
}