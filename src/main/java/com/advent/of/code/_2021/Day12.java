package com.advent.of.code._2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day12 {
    static Map<String, Set<String>> fromToMap = new HashMap<>();
    static Set<List<String>> allPaths = new HashSet<>();

    public static void main(String[] args) throws Exception {
        parseData();
        fromToMap.get("start").forEach(dest -> visitCave(dest, List.of("start"), new HashMap<>()));
        System.out.println(allPaths.size());
    }

    private static void parseData() throws IOException {
        Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day12.txt")).forEach(line -> {
            String[] parts = line.split("-");
            String a = parts[0];
            String b = parts[1];

            Set<String> aDest = fromToMap.getOrDefault(a, new HashSet<>());
            aDest.add(b);
            fromToMap.put(a, aDest);

            Set<String> bDest = fromToMap.getOrDefault(b, new HashSet<>());
            bDest.add(a);
            fromToMap.put(b, bDest);
        });
    }

    public static void visitCave(String cave, List<String> path, Map<String, Integer> visitedSmall) {
        if(cave.equals("start")){
            return;
        }

        List<String> newPath = new ArrayList<>(path);
        newPath.add(cave);

        if(cave.equals("end")){
            allPaths.add(newPath);
        } else {

            if(!isBigCave(cave)){
                int visitedTimes = visitedSmall.getOrDefault(cave, 0);
                visitedSmall.put(cave, visitedTimes + 1);
            }

            boolean visitedASmallCaveTwice = visitedSmall.values().stream().anyMatch(v -> v >= 2);

            fromToMap.getOrDefault(cave, new HashSet<>())
                .stream()
                .filter(c -> visitedASmallCaveTwice ? visitedSmall.getOrDefault(c, 0) == 0 : visitedSmall.getOrDefault(c, 0) < 2)
                .forEach(c -> visitCave(c, newPath, new HashMap<>(visitedSmall)));
        }
    }

    public static boolean isBigCave(String s) {
        return s.chars().mapToObj(c -> (char)c).noneMatch(Character::isLowerCase);
    }
}
