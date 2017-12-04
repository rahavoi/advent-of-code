package rahavoi.advent.of.code;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day4 {
    private static int countValid = 0;

    public static void main(String[] args) {
        Arrays.asList(Util.loadFileAsString("day4.txt")
                .split("\n")).forEach(record -> {
                    // rocessRecordTask1(record);
                    processRecordTask2(record);

                });

        System.out.println(countValid);
    }

    private static void processRecordTask1(String record) {
        List<String> words = Arrays.asList(record.split(" "));

        List<String> uniqueWords = words.stream().distinct().collect(Collectors.toList());

        if (words.size() == uniqueWords.size()) {
            countValid++;
        }
    }

    private static void processRecordTask2(String record) {
        List<String> words = Arrays.asList(record.split(" "));

        List<String> uniqueWords = words.stream().distinct().collect(Collectors.toList());

        if (words.size() == uniqueWords.size() && !containsAnagrams(words)) {
            countValid++;
        }
    }

    private static boolean containsAnagrams(List<String> words) {
        Map<String, Integer> anagrams = new HashMap<>();

        return words.stream().filter(word -> {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            int times = anagrams.get(sorted) == null ? 1 : anagrams.get(sorted) + 1;
            anagrams.put(sorted, times);

            return times > 1;
        }).findFirst().isPresent();

    }
}
