package rahavoi.advent.of.code._2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Day24 {
    private static List<String> elements;

    public static void main(String[] args) {
        elements = Arrays.asList(Util.loadFileAsString("day24.txt")
                .split("\n"));

        List<Set<String>> bridges = new ArrayList<>();

        // Start from zero;
        elements.stream().filter(l -> l.startsWith("0")).forEach(startingWith0 -> {
            Set<String> bridge = new HashSet<>();
            bridge.add(startingWith0);
            bridges.addAll(build(startingWith0.split("/")[1], bridge));
        });

        int maxSize = bridges.stream().max((b1, b2) -> b1.size() - b2.size()).get().size();

        bridges.stream().filter(b -> b.size() == maxSize).forEach(longest -> System.out.println(calclulateStrength(longest)));
    }

    private static void print(Set<String> bridge) {
        StringJoiner sj = new StringJoiner("--");
        bridge.forEach(s -> {
            sj.add(s);
        });

        System.out.println(sj.toString() + " (" + calclulateStrength(bridge) + ")");
    }

    private static int calclulateStrength(Set<String> bridge) {
        int strength = 0;
        for (String s : bridge) {
            String[] ports = s.split("/");
            strength += Integer.parseInt(ports[0]);
            strength += Integer.parseInt(ports[1]);
        }

        return strength;
    }

    private static List<Set<String>> build(String port, Set<String> bridge) {
        List<Set<String>> combinations = new ArrayList<>();

        Set<String> matches = elements.stream()
                .filter(e -> {
                    String[] ports = e.split("/");
                    boolean result = (port.equals(ports[0]) || port.equals(ports[1])) && !bridge.contains(e);
                    return result;
                }).collect(Collectors.toSet());

        if (matches.isEmpty()) {
            combinations.add(bridge);
        } else {
            matches.forEach(match -> {
                String[] ports = match.split("/");
                String newPort = (ports[0].equals(port)) ? ports[1] : ports[0];
                Set<String> biggerBridge = new HashSet<>(bridge);
                biggerBridge.add(match);

                combinations.addAll(build(newPort, biggerBridge));
            });
        }

        return combinations;

    }

}
