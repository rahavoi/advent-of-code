package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day22 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(
            "/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day22.txt"));

        List<Command> commands = lines.stream().map(l -> {
            String[] parts = l.split(" ");
            String operation = parts[0];
            String[] ranges = parts[1].split(",");
            String xRange[] = ranges[0].split("=")[1].split("\\.\\.");
            String yRange[] = ranges[1].split("=")[1].split("\\.\\.");
            String zRange[] = ranges[2].split("=")[1].split("\\.\\.");

            int xMin = Integer.parseInt(xRange[0]);
            int xMax = Integer.parseInt(xRange[1]);

            int yMin = Integer.parseInt(yRange[0]);
            int yMax = Integer.parseInt(yRange[1]);

            int zMin = Integer.parseInt(zRange[0]);
            int zMax = Integer.parseInt(zRange[1]);

            return new Command(operation, xMin, xMax, yMin, yMax, zMin, zMax);

        }).collect(Collectors.toList());

        //total volume is:  731 082 516. Too big for brute forcing :(
        naiveButGoodEnoughForPart1(commands);
    }

    private static void naiveButGoodEnoughForPart1(List<Command> commands) {
        Map<Point, String> pointStates = new HashMap<>();

        for(int i = 0; i < commands.size(); i++){
            Command c = commands.get(i);

            //1 443 132 032
            //731 082 516
            for(int x = Math.max(c.xMin, -50); x < Math.min(c.xMax, 50) + 1; x++){
                for(int y = Math.max(c.yMin, -50); y < Math.min(c.yMax, 50) + 1; y++){
                    for(int z = Math.max(c.zMin, -50); z < Math.min(c.zMax, 50) + 1; z++){
                        Point p = new Point(x, y, z);
                        pointStates.put(p, c.operation);
                    }
                }
            }
        }

        Long cubesTurnedOn = pointStates.entrySet().stream().filter(e -> e.getValue().equals("on")).count();
        System.out.println(cubesTurnedOn);
    }

    static class Command {
        String operation;
        int xMin;
        int xMax;
        int yMin;
        int yMax;
        int zMin;
        int zMax;

        public Command(String operation, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
            this.operation = operation;
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
            this.zMin = zMin;
            this.zMax = zMax;
        }
    }

    static class Point{
        int x;
        int y;
        int z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x && y == point.y && z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
