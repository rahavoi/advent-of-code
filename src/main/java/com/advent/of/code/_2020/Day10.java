package com.advent.of.code._2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 extends Day
{
    public static void main(String[] args) throws IOException
    {
        List<String> lines = lines("src/main/resources/2020/Day10.txt");
        long start = System.currentTimeMillis();
        System.out.println(part1(lines));
        System.out.println(part2(lines));
        System.out.println("Time elapsed:  " + (System.currentTimeMillis() - start) + "ms") ;

    }

    private static long part1(List<String> lines)
    {
        List<Integer> jolts = lines.stream().map(s -> Integer.parseInt(s))
            .collect(Collectors.toList());

        Collections.sort(jolts);

        int countOne = 0;
        int countThree = 0;
        int prev = 0;

        for(int i = 0; i < jolts.size(); i++){
            if(jolts.get(i) - prev == 1){
                countOne++;
            }else if(jolts.get(i) - prev == 3){
                countThree++;
            }
            prev = jolts.get(i);
        }

        return countOne * (countThree + 1);
    }

    private static long part2(List<String> lines) throws IOException
    {
        List<Integer> jolts = lines.stream().map(s -> Integer.parseInt(s))
            .collect(Collectors.toList());

        jolts.add(0); //add outlet
        Collections.sort(jolts);
        jolts.add(jolts.get(jolts.size() - 1) + 3); //add the device to the end of the chain

        Map<Integer, Node> joltsToAdaptor = new HashMap<>();
        jolts.forEach(j -> joltsToAdaptor.put(j, new Node(j)));

        joltsToAdaptor.values().forEach(a -> a.setPossibleAdaptors(joltsToAdaptor));

        StringJoiner sj = new StringJoiner("\n");
        joltsToAdaptor.values().forEach(a -> {
            a.destinations.forEach(d -> {
                sj.add(a.jolts + "," + d.jolts);
            });

        });
        Files.writeString(Paths.get("src/main/resources/2020/Day10_graph.csv"), sj.toString());

        return joltsToAdaptor.get(0).countPathsToDestination();
    }

    /*
        //Solved by transforming the program to csv and uploading it to Neo4j:

        /*

        LOAD CSV WITH HEADERS FROM "file:///tmp2.csv" AS row
        WITH row
        MERGE (p:Object{name: row.PLANET})
        MERGE (s:Object{name: row.SATELLITE})
        MERGE (s)-[r:ORBITS]->(p)

         */

    //Solution in Cypher:

    //Part 1
    //MATCH p=(s:Object)-[r:ORBITS *]->(o:Object{name:'COM'}) RETURN sum(length(p))

    //Part 2:
    //MATCH p=(me:Object{name:'YOU'})-[r:ORBITS *]-(santa:Object{name:'SAN'}) RETURN length(p) - 2;

    static class Node
    {
        int jolts;
        List<Node> destinations = new ArrayList<>();
        Long combinations;

        public Node(int jolts)
        {
            this.jolts = jolts;
        }

        public void setPossibleAdaptors(Map<Integer, Node> joltsToAdapter){
            IntStream.rangeClosed(1, 3).forEach(i -> {
                if(joltsToAdapter.containsKey(jolts + i)){
                    destinations.add(joltsToAdapter.get(jolts + i));
                }
            });
        }

        public long countPathsToDestination(){
            if(combinations != null){
                return combinations;
            }

            if(destinations.isEmpty()){
                return 1L;
            }

            combinations = destinations.stream().mapToLong(Node::countPathsToDestination).sum();
            return combinations;
        }
    }
}
