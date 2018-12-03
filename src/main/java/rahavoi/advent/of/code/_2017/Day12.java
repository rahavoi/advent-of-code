package rahavoi.advent.of.code._2017;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day12 {

    static int lastSize = -1;
    static Map<String, Set<String>> programMap = new HashMap<>();
    static Set<String> programsConnectedToZero = new HashSet<>();
    static Set<String> allPrograms = new HashSet<>();
    static int totalGroups;
    static int notConnectedYet;

    public static void main(String[] args) {
        programsConnectedToZero.add("0");

        while (true) {
            traverse();
            if (lastSize == programsConnectedToZero.size()) {
                break;
            } else {
                lastSize = programsConnectedToZero.size();
            }
        }

        System.out.println("Connected to zero");
        System.out.println(programsConnectedToZero.size());
        programMap.put("0", programsConnectedToZero);

        totalGroups++;

        Set<String> belongingToAGroup = programMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        for (String program : allPrograms) {
            if (!belongingToAGroup.contains(program)) {
                System.out.println("Does not belong to a group. Yet: " + program);
                System.out.println("Looking..");

                int prevSize = -1;
                Set<String> connectedProgs = new HashSet<>();
                while (true) {
                    anotherTraverse(program, connectedProgs);

                    if (prevSize == connectedProgs.size()) {
                        break;
                    } else {
                        prevSize = connectedProgs.size();
                    }
                }

                System.out.println("Done! Found " + connectedProgs.size() + " Connected to this group!");
                programMap.put(program, connectedProgs);
                belongingToAGroup.add(program);
                belongingToAGroup.addAll(connectedProgs);

            }
        }

        System.out.println("Total groups: ");
        System.out.println(programMap.size());
    }

    private static void traverse() {
        Arrays.asList(Util.loadFileAsString("day12.txt").split("\n"))
                .forEach(line -> {
                    String[] elements = line.split("<->");
                    String programName = elements[0].trim();

                    allPrograms.add(programName);

                    String connectedPrograms = elements[1].trim();

                    if ("0".equals(programName)) {
                        Arrays.stream(connectedPrograms.split(","))
                                .forEach(program -> programsConnectedToZero.add(program.trim()));
                    }

                    checkConnected(connectedPrograms, programName);
                });
    }

    private static void anotherTraverse(String prog, Set<String> connectedProgs) {
        Arrays.asList(Util.loadFileAsString("day12.txt").split("\n"))
                .forEach(line -> {
                    String[] elements = line.split("<->");
                    String programName = elements[0].trim();
                    String connectedPrograms = elements[1].trim();

                    if (!programMap.containsValue(programName)) {
                        if (prog.equals(programName)) {
                            Arrays.stream(connectedPrograms.split(","))
                                    .forEach(program -> connectedProgs.add(program.trim()));
                        }

                        List<String> connectedProgramList = Arrays.stream(connectedPrograms.split(","))
                                .map(s -> s.trim()).collect(Collectors.toList());

                        Optional<String> connected = connectedProgramList.stream()
                                .filter(program -> connectedProgs.contains(program))
                                .findFirst();

                        if (connected.isPresent()) {
                            connectedProgs.addAll(connectedProgramList);
                        }

                    }
                });
    }

    private static void checkConnected(String connectedPrograms, String programName) {
        Optional<String> connected = Arrays.stream(connectedPrograms.split(","))
                .map(s -> s.trim())
                .filter(program -> programsConnectedToZero.contains(program))
                .findFirst();

        if (connected.isPresent()) {
            programsConnectedToZero.add(programName);
        }
    }
}
