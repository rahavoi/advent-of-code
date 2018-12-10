package com.advent.of.code._2017;

import java.util.ArrayList;
import java.util.List;

public class Day17 {
    public static void main(String[] args) {
        task1();
        task2();
    }

    private static void task1() {
        int step = 314;
        int currentPos = 0;
        List<Integer> path = new ArrayList<>();
        path.add(0);

        for (int i = 1; i < 2018; i++) {
            int insertAt = ((step + currentPos) % path.size()) + 1;
            path.add(insertAt, i);
            currentPos = insertAt;
        }

        System.out.println(path.get(path.indexOf(2017) + 1));
    }

    public static void task2() {
        int step = 314;
        int currentPos = 0;
        int numAfterZero = 0;
        List<Integer> path = new ArrayList<>();
        path.add(0);

        int stormSize = 1;

        for (int i = 1; i < 50000000; i++) {
            int insertAt = ((step + currentPos) % stormSize) + 1;

            if (insertAt == 1) {
                numAfterZero = i;
            }

            currentPos = insertAt;
            stormSize++;
        }

        System.out.println(numAfterZero);
    }

}
