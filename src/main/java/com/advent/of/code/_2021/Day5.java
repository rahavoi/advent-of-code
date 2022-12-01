package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day5 {
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day5.txt"));

        List<Line> lines = new ArrayList<>();

        input.forEach(l -> {
            String[] parts = l.split(" -> ");
            Point a = new Point(parts[0]);
            Point b = new Point(parts[1]);

            lines.add(new Line(a,b));
        });

        int maxX = lines.stream().mapToInt(line -> Math.max(line.from.x, line.to.x)).max().getAsInt();
        int maxY = lines.stream().mapToInt(line -> Math.max(line.from.y, line.to.y)).max().getAsInt();

        int[][] grid = new int[maxY + 1][maxX + 1];

        fillGrid(grid, lines);

        System.out.println(countOverlaps(grid));

    }

    static int countOverlaps(int[][] grid){
        int result = 0;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] > 1){
                    result++;
                }
            }
        }

        return result;
    }

    static void fillGrid(int[][] grid, List<Line> lines){
        lines.forEach(line -> {
            if(line.from.x != line.to.x && line.from.y != line.to.y){
                //Ignored for part 1.
                //Part 2: Diagonal at 45 degrees
                Point a = line.from.y > line.to.y ? line.to : line.from;
                Point b = a == line.from ? line.to : line.from;

                boolean increaseX = a.x < b.x;
                int x = a.x;
                int y = a.y;

                while(y <= b.y){
                    addPoint(grid, x, y);
                    y++;

                    if(increaseX){
                        x++;
                    } else {
                        x--;
                    }
                }

            } else {
                if(line.from.x == line.to.x){
                    //Vertical line
                    Point a = line.from.y > line.to.y ? line.to : line.from;
                    Point b = a == line.from ? line.to : line.from;

                    int x = a.x;
                    int y = a.y;

                    while(y <= b.y){
                        addPoint(grid, x, y);
                        y++;
                    }

                } else {
                    //Horizontal line
                    Point a = line.from.x > line.to.x ? line.to : line.from;
                    Point b = a == line.from ? line.to : line.from;

                    int x = a.x;
                    int y = a.y;

                    while(x <= b.x){
                        addPoint(grid, x, y);
                        x++;
                    }
                }
            }
        });
    }

    static void addPoint(int[][] grid, int x, int y){
        grid[y][x] = grid[y][x]  + 1;
    }

    static class Line{
        Point from;
        Point to;

        public Line(Point from, Point to){
            this.from = from;
            this.to = to;
        }
    }

    static class Point {
        int x;
        int y;

        public Point(String str){
            String[] parts = str.split(",");
            x = Integer.parseInt(parts[0]);
            y = Integer.parseInt(parts[1]);
        }
    }
}
