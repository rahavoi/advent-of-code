package rahavoi.advent.of.code._2017;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    private static int sum = 0;

    public static void main(String[] args) {
        try {
            List<String> records = Arrays.asList(Util.loadFileAsString("day2.txt").split("\n"));
            System.out.println(task1(records));
            sum = 0;
            System.out.println(task2(records));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int task1(List<String> records) throws IOException {
        records.forEach(record -> processRecordTask1(record));
        return sum;
    }

    public static int task2(List<String> records) throws IOException {
        records.forEach(record -> processRecordTask2(record));
        return sum;
    }

    private static void processRecordTask1(String record) {
        List<Integer> elements = Arrays.asList(record.trim().split(" "))
                .stream().filter(s -> s.length() != 0)
                .map(s -> new Integer(s)).collect(Collectors.toList());

        int max = elements.stream().max((i1, i2) -> i1.compareTo(i2)).get();
        int min = elements.stream().min((i1, i2) -> i1.compareTo(i2)).get();

        sum += max - min;
    }

    private static void processRecordTask2(String record) {
        List<Integer> elements = Arrays.asList(record.trim().split(" "))
                .stream().filter(s -> s.length() != 0)
                .map(s -> new Integer(s)).collect(Collectors.toList());

        outerloop: for (int i = 0; i < elements.size(); i++) {
            Integer element = elements.get(i);
            for (int z = i + 1; z < elements.size(); z++) {
                if (isEvenlyDivisible(element, elements.get(z))) {
                    int divisionResult = (element > elements.get(z)) ? element / elements.get(z) : elements.get(z) / element;
                    sum += divisionResult;
                    break outerloop;
                }
            }
        }
    }

    private static boolean isEvenlyDivisible(int x, int y) {
        return x % y == 0 || y % x == 0;
    }
}
