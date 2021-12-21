package com.advent.of.code._2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day20 {
    static String algorithm = "#..##...#...####.......#...###.#...#.##.#.#.###.##...#..#.....#..#.##..#.....#...###.......###.###...##..##.#.##.#.#.......##.#.#..#.#...##....#..#.###...#......#..##...##.#.##.##.###.##.#...#..###...###..###.##.#..#..#.#.#..########..#.#.#.####....##.##.#.##.##.#...#..###...###.###..#...##.##..###.##.##.#.###.#...#####.##.####.####.##...####.####...#.#.##..#.######.#..#...##.#.##..###.##.#..##.##....##.###.###..#..##.#.#...##.#.#####...##....#.##....####.#.####.#####.#....#...###.....#####.#...###..#.####.";



    static Map<Point, Character> points = new HashMap<>();

    public static void main(String[] args) throws Exception {
        part1();
    }



    private static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(
            "/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day20.txt"));

        char[][] data = new char[lines.size()][lines.get(0).length()];

        for(int i = 0; i < lines.size(); i++){
            for(int j = 0; j < lines.get(0).length(); j++){
                data[i][j] = lines.get(i).charAt(j);
                points.put(new Point(i,j), lines.get(i).charAt(j));
            }
        }

        int minX = 0;
        int maxX = lines.get(0).length() - 1;
        int minY = 0;
        int maxY = lines.size() -1;

        for(int i = 0; i < 50; i++){
            minX -= 2;
            maxX += 2;
            minY -= 2;
            maxY += 2;

            char defaultChar = i % 2 == 0 ? '.' : '#';

            optimize(minX, maxX, minY, maxY, defaultChar);
        }

        //print(minX, maxX, minY, maxY);

        int finalMinX = minX + 2;
        int finalMaxX = maxX  - 2;
        int finalMinY = minY + 2;
        int finalMaxY = maxY -2;

        Long result = points.entrySet().stream()
            .filter(e -> {
              Point p = e.getKey();
              return p.x >= finalMinX && p.x <= finalMaxX && p.y >= finalMinY && p.y <= finalMaxY;
            })
            .filter(e -> e.getValue().equals('#')).count();

        System.out.println(result);
    }

    private static void print(int minX, int maxX, int minY, int maxY) {
        //Print
        System.out.println("State: ");
        for(int i = minY; i < maxY; i++){
            for(int j = minX; j < maxX; j++){
                System.out.print(points.getOrDefault(new Point(i,j), '.'));
            }
            System.out.println();
        }
    }

    private static void optimize(int minX, int maxX, int minY, int maxY, char def) {
        Map<Point, Character> newPoints = new HashMap<>();

        for(int i = minY; i < maxY; i++){
            for(int j = minX; j < maxX; j++){
                Character c = points.get(new Point(i,j));
                Point p = new Point(i, j);

                if(c == null){
                    points.put(p, def);
                }

                newPoints.put(p, calculateNewValue(p, def));

            }
        }

        points = newPoints;
    }

    static Character calculateNewValue(Point p, char defaultChar){
        StringBuilder sb = new StringBuilder();
        //FirstRow
        sb.append(points.getOrDefault(new Point(p.y - 1, p.x - 1), defaultChar));
        sb.append(points.getOrDefault(new Point(p.y - 1, p.x), defaultChar));
        sb.append(points.getOrDefault(new Point(p.y - 1, p.x + 1), defaultChar));

        //Second Row
        sb.append(points.getOrDefault(new Point(p.y, p.x - 1), defaultChar));
        sb.append(points.getOrDefault(p, defaultChar));
        sb.append(points.getOrDefault(new Point(p.y, p.x + 1), defaultChar));

        //Third Row
        sb.append(points.getOrDefault(new Point(p.y + 1, p.x - 1), defaultChar));
        sb.append(points.getOrDefault(new Point(p.y + 1, p.x), defaultChar));
        sb.append(points.getOrDefault(new Point(p.y + 1, p.x + 1), defaultChar));


        String result = sb.toString();
        result = result.replaceAll("\\.", "0");
        result = result.replaceAll("#", "1");

        int code = Integer.parseInt(result, 2);

        return algorithm.charAt(code);


    }

    static class Point{
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
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
            return y == point.y && x == point.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y, x);
        }
    }
}
