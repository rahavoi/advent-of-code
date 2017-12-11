package rahavoi.advent.of.code;

import java.util.Arrays;

public class Day11 {
    static int maxDistance;
    static int x;
    static int y;

    public static void main(String[] args) {
        Arrays.stream(Util.loadFileAsString("day11.txt")
                .split(","))
                .map(s -> Direction.valueOf(s))
                .forEach(direction -> {
                    move(direction);
                });

        x = (x >= 0) ? x : x * -1;
        y = (y >= 0) ? y : y * -1;

        int stepsToDest = (x > y) ? x : y;

        System.out.println("Steps to dest: " + stepsToDest);
        System.out.println("Max distance: " + maxDistance);

    }

    public enum Direction {
        n, ne, se, s, sw, nw;
    }

    public static void move(Direction direction) {
        switch (direction) {
        case n:
            x++;
            break;
        case ne:
            x++;
            y++;
            break;
        case nw:
            x++;
            y--;
            break;
        case s:
            x--;
            break;
        case se:
            x--;
            y++;
            break;
        case sw:
            x--;
            y--;
            break;
        default:
            break;
        }

        int currentXDist = (x >= 0) ? x : x * -1;
        int currentYDist = (y >= 0) ? y : y * -1;

        if (maxDistance < currentXDist) {
            maxDistance = currentXDist;
        }

        if (maxDistance < currentYDist) {
            maxDistance = currentYDist;
        }
    }
}
