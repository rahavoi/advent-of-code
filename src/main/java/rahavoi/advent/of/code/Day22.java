package rahavoi.advent.of.code;

import java.util.HashMap;
import java.util.Map;

public class Day22 {
    private static final char CLEAN = '.';
    private static final char WEAKENED = 'W';
    private static final char INFECTED = '#';
    private static final char FLAGGED = 'F';
    private static int infectionsCount;
    private static char[][] grid;
    private static int currentRow;
    private static int currentColumn;
    private static Direction currentDirection = Direction.NORTH;

    private static Map<Coordinate, Character> outOfGridNodes = new HashMap<>();

    public static void main(String[] args) {
        init();

        currentRow = grid.length / 2;
        currentColumn = grid[0].length / 2;

        for (int i = 0; i < 10000000; i++) {
            proceed();
        }

        System.out.println(infectionsCount);
    }

    private static void proceed() {
        char currentNode;
        try {
            currentNode = grid[currentRow][currentColumn];
        } catch (ArrayIndexOutOfBoundsException e) {
            Character outNode = outOfGridNodes.get(new Coordinate(currentRow, currentColumn));
            if (outNode != null) {
                currentNode = outNode;
            } else {
                currentNode = '.';
            }

        }

        char newValue;

        if (currentNode == CLEAN) {
            newValue = WEAKENED;
            turnLeft();
        } else if (currentNode == WEAKENED) {
            newValue = INFECTED;
            infectionsCount++;
        } else if (currentNode == INFECTED) {
            newValue = FLAGGED;
            turnRight();
        } else {
            newValue = CLEAN;
            turnLeft();
            turnLeft();
        }

        try {
            grid[currentRow][currentColumn] = newValue;

        } catch (ArrayIndexOutOfBoundsException e) {
            // It's ok, we're out of the visible grid;
            outOfGridNodes.put(new Coordinate(currentRow, currentColumn), newValue);
        }

        move();
    }

    public static class Coordinate {
        private int row;
        private int column;

        public Coordinate(int row, int column) {
            super();
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Coordinate) {
                Coordinate c = (Coordinate) obj;
                return this.getColumn() == c.getColumn() && this.getRow() == c.getRow();
            }

            return false;
        }

        public int hashCode() {
            return row + column;
        }

    }

    private enum Direction {
        NORTH, EAST, SOUTH, WEST;
    }

    private static void move() {
        switch (currentDirection) {
        case NORTH:
            currentRow--;
            break;
        case WEST:
            currentColumn--;
            break;
        case SOUTH:
            currentRow++;
            break;
        case EAST:
            currentColumn++;
            break;
        default:
            break;
        }
    }

    private static void turnLeft() {
        switch (currentDirection) {
        case NORTH:
            currentDirection = Direction.WEST;
            break;
        case WEST:
            currentDirection = Direction.SOUTH;
            break;
        case SOUTH:
            currentDirection = Direction.EAST;
            break;
        case EAST:
            currentDirection = Direction.NORTH;
            break;
        default:
            break;
        }
    }

    private static void turnRight() {
        switch (currentDirection) {
        case NORTH:
            currentDirection = Direction.EAST;
            break;
        case WEST:
            currentDirection = Direction.NORTH;
            break;
        case SOUTH:
            currentDirection = Direction.WEST;
            break;
        case EAST:
            currentDirection = Direction.SOUTH;
            break;
        default:
            break;
        }
    }

    private static void init() {
        String[] lines = Util.loadFileAsString("day22.txt")
                .split("\n");

        grid = new char[lines.length][lines[0].length()];

        for (int i = 0; i < lines.length; i++) {
            char[] chars = lines[i].toCharArray();

            for (int j = 0; j < chars.length; j++) {
                grid[i][j] = chars[j];
            }
        }
    }
}
