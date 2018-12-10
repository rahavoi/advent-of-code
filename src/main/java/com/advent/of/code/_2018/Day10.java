package com.advent.of.code._2018;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day10 {
    public static void main(String[] args) throws Exception{
        new Day10().task1();
    }

    private void task1() throws Exception {
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/2018/Day10.txt"));

        input.forEach(line -> {
            String[] positionAndVelocity = line.replace("position=", "")
                .split(" velocity=");

            String[] positionString = positionAndVelocity[0].replace("<", "").replace(">", "").split(",");
            String[] velocityString = positionAndVelocity[1].replace("<", "").replace(">", "").split(",");

            Integer x = Integer.parseInt(positionString[0].trim());
            Integer y = Integer.parseInt(positionString[1].trim());

            Integer velocityX = Integer.parseInt(velocityString[0].trim());
            Integer velocityY = Integer.parseInt(velocityString[1].trim());

            System.out.println("X: " + x + "Y:" + " Moving: X" + velocityX + "Y" + velocityY);

        });
    }
}
