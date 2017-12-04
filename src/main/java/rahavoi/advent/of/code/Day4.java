package rahavoi.advent.of.code;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day4 {
    private static int countValid = 0;

    public static void main(String[] args) {
        // task1();

        task2();
    }

    private static void task2() {
        Arrays.asList(Util.loadFileAsString("day4.txt")
                .split("\n")).forEach(record -> {

                    List<String> words = Arrays.asList(record.split(" "));

                    List<String> uniqueWords = words.stream().distinct().collect(Collectors.toList());

                    if (words.size() == uniqueWords.size() && !containsAnagrams(words)) {
                        countValid++;
                    }

                });

        System.out.println(countValid);
    }

    private static boolean containsAnagrams(List<String> words) {
        Map<String, Integer> anagrams = new HashMap<>();

        words.forEach(word -> {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            int times = anagrams.get(sorted) == null ? 1 : anagrams.get(sorted) + 1;
            anagrams.put(sorted, times);
        });

        return anagrams.values().stream().anyMatch(value -> value > 1);
    }

    private static void task1() {
        Arrays.asList(Util.loadFileAsString("day4.txt")
                .split("\n")).forEach(record -> {

                    List<String> words = Arrays.asList(record.split(" "));

                    List<String> uniqueWords = words.stream().distinct().collect(Collectors.toList());

                    if (words.size() != uniqueWords.size()) {
                        countValid++;
                    }

                });

        System.out.println(countValid);
    }
}
