package com.advent.of.code._2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2
{
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/2020/Day2.txt"));

        System.out.println(solve(input, true));;
        System.out.println(solve(input, false));;
    }

    private static int solve(List<String> input, boolean partOne) {
        int validCount = 0;
        for (String line : input) {
            String[] parts = line.split(": ");
            String policy = parts[0];
            String password = parts[1];
            String policyParts[] = policy.split(" ");
            String[] minMax = policyParts[0].split("-");

            int min = Integer.parseInt(minMax[0]);
            int max = Integer.parseInt(minMax[1]);
            char letter = policyParts[1].charAt(0);

            boolean valid = partOne ? isValid1(password, letter, min, max) :
                isValid2(password, letter, min, max);

            if(valid){
                validCount++;
            }
        }

        return validCount;
    }

    private static boolean isValid1(String password, char letter, int min, int max) {
        int count = 0;

        for(char c : password.toCharArray()) {
            if(letter == c){
                count++;
            }
        }

        return count >= min && count <= max;
    }

    private static boolean isValid2(String password, char letter, int min, int max) {
        boolean firstMatch = min <= password.length() && password.charAt(min - 1) == letter;
        boolean secondMatch = max <= password.length() && password.charAt(max - 1) == letter;

        return firstMatch ^ secondMatch;
    }
}