package com.advent.of.code._2018;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day17 {
    private List<Point> points;
    public static void main(String[] args) throws Exception {
        Day17 day17 = new Day17();
        day17.initPoints();

        int maxX = (int) day17.points.stream().mapToDouble(Point::getX).max().getAsDouble();
        int maxY = (int) day17.points.stream().mapToDouble(Point::getY).max().getAsDouble();
        int minX = (int) day17.points.stream().mapToDouble(Point::getX).min().getAsDouble();
        int minY = (int) day17.points.stream().mapToDouble(Point::getY).min().getAsDouble();

        System.out.println("X: From " + minX + " to " + maxX);
        System.out.println("Y: From " + minY + " to " + maxY);

    }

    private void initPoints() throws IOException {
        points = new ArrayList<>();
        Files.readAllLines(Paths.get("src/main/resources/2018/Day17.txt")).forEach(line -> {
            String xLabel = "x=";
            String yLabel = "y=";
            String range = "\\..";
            String[] elements = line.split(", ");

            String xValue = elements[0].contains(xLabel) ? elements[0] : elements[1];
            String yValue = elements[0].contains(yLabel) ? elements[0] : elements[1];

            xValue = xValue.replace(xLabel, "");
            yValue = yValue.replace(yLabel, "");


            if(xValue.contains(range)){
                String[] xRange = xValue.split(range);
                int xMin = Integer.parseInt(xRange[0]);
                int xMax = Integer.parseInt(xRange[1]);

                int y = Integer.parseInt(yValue);

                for(int x = xMin; x <= xMax; x++){
                    points.add(new Point(x, y));
                }
            } else {
                String[] yRange = yValue.split(range);
                int yMin = Integer.parseInt(yRange[0]);
                int yMax = Integer.parseInt(yRange[1]);

                int x = Integer.parseInt(xValue);

                for(int y = yMin; x <= yMax; y++){
                    points.add(new Point(x, y));
                }
            }




        });
    }
}
