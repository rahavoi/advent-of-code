package com.advent.of.code._2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day2 {
    public static void main(String[] args) throws IOException {
        Submarine submarine = new Submarine();
        Files.readAllLines(Paths.get(Constants.PATH_2021 + "Day2.txt"))
            .stream().map(l -> l.split(" "))
            .forEach(parts -> submarine.move(parts[0], Long.parseLong(parts[1])));

        System.out.println(submarine.position * submarine.depth);
    }
}