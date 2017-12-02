package rahavoi.advent.of.code;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day1 {
    private static int sum;
    private static List<Integer> numbers = Util.loadFileAsString("day1.txt")
            .chars().mapToObj(ch -> Character.getNumericValue(ch)).collect(Collectors.toList());

    public static void main(String[] main) {
        System.out.println(task1());
        sum = 0;
        System.out.println(task2());
    }

    /**
     * The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits that match the next digit in the list. The
     * list is circular, so the digit after the last digit is the first digit in the list.
     * 
     * For example:
     * 
     * 1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit. 1111 produces 4
     * because each digit (all 1) matches the next. 1234 produces 0 because no digit matches the next. 91212129 produces 9 because the only digit that matches
     * the next one is the last digit, 9. What is the solution to your captcha?
     * 
     * @author irahavoi
     *
     */
    private static int task1() {
        if (numbers.get(0) == numbers.get(numbers.size() - 1)) {
            sum += numbers.get(0);
        }

        IntStream.range(0, numbers.size() - 1)
                .forEach(i -> {
                    if (numbers.get(i) == numbers.get(i + 1))
                        sum += numbers.get(i);
                });

        return sum;
    }

    /**
     * Now, instead of considering the next digit, it wants you to consider the digit halfway around the circular list. That is, if your list contains 10 items,
     * only include a digit in your sum if the digit 10/2 = 5 steps forward matches it. Fortunately, your list has an even number of elements.
     * 
     * For example:
     * 
     * 1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead. 1221 produces 0, because every comparison is between a 1
     * and a 2. 123425 produces 4, because both 2s match each other, but no other digit has a match. 123123 produces 12. 12131415 produces 4. What is the
     * solution to your new captcha?
     * 
     * 
     */
    private static int task2() {
        IntStream.range(0, numbers.size() - 1)
                .forEach(i -> {
                    if (numbers.get(i) == getOposite(i))
                        sum += numbers.get(i);
                });

        return sum;
    }

    private static int getOposite(int index) {
        int half = numbers.size() / 2;

        int oppositeIndex = (index + half <= numbers.size() - 1) ? index + half : index + half - (numbers.size());

        return numbers.get(oppositeIndex);

    }
}
