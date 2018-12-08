package com.advent.of.code._2018;

import javafx.util.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {
    private Map<Integer, Node> nodes = new HashMap<>();
    private int nodeCounter;

    public static void main(String[] args) throws Exception{
        int[] nums = Arrays.stream(Files.readAllLines(Paths.get("src/main/resources/2018/Day8.txt"))
                .get(0).split(" ")).mapToInt(Integer::parseInt).toArray();

        Day8 task = new Day8();
        task.parseNode(0, nums, new ArrayList<>());

        int metadataSum = task.nodes.values().stream()
                .map(node -> node.metadata)
                .flatMap(List::stream)
                .mapToInt(v -> v)
                .sum();

        System.out.println("Task1: " + metadataSum);
        System.out.println("Task2: " + task.nodes.get(0).getValue());
    }

    private class Node{
        List<Node> children;
        List<Integer> metadata;

        private Node(List<Node> children, List<Integer> metadata) {
            this.children = children;
            this.metadata = metadata;
        }

        public int getValue(){
            if(children.isEmpty()){
                return metadata.stream().mapToInt(Integer::intValue).sum();
            } else{
                int value = 0;

                for (Integer aMetadata : metadata) {
                    int index = aMetadata - 1;

                    if (index >= 0 && index < children.size()) {
                        value += children.get(index).getValue();
                    }
                }

                return value;
            }
        }
    }

    private Pair<Integer, Node> parseNode(int pos, int[] nums, List<Node> siblings){
        int nodePosition = nodeCounter++;

        //First 2 elements are always number of child nodes and metadata entries:
        int numberOfChildNodes = nums[pos];
        int numberOfMetadataEntries = nums[++pos];

        List<Node> children = new ArrayList<>();
        List<Integer> metadata = new ArrayList<>();
        Node node = new Node(children, metadata);

        siblings.add(node);

        int nextPos = pos;
        for(int i = 0; i < numberOfChildNodes; i++){
            nextPos = parseNode(nextPos + 1, nums, children).getKey();
        }

        for(int i = 0; i < numberOfMetadataEntries; i++){
            int metaDataEntry = nums[++nextPos];
            metadata.add(metaDataEntry);
        }

        nodes.put(nodePosition, node);
        return new Pair<>(nextPos, node);
    }
}
