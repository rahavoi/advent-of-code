package com.advent.of.code._2017;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
    static List<Integer> nums;
    private static int steps = 0;

    public static void main(String[] args) {
        String data = Util.loadFileAsString("day5.txt");
        nums = Arrays.asList(data.split("\n")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        int next = 0;
        try {
            while (true) {
                next = jump(next);
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(steps);
        }
    }

    public static int jump(int position) {
        int nextJump = nums.get(position);
        nums.set(position, (nextJump >= 3) ? nextJump - 1 : nextJump + 1);
        steps++;
        return position + nextJump;
    }
}
