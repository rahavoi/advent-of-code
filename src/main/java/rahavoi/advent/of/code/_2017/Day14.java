package rahavoi.advent.of.code._2017;

import java.util.HashSet;
import java.util.Set;

public class Day14 {
    private static final int GRID_SIZE = 128;

    public static void main(String[] args) {
        int countOnes = 0;
        StringBuilder grid = new StringBuilder();
        String input = Util.loadFileAsString("day14.txt").trim();

        for (String row : input.split(",")) {
            StringBuilder sb = getHash(row.trim());

            StringBuilder binarySb = new StringBuilder();
            for (char c : sb.toString().toCharArray()) {
                int decimal = Integer.parseInt(Character.toString(c), 16);
                String binary = Integer.toBinaryString(decimal);

                if (binary.length() < 4) {
                    int addZeros = 4 - binary.length();
                    for (int x = 0; x < addZeros; x++) {
                        binary = "0" + binary;
                    }
                }

                binarySb.append(binary);
            }

            for (char bc : binarySb.toString().toCharArray()) {
                if (bc == '1') {
                    countOnes++;
                }
            }

            grid.append(binarySb.toString() + "\n");
        }

        System.out.println("Filled squares: " + countOnes);
        findGroups(grid.toString());
    }

    private static void findGroups(String sectorGrid) {
        int groupCounter = 0;
        String[] rows = sectorGrid.split("\n");

        Square[][] sGrid = new Square[GRID_SIZE][GRID_SIZE];
        for (int x = 0; x < GRID_SIZE; x++) {
            String row = rows[x];
            char[] chars = row.toCharArray();

            for (int y = 0; y < chars.length; y++) {
                Square square = new Square();
                square.setFilled((chars[y] == '1') ? true : false);
                square.setX(x);
                square.setY(y);
                sGrid[x][y] = square;
            }
        }

        System.out.println(sGrid.length);
        Set<Set<Square>> clusters = getClusters(sGrid);
        System.out.println(clusters.size());
    }

    public static Set<Set<Square>> getClusters(Square[][] grid) {
        Set<Set<Square>> clusters = new HashSet<>();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                Set<Square> cluster = getClusterMembers(grid[x][y], grid);
                if (cluster != null) {
                    clusters.add(cluster);
                }
            }
        }

        return clusters;

    }

    public static Set<Square> getClusterMembers(Square square, Square[][] grid) {
        Set<Square> members = null;
        if (square.isFilled() && !square.isProcessed()) {
            square.setProcessed(true);
            members = new HashSet<>();
            members.add(square);

            Set<Square> neighbors = new HashSet<>();
            Square north = getSquare(square.getX(), square.getY() + 1, grid);
            Square south = getSquare(square.getX(), square.getY() - 1, grid);
            Square east = getSquare(square.getX() + 1, square.getY(), grid);
            Square west = getSquare(square.getX() - 1, square.getY(), grid);

            neighbors.add(north);
            neighbors.add(south);
            neighbors.add(east);
            neighbors.add(west);

            for (Square neighbor : neighbors) {
                if (neighbor != null) {
                    Set<Square> farNeighbors = getClusterMembers(neighbor, grid);
                    if (farNeighbors != null) {
                        members.addAll(farNeighbors);
                    }
                }
            }
        }

        return members;

    }

    public static Square getSquare(int x, int y, Square[][] grid) {
        Square result = null;

        try {
            result = grid[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            // It happens;
        }

        return result;
    }

    private static StringBuilder getHash(String row) {
        int size = 256;
        int[] nums = new int[size];
        int currentPosition = 0;
        int skipSize = 0;

        int KNOT_BLOCK_SIZE = 16;

        char[] characters = row.toCharArray();
        char[] standardSuffixValues = { 17, 31, 73, 47, 23 };

        char[] combined = concatCharArrays(characters, standardSuffixValues);

        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }

        for (int x = 0; x < 64; x++) {
            for (char ch : combined) {
                int from = currentPosition;
                int to = (currentPosition + ch - 1) % size;

                for (int i = 0; i < ch / 2; i++) {
                    if (to == -1) {
                        to = nums.length - 1;
                    }

                    int temp = nums[from];
                    nums[from] = nums[to];
                    nums[to] = temp;

                    from = (from + 1) % size;
                    to = (to - 1) % size;
                }

                currentPosition = (currentPosition + skipSize + ch) % size;

                skipSize++;
            }
        }

        int totalBlocks = 0;
        int[] denseHash = new int[KNOT_BLOCK_SIZE];
        int[] block = new int[KNOT_BLOCK_SIZE];
        for (int i = 0; i < nums.length; i++) {
            int blockIndex = i % KNOT_BLOCK_SIZE;
            block[blockIndex] = nums[i];
            if (blockIndex == KNOT_BLOCK_SIZE - 1) {
                denseHash[totalBlocks] = dense(block);
                totalBlocks++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int num : denseHash) {
            String hex = Integer.toHexString(num);
            if (hex.length() != 2) {
                hex = "0" + hex;
            }
            sb.append(hex);
        }
        return sb;
    }

    private static int dense(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result = result ^ num;
        }

        return result;
    }

    private static char[] concatCharArrays(char[] a, char[] b) {
        char[] c = new char[a.length + b.length];

        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }

        int cPos = a.length;

        for (int j = 0; j < b.length; j++) {
            c[cPos] = b[j];
            cPos++;
        }

        return c;
    }
}
