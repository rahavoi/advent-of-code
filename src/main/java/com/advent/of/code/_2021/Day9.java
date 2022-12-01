package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day9 {
    public static void main(String[] args) throws Exception{
        List<String> input = Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day9.txt"));

        int[][] data = new int[input.size()][input.get(0).length()];

        for(int i = 0; i < input.size(); i++){
            String line = input.get(i);

            for(int j = 0; j < line.length(); j++){
                data[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }

        int sum = 0;
        List<Pair> lowPoints = new ArrayList<>();

        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length; j++){
                int cur = data[i][j];

                int south = i > 0 ? data[i - 1][j] : Integer.MAX_VALUE;
                int north = i < data.length - 1 ? data[i + 1][j] : Integer.MAX_VALUE;

                int east = j > 0 ? data[i][j - 1] : Integer.MAX_VALUE;
                int west = j < data[0].length - 1 ? data[i][j + 1] : Integer.MAX_VALUE;


                if(cur < south && cur < north && cur < east && cur < west){
                    sum+= 1 + cur;
                    Pair pair = new Pair(i, j);
                    lowPoints.add(pair);
                }
            }
        }

        System.out.println("Part 1: " + sum);

        //for each low point find a basin - area surrounded by nines;
        long result = lowPoints.stream().map(point -> {
            Set<Pair> visited = new HashSet<>();
            Queue<Pair> q = new LinkedList<>();
            q.add(point);

            while (!q.isEmpty()){
                Pair current = q.poll();
                visited.add(current);

                int i = current.i;
                int j = current.j;

                int south = i > 0 ? data[i - 1][j] : Integer.MAX_VALUE;
                if(!visited.contains(new Pair(i - 1, j)) && south < 9){
                    q.add(new Pair(i - 1, j));
                }

                int north = i < data.length - 1 ? data[i + 1][j] : Integer.MAX_VALUE;
                if(!visited.contains(new Pair(i + 1, j)) && north < 9){
                    q.add(new Pair(i + 1, j));
                }

                int east = j > 0 ? data[i][j - 1] : Integer.MAX_VALUE;
                if(!visited.contains(new Pair(i, j - 1)) && east < 9){
                    q.add(new Pair(i, j - 1));
                }

                int west = j < data[0].length - 1 ? data[i][j + 1] : Integer.MAX_VALUE;
                if(!visited.contains(new Pair(i, j + 1)) && west < 9){
                    q.add(new Pair(i, j + 1));
                }
            }

            return visited.size();
        }).sorted(Comparator.reverseOrder()).limit(3).reduce(1, (a , b) -> a * b );

        System.out.println("Part 2:" +  result);
    }

    static class Pair{
        int i;
        int j;

        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair p = (Pair) o;

            return this.i == p.i && this.j == p.j;
        }

        @Override
        public int hashCode() {
            return i * j;
        }
    }
}
