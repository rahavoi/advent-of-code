package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.StringJoiner;

public class Day15 {
    static Map<Position, Long> distancesFromStart = new HashMap<>();
    static Map<Position, Position> childToParent = new HashMap<>();
    static Set<Position> positions = new HashSet<>();
    static int destY;
    static int destX;

    static int[][] data;

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day15.txt"));

        data = new int[lines.size()][lines.get(0).length()];

        for(int y = 0; y < lines.size(); y++){
            for(int x = 0; x < lines.get(0).length(); x++){
                data[y][x] = Character.getNumericValue(lines.get(y).charAt(x));
            }
        }

        data = createMap(data);

        for(int y = 0; y < data.length; y++){
            for(int x = 0; x < data[0].length; x++){
                Position p = new Position(x, y);
                positions.add(p);
            }
        }


        destY = data.length - 1;
        destX = data[0].length;

        Position start = new Position(0, 0);
        distancesFromStart.put(start, 0L);

        Queue<DistanceInfo> q = new LinkedList<>();
        q.add(new DistanceInfo(start, 0L));

        while(!q.isEmpty()){
            //TODO: I am using Dijkstra here and it's super slow for part 2. I think A* will do better here?
            findDistanceFromStart(q);
        }

        Position finalDest = new Position(data[0].length - 1, data.length -1);
        long finalDestCost = distancesFromStart.get(finalDest);

        System.out.println("Distance to " + (data.length -1) + "," + (data[0].length - 1) + " is: " + finalDestCost);

        Position cur = finalDest;
        StringJoiner sj = new StringJoiner("->");
        sj.add(cur.x + "," + cur.y);

        while(cur != start){
            cur = childToParent.get(cur);
            sj.add(cur.x + "," + cur.y);
        }

        System.out.println("Back tracing: ");
        //System.out.println(sj);

    }

    //TODO: this is redundant: we do not need to build this huge 2d array.
    // It is possible to figure out which "tile" a point belongs to by just looking at it's coordinates.
    private static int[][] createMap(int[][] firstTile){

        List<StringBuilder> map = new ArrayList<>();
        int rowIdx = 0;

        int rowIncrement = 0;
        for(int i = 0; i < 5; i++){

            for(int j = 0; j < 5; j++){
                int[][] cur = getNextTile(firstTile, j + rowIncrement);
                append(map, cur, rowIdx);
            }
            rowIncrement++;
            rowIdx += firstTile.length;
        }

        int[][] finalMap = new int[firstTile.length * 5][firstTile[0].length * 5];

        for(int i = 0; i < finalMap.length; i++){
            String row = map.get(i).toString();
            for(int j = 0; j < finalMap[0].length; j++){
                finalMap[i][j] = Character.getNumericValue(row.charAt(j));
            }
        }

        //System.out.println("Final Map: ");
        //print(finalMap);

        return finalMap;
    }

    private static void print(int[][] data){
        for(int y = 0; y < data.length; y++){
            for(int x = 0; x < data[0].length; x++){
                System.out.print(data[y][x]);
            }
            System.out.println();
        }

        System.out.println();
    }

    private static void append(List<StringBuilder> map, int[][] tile, int rowIdx){
        for(int i = 0; i < tile.length; i++){
            if(rowIdx == map.size()){
                map.add(new StringBuilder());
            }

            StringBuilder row = map.get(rowIdx);
            for(int j = 0; j < tile[0].length; j++){
                row.append(tile[i][j]);
            }
            rowIdx++;
        }

    }

    private static int[][] getNextTile(int[][] tile, int increment){
        int[][] nextTile = new int[tile.length][tile[0].length];

        for(int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[0].length; j++) {
                int newValue = tile[i][j] + increment;

                if(newValue > 9) {
                    newValue -= 9;
                }

                nextTile[i][j] = newValue;
            }
        }

        return nextTile;
    }

    private static void findDistanceFromStart(Queue<DistanceInfo> q){
        DistanceInfo distanceInfo = q.poll();
        Position p = distanceInfo.p;

        long distanceSoFar = distanceInfo.distance;

        getNeighbors(p).forEach(neighbor ->{
            int cost = data[neighbor.y][neighbor.x];

            long newDistance = distanceSoFar + cost;

            Long knownDistance = distancesFromStart.getOrDefault(neighbor, Long.MAX_VALUE);

            if(knownDistance > newDistance) {
                childToParent.put(neighbor, p);
                distancesFromStart.put(neighbor, newDistance);
                q.add(new DistanceInfo(neighbor, newDistance));
            }
        });
    }

    static class DistanceInfo{
        Position p;
        long distance;

        public DistanceInfo(Position p, long distance) {
            this.p = p;
            this.distance = distance;
        }
    }

    private static Set<Position> getNeighbors(Position p){
        Set<Position> result = new HashSet<>();

        Position north = new Position(p.x, p.y - 1);
        if(positions.contains(north)){
            result.add(north);
        }

        Position south = new Position(p.x, p.y + 1);
        if(positions.contains(south)){
            result.add(south);
        }

        Position west = new Position(p.x - 1, p.y);
        if(positions.contains(west)){
            result.add(west);
        }

        Position east = new Position(p.x + 1, p.y);
        if(positions.contains(east)){
            result.add(east);
        }

        return result;
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
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
