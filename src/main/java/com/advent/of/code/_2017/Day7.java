package com.advent.of.code._2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7 {
    static Set<String> programNames = new HashSet<>();
    static Set<String> nonBottomPrograms = new HashSet<>();
    static Map<String, Program> programMap = new HashMap<>();

    static Map<Integer, Set<Program>> previuouslyAnalyzedTowerWeights;

    public static void main(String[] args) {
        List<String> input = Arrays.asList(Util.loadFileAsString("day7.txt")
                .split("\n"));

        input.forEach(record -> {
            String name = record.split(" ")[0];
            int weight = Integer.parseInt(record.substring(record.indexOf("(") + 1, record.indexOf(")")));

            String[] temp = record.split("->");
            List<String> upperPrograms = new ArrayList<>();

            if (temp.length == 2) {
                upperPrograms.addAll(Arrays.asList(temp[1].split(","))
                        .stream().map(s -> s.trim()).collect(Collectors.toList()));

                nonBottomPrograms.addAll(upperPrograms);
            }

            programNames.add(name);
            programMap.put(name, new Program(name, weight, upperPrograms));

        });

        programNames.forEach(program -> {
            if (!nonBottomPrograms.contains(program)) {
                System.out.println("Bottom program: " + program);
                findUnbalancedTower(programMap.get(program));
            }
        });

    }

    private static Program findUnbalancedTower(Program bottomProgram) {
        Set<Program> upperProgs = bottomProgram.getUpperPrograms().stream().map(programName -> programMap.get(programName)).collect(Collectors.toSet());

        Map<Integer, Set<Program>> towerWeights = new HashMap<>();

        upperProgs.forEach(upperProgram -> {
            int towerWeight = getTowerWeight(upperProgram);

            Set<Program> programsWithThisWeight = towerWeights.get(towerWeight);
            if (programsWithThisWeight == null) {
                programsWithThisWeight = new HashSet<>();
                towerWeights.put(towerWeight, programsWithThisWeight);
            }

            programsWithThisWeight.add(upperProgram);
        });

        List<Program> suspiciousPrograms = towerWeights.values().stream().filter(towers -> towers.size() == 1)
                .map(towers -> towers.iterator().next()).collect(Collectors.toList());

        if (!suspiciousPrograms.isEmpty()) {
            previuouslyAnalyzedTowerWeights = towerWeights;
            suspiciousPrograms.forEach(program -> findUnbalancedTower(program));
        } else {
            Integer correctTowerWeight = previuouslyAnalyzedTowerWeights.keySet().stream().filter(key -> previuouslyAnalyzedTowerWeights.get(key).size() > 1)
                    .findFirst().get();

            System.out.println("The program " + bottomProgram.getName() + "  has incorrect weight: " + bottomProgram.getWeight());
            int subTowerWeight = getTowerWeight(upperProgs.iterator().next());

            System.out.println("Correct program weight: " + (correctTowerWeight - (upperProgs.size() * subTowerWeight)));

        }

        return null;

    }

    private static int getTowerWeight(Program program) {
        int totalWeight = program.getWeight();

        List<String> upperPogramNames = program.getUpperPrograms();

        for (String programName : upperPogramNames) {
            Program upperProgram = programMap.get(programName);
            totalWeight += getTowerWeight(upperProgram);

        }

        return totalWeight;
    }

    public static class Program {
        private String name;
        private Integer weight;

        private List<String> upperPrograms;

        public Program(String name, Integer weight, List<String> upperPrograms) {
            super();
            this.name = name;
            this.weight = weight;
            this.upperPrograms = upperPrograms;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public List<String> getUpperPrograms() {
            return upperPrograms;
        }

        public void setUpperPrograms(List<String> upperPrograms) {
            this.upperPrograms = upperPrograms;
        }

    }

}
