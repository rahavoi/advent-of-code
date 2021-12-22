package com.advent.of.code._2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day19 {

    static List<Scanner> scanners = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        parseInput();

        Scanner scanner0 = scanners.get(0);
        Scanner scanner1 = scanners.get(1);



        for(Point p0: scanner0.points){
            for(Point p1 : scanner1.points){

            }
        }

    }

    private static void parseInput() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(
            "/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day19.txt"));

        Scanner s = null;

        for(String l : lines){
            if(l.isEmpty()){
                continue;
            }

            if(l.startsWith("---")){
                s = new Scanner();
                s.id = l;
                scanners.add(s);
            } else{
                s.points.add(Point.fromInput(l));
            }

        }

        System.out.println("Parsed " + scanners.size());
    }

    static class Scanner{
        String id;
        Set<Point> points = new HashSet<>();
    }



    static class Point {
        private int x;
        private int y;
        private int z;

        public static Point fromInput(String input) {
            var inputs = input.split(",");

            return new Point(Integer.parseInt(inputs[0]),Integer.parseInt(inputs[1]),Integer.parseInt(inputs[2]));
        }

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point roll() {
            var oldY = y;

            y = z;
            z = -oldY;
            return this;
        }

        public Point turn() {
            var oldX = x;
            x = -y;
            y = oldX;
            return this;
        }

        public Point reverseTurn() {
            var oldX = x;
            x = y;
            y = -oldX;
            return this;
        }
    }
}
