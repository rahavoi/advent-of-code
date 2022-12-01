package com.advent.of.code._2020;

import java.util.List;

public class Day12 extends Day
{
    static int eastWestPos = 0;
    static int southNorthPos = 0;

    static int eastWayPoint = 10;
    static int northWayPoint = 1;

    public static void main(String[] args) throws Exception {
        List<String> lines = lines("src/main/resources/2020/Day12.txt");

        for(String line : lines){
            char command = line.charAt(0);
            int length = Integer.parseInt(line.substring(1));

            switch (command){
                case 'L':
                case 'R':
                    rotate(command, length);
                    break;
                case 'F':
                    moveForward(length);
                    break;
                case 'N':
                    northWayPoint += length;
                    break;
                case 'S':
                    northWayPoint -= length;
                    break;
                case 'E':
                    eastWayPoint += length;
                    break;
                case 'W':
                    eastWayPoint -= length;
                    break;
            }
        }

        System.out.println(Math.abs(southNorthPos) + Math.abs(eastWestPos));

    }

    private static void moveForward(int length){
        eastWestPos -= eastWayPoint * length;
        southNorthPos -= northWayPoint * length;
    }

    private static void rotate(char rightOrLeft, int value){
        int times = value / 90;

        while(times > 0){
            rotate(rightOrLeft);
            times--;
        }
    }

    private static void rotate(char rightOrLeft){
        if(rightOrLeft == 'L'){
            turnLeft();
        } else {
            turnRight();
        }
    }

    private static void turnLeft(){
        int tmp = eastWayPoint;
        eastWayPoint = northWayPoint * -1;
        northWayPoint = tmp;
    }

    private static void turnRight(){
        int tmp = eastWayPoint;
        eastWayPoint = northWayPoint;
        northWayPoint = tmp * -1;
    }
}
