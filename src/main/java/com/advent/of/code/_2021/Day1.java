package com.advent.of.code._2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.stream.IntStream;

public class Day1 {
    public static void main(String[] args) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(Constants.PATH + "Day1.txt"));

        long p1 = IntStream.range(0, lines.size() -1 )
            .filter(i -> Integer.parseInt(lines.get(i)) < Integer.parseInt(lines.get(i + 1)))
            .count();

        long p2 = IntStream.range(2, lines.size() -1 )
            .filter(i -> Integer.parseInt(lines.get(i - 2)) < Integer.parseInt(lines.get(i + 1)))
            .count();

        System.out.println(p1);
        System.out.println(p2);
    }
}
