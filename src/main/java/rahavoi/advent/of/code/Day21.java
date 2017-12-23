package rahavoi.advent.of.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 {
    private static Map<String, char[][]> enhancementRules = new HashMap<>();

    public static void main(String[] args) {
        char[][] start = getMatrix(".#./..#/###");
        Arrays.asList(Util.loadFileAsString("day21.txt").split("\n"))
                .forEach(s -> {
                    String[] elements = s.split(" => ");
                    String inStr = elements[0];
                    String outStr = elements[1];

                    char[][] input = getMatrix(inStr);
                    char[][] output = getMatrix(outStr);

                    enhancementRules.put(toString(input), output);
                });

        char[][] result = start;
        for (int i = 0; i < 18; i++) {
            result = enhance(result);
        }

        print(result);

        int count = 0;
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                char c = result[i][j];
                if (c == '#') {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    private static String toString(char[][] chars) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                sb.append(chars[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString().trim();
    }

    private static char[][] enhance(char[][] m) {
        List<char[][]> subSquares = new ArrayList<>();

        if (m.length % 2 == 0) {
            subSquares = breakSquareIntoSubSquares(m, 2);
        } else {
            subSquares = breakSquareIntoSubSquares(m, 3);
        }

        List<char[][]> subSquaresOutput = new ArrayList<>();
        for (char[][] sub : subSquares) {
            char[][] output = getOutput(sub);
            subSquaresOutput.add(output);
        }

        char[][] rebuilt = buildSquareFromSubSquares(subSquaresOutput);

        return rebuilt;
    }

    private static List<char[][]> breakSquareIntoSubSquares(char[][] matrix, int size) {
        List<char[][]> result = new ArrayList<>();

        boolean keepGoing = true;
        int startRow = 0;
        int startColumn = 0;

        while (keepGoing) {
            char[][] square = new char[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    square[i][j] = matrix[startRow][startColumn + j];
                }
                startRow++;
            }
            startColumn += size;

            if (startColumn == matrix.length && startRow == matrix.length) {
                keepGoing = false;
            } else if (startColumn == matrix.length) {
                startColumn = 0;
            } else {
                startRow -= size;
            }

            result.add(square);
        }

        return result;

    }

    private static char[][] buildSquareFromSubSquares(List<char[][]> subSquares) {
        if (subSquares.size() == 1) {
            return subSquares.get(0);
        }

        int size = subSquares.get(0).length * ((int) Math.sqrt(subSquares.size()));
        char[][] result = new char[size][size];

        int startFromRow = 0;
        int startFromColumn = 0;
        int subsProcessedInRow = 0;
        for (int subSquaresProcessed = 0; subSquaresProcessed < subSquares.size(); subSquaresProcessed++) {
            char[][] subSquare = subSquares.get(subSquaresProcessed);
            startFromColumn = (subsProcessedInRow * subSquare.length);

            if (startFromColumn == result.length) {
                subsProcessedInRow = 0;
                startFromColumn = 0;
                startFromRow = startFromRow + subSquare.length;
            }

            // System.out.println("Sub " + subSquaresProcessed + ":");
            for (int i = 0; i < subSquare.length; i++) {
                for (int j = 0; j < subSquare.length; j++) {
                    char c = subSquare[i][j];
                    int rowNum = i + startFromRow;
                    int colNum = j + startFromColumn;
                    // System.out.println("" + i + "," + j + " -> " + rowNum + "," + colNum);

                    result[rowNum][colNum] = c;
                }
            }
            subsProcessedInRow++;
        }

        return result;
    }

    public static void print(char[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                System.out.print(m[i][j]);
            }
            System.out.println();
        }
    }

    private static char[][] getOutput(char[][] input) {
        int rotations = 0;
        char[][] output = enhancementRules.get(toString(input));

        while (output == null) {
            rotate90(input);
            rotations++;
            output = enhancementRules.get(toString(input));
            if (rotations == 3 && output == null) {
                rotate90(input);
                flip(input);
                output = enhancementRules.get(toString(input));

                if (output == null) {
                    for (int i = 0; i < 3; i++) {
                        rotate90(input);
                        output = enhancementRules.get(toString(input));
                        if (output != null) {
                            break;
                        }
                    }

                    if (output == null) {
                        throw new IllegalArgumentException("Wrong Input or something is wrong with enhancement rules!");
                    }
                }

                rotations++;
            }
        }

        return output;
    }

    public static char[][] getMatrix(String s) {
        String[] inputElements = s.split("/");
        char[][] matrix = new char[inputElements.length][inputElements.length];

        for (int i = 0; i < inputElements.length; i++) {
            String row = inputElements[i];
            matrix[i] = row.toCharArray();
        }

        return matrix;
    }

    public static void rotate90(char[][] matrix) {
        getTranspose(matrix);
        rotateAlongMidRow(matrix);
    }

    public static void flip(char[][] matrix) {
        for (int i = 0; i < (matrix.length / 2); i++) {
            char[] temp = matrix[i];
            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }
    }

    private static void getTranspose(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                char temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    private static void rotateAlongMidRow(char[][] matrix) {
        int len = matrix.length;
        for (int i = 0; i < len / 2; i++) {
            for (int j = 0; j < len; j++) {
                char temp = matrix[i][j];
                matrix[i][j] = matrix[len - 1 - i][j];
                matrix[len - 1 - i][j] = temp;
            }
        }
    }
}
