package com.advent.of.code._2018;

import javafx.util.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6 {
    public static void main(String[] args) throws Exception{
       new Day6().task1();
        new Day6().task2(10000);
    }

    public void task1() throws Exception{
        List<Pair<Integer, Integer>> startPoints = Files.readAllLines (Paths.get("src/main/resources/2018/Day6.txt"))
            .stream().map(line -> {
                String[] parts = line.split(", ");
                return new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            }).collect(Collectors.toList());

        Pair<Integer, Integer> west = startPoints.stream().min(Comparator.comparing(Pair::getKey)).get();
        Pair<Integer, Integer> east = startPoints.stream().max(Comparator.comparing(Pair::getKey)).get();
        Pair<Integer, Integer> south = startPoints.stream().min(Comparator.comparing(Pair::getValue)).get();
        Pair<Integer, Integer> north = startPoints.stream().max(Comparator.comparing(Pair::getValue)).get();

        Set<Pair<Integer, Integer>> occupiedPoints = new HashSet<>();

        occupiedPoints.addAll(startPoints);

        //Initialize score map:
        Map<Pair<Integer, Integer>, Integer> areas = new HashMap<>();
        startPoints.stream().forEach(p -> areas.put(p, 1));

        //Mapping points to their origin. Start points do not have origin so they point to themselves
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> pointOrigins = new HashMap<>();
        startPoints.stream().forEach(p -> pointOrigins.put(p, p));

        Set<Pair<Integer, Integer>> currentRoundPoints = new HashSet<>(startPoints);
        Set<Pair<Integer, Integer>> originsGoingOutsideBorders = new HashSet<>();

        while(true){
            Map<Pair<Integer, Integer>, Set<Pair<Integer, Integer>>> pointsClaimedByOriginsInThisRound = new HashMap<>();
            Set<Pair<Integer, Integer>> pointsOccupiedInThisRound = new HashSet<>();

            //1st phase: claiming points
            currentRoundPoints.forEach(currentRoundPoint -> {
                getNeighbors(currentRoundPoint).forEach(p -> claim(p, pointOrigins.get(currentRoundPoint), pointsClaimedByOriginsInThisRound));
            });

            //2nd phase: occupying points not claimed by others
            currentRoundPoints.forEach(currentRoundPoint -> {
                getNeighbors(currentRoundPoint).forEach(neighbor -> {
                    Pair<Integer, Integer> origin = pointOrigins.get(currentRoundPoint);
                    if(!occupiedPoints.contains(neighbor) && pointsClaimedByOriginsInThisRound.get(neighbor).size() == 1){

                        //Growing area occupied by Origin point by one:
                        areas.put(origin, areas.get(origin) + 1);
                        pointOrigins.put(neighbor, origin);
                        pointsOccupiedInThisRound.add(neighbor);

                        if(neighbor.getKey() < west.getKey() - 1 || neighbor.getKey() > east.getKey() + 1 || neighbor.getValue() < south.getValue() - 1 || neighbor.getValue() > north.getValue() + 1){
                            originsGoingOutsideBorders.add(origin);
                        }

                    }
                    //Always Marking as occupied (even if 2 areas are competing for this region and thus neither is able to occupy it)
                    occupiedPoints.add(neighbor);
                });
            });

            Set<Pair<Integer,Integer>> pointsOccupiedWithinBorders = pointsOccupiedInThisRound.stream()
                    .filter(p -> p.getKey() >= west.getKey() - 1 && p.getKey() <= east.getKey() + 1 && p.getValue() >= south.getValue() - 1 && p.getValue() <= north.getValue() + 1)
                    .collect(Collectors.toSet());

            currentRoundPoints = pointsOccupiedInThisRound;

            if(pointsOccupiedWithinBorders.size() == 0){
                break;
            }
        }

        Integer largest = areas.entrySet().stream().filter(area -> !originsGoingOutsideBorders.contains(area.getKey()))
                .max(Comparator.comparing(Map.Entry::getValue)).get().getValue();

        System.out.println("Largest non-infinite area occupied within borders: " + largest);
    }

    private Set<Pair<Integer, Integer>> getNeighbors(Pair<Integer, Integer> point){
        Pair<Integer, Integer> eastNeighbor = new Pair<>(point.getKey() + 1, point.getValue());
        Pair<Integer, Integer> westNeighbor = new Pair<>(point.getKey() - 1, point.getValue());
        Pair<Integer, Integer> northNeighbor = new Pair<>(point.getKey(), point.getValue() + 1);
        Pair<Integer, Integer> southNeighbor = new Pair<>(point.getKey(), point.getValue() - 1);

        Set<Pair<Integer, Integer>> neighbors = new HashSet<>();
        neighbors.add(eastNeighbor);
        neighbors.add(westNeighbor);
        neighbors.add(northNeighbor);
        neighbors.add(southNeighbor);

        return neighbors;
    }

    private void claim(Pair<Integer, Integer> pointToClaim, Pair<Integer, Integer> origin, Map<Pair<Integer, Integer>, Set<Pair<Integer, Integer>>> pointsClaimedByOrigins){
        Set<Pair<Integer, Integer>> originsClaimingThisPoint = pointsClaimedByOrigins.get(pointToClaim);
        if(originsClaimingThisPoint == null){
            originsClaimingThisPoint = new HashSet<>();
        }

        originsClaimingThisPoint.add(origin);

        pointsClaimedByOrigins.put(pointToClaim, originsClaimingThisPoint);
    }

    public void task2(int maxDistance) throws Exception{
        List<Pair<Integer, Integer>> startPoints = Files.readAllLines (Paths.get("src/main/resources/2018/Day6.txt"))
                .stream().map(line -> {
                    String[] parts = line.split(", ");
                    return new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }).collect(Collectors.toList());

        Pair<Integer, Integer> west = startPoints.stream().min(Comparator.comparing(Pair::getKey)).get();
        Pair<Integer, Integer> east = startPoints.stream().max(Comparator.comparing(Pair::getKey)).get();
        Pair<Integer, Integer> south = startPoints.stream().min(Comparator.comparing(Pair::getValue)).get();
        Pair<Integer, Integer> north = startPoints.stream().max(Comparator.comparing(Pair::getValue)).get();

        Set<Pair<Integer, Integer>> pointsWithinBorders = new HashSet<>();
        for(int i = west.getKey() -1; i < east.getKey() + 1; i++){
            for(int j = south.getValue() -1; j < north.getValue() + 1; j++){
                pointsWithinBorders.add(new Pair<>(i, j));
            }
        }

        long totalPointsWithProperDistance = pointsWithinBorders.stream().filter(p -> {
            Integer distanceToAllStartPoints = startPoints.stream().mapToInt(startPoint ->
                    Math.abs(p.getKey() - startPoint.getKey()) + Math.abs(p.getValue() - startPoint.getValue())).sum();

            return distanceToAllStartPoints < maxDistance;
        }).count();

        System.out.println("Day10 with optimal distance: " + totalPointsWithProperDistance);

    }
}
