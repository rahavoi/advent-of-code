package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day9 {
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day9.txt"));
        Set<Position> tailPositions = new HashSet<>();
        tailPositions.add(new Position(0, 0));

        List<Position> positions = new ArrayList<>();
        for(int i = 0 ; i < 10; i++){
            positions.add(new Position(0, 0));
        }

        for(String line : input){
            System.out.println(line);
            String[] parts = line.split(" ");
            String direction = parts[0];

            int times = Integer.parseInt(parts[1]);

            for(int i = 0; i < times; i++){
                updatePositions(direction, positions);
                Position tail = positions.get(positions.size() - 1);
                tailPositions.add(new Position(tail.x, tail.y));
            }
        }

        System.out.println(tailPositions.size());

    }

    private static void updatePositions(String direction, List<Position> positions){
        List<Position> prevPos = new ArrayList<>();

        positions.forEach(p -> prevPos.add(new Position(p.x, p.y)));

        switch (direction){
            case "R":
                positions.get(0).x++;
                break;
            case "L":
                positions.get(0).x--;
                break;
            case "D":
                positions.get(0).y++;
                break;
            case "U":
                positions.get(0).y--;
                break;
        }

        for(int i = 1; i < positions.size(); i++){
            Position head = positions.get(i - 1);
            Position tail = positions.get(i);

            if(Math.abs(head.x - tail.x) > 1 || Math.abs(head.y - tail.y) > 1){
                if(head.y == tail.y){
                    //simple case:
                    tail.x = head.x > tail.x ? tail.x + 1 : tail.x - 1;
                } else if(head.x == tail.x){
                    //simple case:
                    tail.y = head.y > tail.y ? tail.y + 1 : tail.y - 1;
                }  else {
                    //if the head and tail aren't touching and aren't in the same row or column, tail must move diagonally to keep up:
                    tail.x = head.x > tail.x ? tail.x + 1 : tail.x - 1;
                    tail.y = head.y > tail.y ? tail.y + 1 : tail.y - 1;
                }
            }
        }
    }

    private static void print(List<Position> positions){
        int minx = positions.stream().mapToInt(p -> p.x).min().getAsInt();
        int maxx = positions.stream().mapToInt(p -> p.x).max().getAsInt();
        int miny = positions.stream().mapToInt(p -> p.y).min().getAsInt();
        int maxy = positions.stream().mapToInt(p -> p.y).max().getAsInt();

        int[][] grid = new int[maxy - miny + 1][maxx - minx + 1];

        for(int i = 0; i < positions.size(); i++){

            grid[positions.get(i).y - miny][positions.get(i).x - minx] = i == 0 ? -1 : i;
        }

        for(int y = 0; y < grid.length; y++){
            for(int x = 0; x < grid[0].length; x++){
                if(grid[y][x] == -1){
                    System.out.print('H');
                } else {
                    System.out.print(grid[y][x]);
                }
            }
            System.out.println();
        }

        System.out.println("END");


    }

    static class Position{
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
