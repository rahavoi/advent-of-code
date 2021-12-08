package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day8 {
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day8.txt"));

        Integer result = input.stream().mapToInt(l -> {
            String[] parts = l.split("\\|");
            String[] in = parts[0].trim().split(" ");
            String[] out = parts[1].trim().split(" ");

            Map<Integer, String> map = new HashMap<>();

            Arrays.stream(in)
                .forEach(d -> {
                    if(d.length() == 2){
                        map.put(1, d);
                    }

                    if(d.length() == 4){
                        map.put(4, d);
                    }

                    if(d.length() == 3){
                        map.put(7, d);
                    }

                    if(d.length() == 7){
                        map.put(8, d);
                    }
                });

            String six = Arrays.stream(in).filter(d -> d.length() == 6)
                .filter(d -> !containsAllChars(d, map.get(7)))
                .findFirst().get();
            map.put(6, six);


            String zero = Arrays.stream(in).filter(d -> d.length() == 6)
                .filter(d -> !containsAllChars(d, map.get(4)))
                .filter(d -> !d.equals(six))
                .findFirst().get();
            map.put(0, zero);

            String nine = Arrays.stream(in).filter(d -> d.length() == 6)
                .filter(d -> !d.equals(six))
                .filter(d -> !d.equals(zero))
                .findFirst().get();
            map.put(9, nine);

            String five = Arrays.stream(in).filter(d -> d.length() == 5)
                .filter(d -> containsAllChars(six, d))
                .findFirst().get();
            map.put(5, five);

            String three = Arrays.stream(in).filter(d -> d.length() == 5)
                .filter(d -> containsAllChars(d, map.get(7)))
                .findFirst().get();
            map.put(3, three);

            String two = Arrays.stream(in).filter(d -> d.length() == 5)
                .filter(d -> !d.equals(three))
                .filter(d -> !d.equals(five))
                .findFirst().get();
            map.put(2, two);

            StringBuilder sb = new StringBuilder();

            Arrays.stream(out).forEach(e -> {
                Integer key = map.entrySet().stream()
                    .filter(entry -> entry.getValue().length() ==  e.length() && containsAllChars(entry.getValue(), e))
                    .mapToInt(entry -> entry.getKey())
                    .findFirst()
                    .getAsInt();

                sb.append(key);
            });

            return Integer.parseInt(sb.toString());
        }).sum();

        System.out.println(result);

    }

    public static boolean containsAllChars(String container, String containee) {
        return stringToCharacterSet(container).containsAll(stringToCharacterSet(containee));
    }

    public static Set<Character> stringToCharacterSet(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        return set;
    }

    private void part1() throws Exception {
        List<String> input = Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day8.txt"));


        int r = input.stream().mapToInt(l -> {
            String[] output = l.split("\\|")[1].trim().split(" ");


            int result = Arrays.stream(output)
                .mapToInt(e -> e.length() == 2 || e.length() == 4 || e.length() == 3 || e.length() == 7 ? 1 : 0)
                .sum();

            return result;

        }).sum();

        System.out.println(r);
    }
}
