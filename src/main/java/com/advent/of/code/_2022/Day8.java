package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day8 {
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day8.txt"));

        int[][] forest = new int[input.size()][input.get(0).length()];
        int[][] marked = new int[forest.length][forest[0].length];

        for(int i = 0; i < input.size(); i++){
            String line = input.get(i);

            for(int j = 0; j < line.length(); j++){
                forest[i][j] = Integer.parseInt(Character.toString(line.charAt(j)));
            }
        }

        int totalVisible = 0;


        //from top:
        for(int x = 0; x < forest[0].length; x++){
            marked[0][x] = 1; //outer is visible always
            int max = forest[0][x];
            for(int y = 0; y < forest.length; y++){
                int cur = forest[y][x];
                if(cur > max){
                    marked[y][x] = 1;
                }
                max = Math.max(max, cur);
            }
        }

        //from bottom:
        for(int x = 0; x < forest[0].length; x++){
            marked[forest.length - 1][x] = 1; //outer is visible always
            int max = forest[forest.length - 1][x];

            for(int y = forest.length - 1; y >= 0; y--){
                int cur = forest[y][x];
                if(cur > max){
                    marked[y][x] = 1;
                }
                max = Math.max(max, cur);
            }
        }

        //From left:
        for(int y = 0; y < forest.length; y++){
            marked[y][0] = 1; //outer is visible always
            int max = forest[y][0];

            for(int x = 0; x < forest[0].length; x++){
                int cur = forest[y][x];
                if(cur > max){
                    marked[y][x] = 1;
                }
                max = Math.max(max, cur);
            }
        }

        //from right:
        for(int y = 0; y < forest.length; y++){
            marked[y][forest[0].length - 1] = 1; //outer is visible always
            int max = forest[y][forest[0].length - 1];

            for(int x = forest.length - 1; x >= 0; x--){
                int cur = forest[y][x];
                if(cur > max){
                    marked[y][x] = 1;

                }
                max = Math.max(cur, max);
            }
        }

        for(int[] row : marked){
            for(int i = 0; i < row.length; i++){
                totalVisible += row[i];
            }
        }

        System.out.println(totalVisible);

        long maxVis = 0;
        for(int y = 0; y < forest.length; y++){
            for(int x = 0; x < forest[0].length; x++){
                maxVis = Math.max(maxVis, getVisible(forest, x, y));
            }
        }

        System.out.println(maxVis);

    }

    private static long getVisible(int[][] map, int x, int y){
        int height = map[y][x];

        //Look up:
        int upScore = 0;
        int curY = y - 1;
        while(curY >= 0){
            upScore++;
            if(map[curY][x] >= height){
                break;
            }
            curY--;
        }

        //Look down:
        int downScore = 0;
        curY = y + 1;
        while(curY < map.length){
            downScore++;
            if(map[curY][x] >= height){
                break;
            }
            curY++;
        }

        //Look right:
        int rightScore = 0;
        int curX = x + 1;
        while(curX < map[0].length){
            rightScore++;
            if(map[y][curX] >= height){
                break;
            }
            curX++;
        }

        //Look left:
        int leftScore = 0;
        curX = x - 1;
        while(curX >= 0){
            leftScore++;
            if(map[y][curX] >= height){
                break;
            }
            curX--;
        }


        long score = upScore * leftScore * downScore * rightScore;
        return score;

    }
}
