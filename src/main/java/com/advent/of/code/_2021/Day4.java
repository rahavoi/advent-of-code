package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 {
    public static void main(String[] args) throws Exception{
        String n = "26,38,2,15,36,8,12,46,88,72,32,35,64,19,5,66,20,52,74,3,59,94,45,56,0,6,67,24,97,50,92,93,84,65,71,90,96,21,87,75,58,82,14,53,95,27,49,69,16,89,37,13,1,81,60,79,51,18,48,33,42,63,39,34,62,55,47,54,23,83,77,9,70,68,85,86,91,41,4,61,78,31,22,76,40,17,30,98,44,25,80,73,11,28,7,99,29,57,43,10";
        List<Integer> nums = new ArrayList<>();

        for(String s : n.split(",")){
            nums.add(Integer.parseInt(s));
        }

        List<String> lines = Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day4.txt"));

        List<int[][]> boards = new ArrayList<>();

        for(int i = 0; i < lines.size(); i++){
            if(lines.get(i).trim().isEmpty()){
                continue;
            }

            int[][] board = new int[5][5];

            //System.out.println("Creating a new board");
            for(int j = 0; j < 5; j++){
                String line = lines.get(i++).trim();
                //System.out.println(line);

                List<Integer> rowNums = Arrays.stream(line.split(" "))
                    .filter(s -> !s.isEmpty())
                    .map(s -> Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());

                for(int z = 0; z < 5; z ++){
                    board[j][z] = rowNums.get(z);
                }
            }
            boards.add(board);
        }

        int idx = -1;

        Set<int[][]> winningBoards = new HashSet<>();

        int[][] winningBoard = null;

        outer:
        for(int i = 0; i < nums.size(); i++){
            int num = nums.get(i);

            for(int[][] board : boards){
                if(checkNumber(num, board)){
                    idx = i;
                    winningBoards.add(board);
                    winningBoard = board;
                    //System.out.println("BINGO!!!!");
                    if(winningBoards.size() == boards.size()){
                        break outer;
                    }
                }
            }
        }


        int sumOfUnmarked = 0;

        for(int i = 0; i < winningBoard.length; i++){
            for(int j = 0; j < winningBoard.length; j++){
                if(winningBoard[i][j] > 0){
                    sumOfUnmarked+= winningBoard[i][j];
                }
            }
        }

        System.out.println(nums.get(idx) * sumOfUnmarked);

    }

    public static boolean checkNumber(int num, int[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(num == board[i][j]){
                    board[i][j] = board[i][j] * -1;
                }
            }
        }

        //check rows
        for(int i = 0; i < board.length; i ++){
            int[] row = board[i];

            if(Arrays.stream(row).filter(n -> n > 0).count() == 0){
                return true;
            }
        }

        //check columns:
        for(int i = 0; i < board.length; i ++){
            int[] column = getColumn(board, i);
            if(Arrays.stream(column).filter(n -> n > 0).count() == 0){
                return true;
            }
        }

        return false;
    }

    public static int[] getColumn(int[][] array, int index){
        int[] column = new int[array[0].length]; // Here I assume a rectangular 2D array!
        for(int i=0; i<column.length; i++){
            column[i] = array[i][index];
        }
        return column;
    }
}
