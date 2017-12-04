package rahavoi.advent.of.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day3 {
    private static int finalNum = 325489;
    private static List<Integer> east = new ArrayList<>(Arrays.asList(25, 1, 2));
    private static List<Integer> north = new ArrayList<>(Arrays.asList(2, 4, 5));
    private static List<Integer> west = new ArrayList<>(Arrays.asList(5, 10, 11));
    private static List<Integer> south = new ArrayList<>(Arrays.asList(11, 23, 25));

    public static void main(String[] args) {
        // task1();

        task2(0);
    }

    public static void task1() {
        int wallSize = 1;
        int odd = 1;
        int oddSquare = 1;

        while (oddSquare <= finalNum) {
            System.out.println("Wallsize: " + wallSize);
            System.out.println(oddSquare);
            System.out.println(odd);
            odd = odd + 2;

            oddSquare = odd * odd;
            wallSize = wallSize + 2;
        }

        // TODO: After finding the closest root, all we need is calculate the difference and steps to the entry point.
    };

    public static void task2(int sumOfLastTwoSouthElements) {
        east = buildExternalSide(0, east, false);
        north = buildExternalSide(getSumofLastTwo(east), north, false);
        west = buildExternalSide(getSumofLastTwo(north), west, false);
        south = buildExternalSide(getSumofLastTwo(west), south, true);

        east.add(0, south.get(south.size() - 1));
        north.add(0, east.get(east.size() - 1));
        west.add(0, north.get(north.size() - 1));
        south.add(0, west.get(west.size() - 1));

        if (south.get(south.size() - 1) > finalNum) {
            System.out.println("Done!");
            return;
        }

        task2(getSumofLastTwo(south));

    };

    private static int getSumofLastTwo(List<Integer> nums) {
        return nums.get(nums.size() - 1) + nums.get(nums.size() - 2);
    }

    private static List<Integer> buildExternalSide(int sumOfTwoLastElementsFromAdjacentSide, List<Integer> oldSide, boolean south) {
        List<Integer> externalSide = new ArrayList<>();

        for (int i = 0; i < oldSide.size(); i++) {
            int num = oldSide.get(i);
            int prev = (i != 0) ? oldSide.get(i - 1) : sumOfTwoLastElementsFromAdjacentSide;
            int next = (i < oldSide.size() - 1) ? oldSide.get(i + 1) : 0;

            int previousInNewSide = (externalSide.isEmpty()) ? 0 : externalSide.get(externalSide.size() - 1);

            int result = num + prev + next + previousInNewSide;
            externalSide.add(result);
            System.out.println(result);
        }

        if (south) {
            externalSide.set(externalSide.size() - 1, externalSide.get(externalSide.size() - 1) + east.get(0));
        }

        int lastElementOfSide = oldSide.get(oldSide.size() - 1) + externalSide.get(externalSide.size() - 1);

        if (south) {
            lastElementOfSide += east.get(0);
        }
        System.out.println(lastElementOfSide);
        externalSide.add(lastElementOfSide);

        return externalSide;
    }

}
