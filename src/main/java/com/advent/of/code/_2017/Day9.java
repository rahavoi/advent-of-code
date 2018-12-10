package com.advent.of.code._2017;

public class Day9 {
    static char GROUP_START = '{';
    static char GROUP_END = '}';
    static char GARBAGE_START = '<';
    static char GARBAGE_END = '>';
    static char IGNORE = '!';

    static boolean ignoreNext = false;
    static boolean insideGarbage = false;
    static int pointsForGroupEnd = 0;
    static int totalGroupCount = 0;
    static int totalPoints = 0;
    static int garbageCount = 0;

    public static void main(String[] args) {
        char[] chars = Util.loadFileAsString("day9.txt")
                .toCharArray();

        for (char ch : chars) {
            if (!ignoreNext) {
                if (ch == IGNORE) {
                    ignoreNext = true;
                } else if (ch == GARBAGE_START) {
                    if (insideGarbage) {
                        garbageCount++;
                    } else {
                        insideGarbage = true;
                    }
                } else if (ch == GARBAGE_END) {
                    insideGarbage = false;
                }

                if (!insideGarbage) {
                    if (ch == GROUP_START) {
                        pointsForGroupEnd++;
                    } else if (ch == GROUP_END) {
                        totalGroupCount++;
                        totalPoints += pointsForGroupEnd;
                        pointsForGroupEnd--;
                    }
                } else {
                    if (ch != IGNORE && ch != GARBAGE_START) {
                        garbageCount++;
                    }
                }

            } else {
                ignoreNext = false;
            }
        }

        System.out.println("Total groups: " + totalGroupCount);
        System.out.println("Total points: " + totalPoints);
        System.out.println("Total garbage: " + garbageCount);

    }
}
