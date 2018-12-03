package rahavoi.advent.of.code._2017;

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
