package com.advent.of.code._2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day18 {
    private static int rounds = 0;
    private static char[][] map;
    private static char OPEN = '.';
    private static char TREE = '|';
    private static char LUMBER = '#';

    public static void main(String[] args) throws Exception{
        initMap();
        //int cycle = 28;
        int iterations = 1000000000;
        int meaningLessIterations = 999999280; //cycle repeats every 28 iterations.

        while(++rounds <= iterations){
            if(rounds > 500 && rounds < meaningLessIterations){
                rounds += meaningLessIterations;
            }

            printMap();
            processMap();
        }

        int trees = count(TREE);
        int lumber = count(LUMBER);

        System.out.println("After: " + rounds);
        System.out.println("Result: " + trees + " * " + lumber + " = " + (trees * lumber));
    }

    private static void processMap(){
        char[][] newMap = new char[map.length][map[0].length];

        for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[0].length; y++){
                newMap[x][y] = getNextResult(x, y);
            }
        }

        map = newMap;
    }

    private static char getNextResult(int x, int y){
        Character result;
        char current = getSquare(x, y);

        List<Character> neighbors = getAdjacentSquares(x, y);

        if(current == OPEN){
            long treeNeighbors = neighbors.stream().filter(n -> n == TREE).count();

            if(treeNeighbors >= 3){
                result = TREE;
            } else {
                result = OPEN;
            }
        } else if(current == TREE){
            long lumberNeighbors = neighbors.stream().filter(n -> n == LUMBER).count();

            if(lumberNeighbors >= 3){
                result = LUMBER;
            } else {
                result = TREE;
            }
        } else if(current == LUMBER){
            long lumberNeighbors = neighbors.stream().filter(n -> n == LUMBER).count();
            long treeNeighbors = neighbors.stream().filter(n -> n == TREE).count();

            if(lumberNeighbors >= 1 && treeNeighbors >= 1){
                result = LUMBER;
            } else {
                result = OPEN;
            }
        } else {
            throw new RuntimeException("Invalid character!");
        }

        return result;
    }

    private static List<Character> getAdjacentSquares(int x, int y){
        List<Character> result = new ArrayList<>();

        Character north = getSquare(x, y - 1);
        Character northEast = getSquare(x + 1, y - 1);
        Character east = getSquare(x + 1, y);
        Character southEast = getSquare(x + 1, y + 1);
        Character south = getSquare(x, y + 1);
        Character southWest = getSquare(x - 1, y + 1);
        Character west = getSquare(x - 1, y);
        Character northWest = getSquare(x - 1, y - 1);

        result.add(north);
        result.add(northEast);
        result.add(east);
        result.add(southEast);
        result.add(south);
        result.add(southWest);
        result.add(west);
        result.add(northWest);

        return result.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static Character getSquare(int x, int y){
        Character result = null;

        try{
            result = map[x][y];
        } catch (ArrayIndexOutOfBoundsException e){
            //Missing. That's fine
        }

        return result;
    }

    private static void initMap() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2018/Day18.txt"));
        map = new char[lines.get(0).length()][lines.size()];
        for(int y = 0; y < lines.size(); y++){
            for(int x = 0; x < lines.get(0).length(); x++){
                map[x][y] = lines.get(y).toCharArray()[x];
                System.out.print(map[x][y]);
            }
            System.out.println();
        }
    }

    private static void printMap(){
        System.out.println();
        for(int y = 0; y < map[0].length; y++){
            for(int x = 0; x < map.length; x++){
                System.out.print(map[x][y]);
            }
            System.out.println();
        }
    }

    private static int count(char ch){
        int result = 0;
        for(int y = 0; y < map[0].length; y++){
            for(int x = 0; x < map.length; x++){
                if(map[x][y] == ch){
                    result++;
                }
            }
        }

        return result;
    }
}
