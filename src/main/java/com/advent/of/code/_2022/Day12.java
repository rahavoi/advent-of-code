package com.advent.of.code._2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 {
    static char[][] map;
    static Position goal;

    static Map<Position, Integer> positionHeights;

    static Set<Position> visited;
    static Set<Position> lowest;

    static  List<Integer> shortestPaths;

    public static void main(String[] args) throws Exception{
        init();

        for(Position l : lowest){
            findShortestPathFrom(l);
            reset();
        }

        System.out.println(shortestPaths.stream().mapToInt(i -> i).min().getAsInt());
    }

    private static void reset(){
        visited = new HashSet<>();
        lowest = new HashSet<>();
    }

    private static void findShortestPathFrom(Position start) throws IOException {
        visited.add(start);

        PriorityQueue<Solution> queue = new PriorityQueue(Comparator.comparing(Solution::getCost));
        queue.add(new Solution(0, List.of(start)));

        while(!queue.isEmpty()){
            Solution cur = queue.poll();
            Position p = cur.path.get(cur.path.size() - 1);

            List<Position> next = getNextOptions(p);

            if(next.contains(goal)){
                shortestPaths.add(cur.cost + 1);
                break;
            } else {
                next.forEach(np -> {
                    visited.add(np);
                    map[np.y][np.x] = '.';
                    List<Position> nextPath = new ArrayList<>(cur.path);
                    nextPath.add(np);
                    Solution nextSolution = new Solution(cur.cost + 1, nextPath);
                    queue.add(nextSolution);
                });
            }
        }
    }

    private static List<Position> getNextOptions(Position position){
        List<Position> neighbors = getNeighbors(position);

        return neighbors
                .stream()
                .filter(d -> !visited.contains(d))
                .filter(d -> positionHeights.get(d) - positionHeights.get(position) <= 1)
                .collect(Collectors.toList());
    }

    private static List<Position> getNeighbors(Position pos){
        return List.of(new Position(pos.x -1 , pos.y),
                        new Position(pos.x + 1, pos.y),
                        new Position(pos.x, pos.y - 1),
                        new Position(pos.x, pos.y + 1)
                ).stream()
                .filter(p -> p.x >= 0 && p.x < map[0].length && p.y >= 0 && p.y < map.length)
                .collect(Collectors.toList());
    }

    private static void init() throws IOException {
        positionHeights = new HashMap<>();
        shortestPaths = new ArrayList<>();
        reset();
        List<String> input = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day12.txt"));
        map = new char[input.size()][input.get(0).length()];

        for(int i = 0; i < input.size(); i++){
            String line = input.get(i);

            for(int j = 0; j < line.length(); j++){
                char c = line.charAt(j);

                map[i][j] = c;

                Position position = new Position(j, i);

                if(c == 'a'){
                    lowest.add(position);
                }

                if(c == 'S'){
                    lowest.add(position);
                    positionHeights.put(position, (int) 'a');
                } else if(c == 'E'){
                    goal = position;
                    positionHeights.put(position, (int) 'z');
                } else {
                    positionHeights.put(position, (int) c);
                }

            }
        }
    }

    static class Solution{
        int cost;
        List<Position> path;

        public Solution(int cost, List<Position> path) {
            this.cost = cost;
            this.path = path;
        }

        public int getCost() {
            return cost;
        }
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
