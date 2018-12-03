package rahavoi.advent.of.code._2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6 {
    static Set<List<Integer>> combos = new HashSet<>();
    static int cycles = 0;
    static List<Integer> sameState;

    static int maxPosition;
    static int max;

    static List<Integer> nums;

    public static void main(String[] args) {
        nums = Arrays.asList(Util.loadFileAsString("day6.txt")
                .split(" +")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        while (true) {
            redistribute();

            if (duplicateStateCheck()) {
                System.out.println(
                        "Redistribution cycles before seeing a config that has been seen before: " + cycles);
                cycles = 0;
                sameState = new ArrayList<>(nums);
                continue;
            }

            if (sameState != null && sameState.equals(nums)) {
                System.out.println("The size of the loop: " + cycles);
                return;

            }
        }

    }

    private static void redistribute() {
        int maxPosition = getMaxPosition();
        int max = nums.get(maxPosition);

        redistribute(maxPosition, max);
        cycles++;
    }

    private static boolean duplicateStateCheck() {
        return sameState == null && !combos.add(new ArrayList<>(nums));
    }

    private static void redistribute(int position, int value) {
        if (value < nums.size()) {
            nums.set(position, 0);
            for (int i = position + 1; i < nums.size(); i++) {
                nums.set(i, nums.get(i) + 1);
                value--;

                if (value == 0) {
                    return;
                }
            }

            for (int i = 0; i < position; i++) {
                nums.set(i, nums.get(i) + 1);
                value--;

                if (value == 0) {
                    return;
                }

            }

        } else {
            int portion = value / nums.size();
            int remainder = value % nums.size();

            for (int i = position + 1; i < nums.size(); i++) {
                nums.set(i, nums.get(i) + portion);
            }

            for (int i = 0; i < position; i++) {
                nums.set(i, nums.get(i) + portion);
            }

            nums.set(position, (remainder == 0) ? portion : remainder);

        }

    }

    private static int getMaxPosition() {
        int max = 0;
        int maxPosition = 0;

        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);

            if (num > max) {
                max = num;
                maxPosition = i;
            }
        }

        return maxPosition;
    }
}
