package com.advent.of.code._2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {
    public static void main(String[] args) throws Exception {
        char[][] data = parseData();
        List<Fold> foldInstructions = parseFoldInstructions();

        for(Fold f : foldInstructions){
            data = fold(data, f);

        }

        print(data);
    }

    private static List<Fold> parseFoldInstructions() throws IOException {
        return Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day13_fold.txt")).stream()
            .map(l -> {
                String[] parts = l.split(" ")[2].split("=");
                Fold fold = new Fold();
                fold.vertically = parts[0].equals("y");
                fold.line = Integer.parseInt(parts[1]);
                return fold;
            }).collect(Collectors.toList());
    }

    private static char[][] parseData() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(
            "/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day13.txt"));

        List<Pair> points = lines.stream().map(l -> {
            String[] parts = l.split(",");

            return new Pair(Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
        }).collect(Collectors.toList());

        char[][] data = new char[points.stream().mapToInt(p -> p.i).max().getAsInt() + 1][points.stream().mapToInt(p -> p.j).max().getAsInt() + 1];

        for(Pair p : points){
            data[p.i][p.j] = '#';
        }

        for(int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if(data[i][j] != '#'){
                    data[i][j] = ' ';
                }
            }
        }
        return data;
    }

    private static char[][] fold(char[][] data, Fold instruction){
        return instruction.vertically ? foldUp(data, instruction) : foldLeft(data, instruction);
    }

    private static char[][] foldLeft(char[][] data, Fold instruction) {
        int leftHalfSize = instruction.line;
        int rightHalfSize = data.length - instruction.line;

        if(rightHalfSize > leftHalfSize){
            instruction.line = data.length - 1 - instruction.line;
            flipHorizontally(data);

        }

        char[][] foldedData = new char[data.length][instruction.line];

        //copy left part
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < instruction.line; j++){

                foldedData[i][j] = data[i][j];
            }
        }

        //fold right part
        for(int i = 0; i < data.length; i++){
            for(int j = data[0].length - 1; j > instruction.line; j--){
                if(data[i][j] == '#'){
                    int offset = j - foldedData[0].length;
                    int pos = foldedData[0].length - offset;

                    foldedData[i][pos] = data[i][j];
                }
            }
        }

        return foldedData;
    }

    private static char[][] foldUp(char[][] data, Fold instruction) {
        int upperHalf = instruction.line;
        int lowerHalf = data[0].length - instruction.line;

        if(lowerHalf > upperHalf){
            instruction.line = data.length - 1 - instruction.line;
            flipVertically(data);
        }

        char[][] foldedData = new char[instruction.line][data[0].length];
        //copy upper part
        for(int i = 0; i < instruction.line; i++){
            for(int j = 0; j < data[0].length; j++){
                foldedData[i][j] = data[i][j];
            }
        }

        //fold lower part
        for(int i = data.length - 1; i > instruction.line; i--){
            for(int j = 0; j < data[0].length; j++){
                if(data[i][j] == '#'){
                    int offset = i - foldedData.length; //9 - 8 = 1
                    int pos = foldedData.length - offset;
                    foldedData[pos][j] = data[i][j];
                }
            }
        }

        return foldedData;
    }

    private static void flipVertically(char[][] data) {
        for(int i = 0; i < data.length / 2; i++){
            for(int j = 0; j < data[0].length; j++){
                char tmp = data[i][j];
                data[i][j] = data[data.length - 1 - i][j];
                data[data.length - 1 - i][j] = tmp;
            }
        }
    }

    private static void flipHorizontally(char[][] data) {
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[0].length / 2; j++){
                char tmp = data[i][j];
                data[i][j] = data[i][data[0].length - 1  - j];
                data[i][data[0].length - 1  - j] = tmp;
            }
        }
    }

    static class Fold{
        int line;
        boolean vertically;
    }

    static class Pair{
        int i;
        int j;

        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private static void print(char[][] data){
        for(int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.print(data[i][j]);
            }
            System.out.println();
        }
    }
}