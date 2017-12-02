package rahavoi.advent.of.code;

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

    /**
     * The spreadsheet consists of rows of apparently-random numbers. To make sure the recovery process is on the right track, they need you to calculate the
     * spreadsheet's checksum. For each row, determine the difference between the largest value and the smallest value; the checksum is the sum of all of these
     * differences.
     * 
     * For example, given the following spreadsheet:
     * 
     * 5 1 9 5 7 5 3 2 4 6 8 The first row's largest and smallest values are 9 and 1, and their difference is 8. The second row's largest and smallest values
     * are 7 and 3, and their difference is 4. The third row's difference is 6. In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.
     * 
     * What is the checksum for the spreadsheet in your puzzle input?
     * 
     * 
     * @param data
     */
    private static void processRecordTask1(String record) {
        List<Integer> elements = Arrays.asList(record.trim().split(" "))
                .stream().filter(s -> s.length() != 0)
                .map(s -> new Integer(s)).collect(Collectors.toList());

        int max = elements.stream().max((i1, i2) -> i1.compareTo(i2)).get();
        int min = elements.stream().min((i1, i2) -> i1.compareTo(i2)).get();

        sum += max - min;
    }

    /**
     * "Great work; looks like we're on the right track after all. Here's a star for your effort." However, the program seems a little worried. Can programs be
     * worried?
     * 
     * "Based on what we're seeing, it looks like all the User wanted is some information about the evenly divisible values in the spreadsheet. Unfortunately,
     * none of us are equipped for that kind of calculation - most of us specialize in bitwise operations."
     * 
     * It sounds like the goal is to find the only two numbers in each row where one evenly divides the other - that is, where the result of the division
     * operation is a whole number. They would like you to find those numbers on each line, divide them, and add up each line's result.
     * 
     * For example, given the following spreadsheet:
     * 
     * 5 9 2 8 9 4 7 3 3 8 6 5 In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4. In the second row, the
     * two numbers are 9 and 3; the result is 3. In the third row, the result is 2. In this example, the sum of the results would be 4 + 3 + 2 = 9.
     * 
     * What is the sum of each row's result in your puzzle input?
     * 
     * @param data
     */
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
