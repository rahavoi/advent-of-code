package com.advent.of.code._2016;

import java.util.HashMap;
import java.util.Map;

public class Day1 {
    public static void main(String[] args){
        Map<String, Integer> locationsCount = new HashMap<>();
        String directions = "L5, R1, L5, L1, R5, R1, R1, L4, L1, L3, R2, R4, L4, L1, L1, R2, R4, R3, L1, R4, L4, L5, L4, R4, L5, R1, R5, L2, R1, R3, L2, L4, L4, R1, L192, R5, R1, R4, L5, L4, R5, L1, L1, R48, R5, R5, L2, R4, R4, R1, R3, L1, L4, L5, R1, L4, L2, L5, R5, L2, R74, R4, L1, R188, R5, L4, L2, R5, R2, L4, R4, R3, R3, R2, R1, L3, L2, L5, L5, L2, L1, R1, R5, R4, L3, R5, L1, L3, R4, L1, L3, L2, R1, R3, R2, R5, L3, L1, L1, R5, L4, L5, R5, R2, L5, R2, L1, L5, L3, L5, L5, L1, R1, L4, L3, L1, R2, R5, L1, L3, R4, R5, L4, L1, R5, L1, R5, R5, R5, R2, R1, R2, L5, L5, L5, R4, L5, L4, L4, R5, L2, R1, R5, L1, L5, R4, L3, R4, L2, R3, R3, R3, L2, L2, L2, L1, L4, R3, L4, L2, R2, R5, L1, R2";

        Direction direction = Direction.NORTH;
        int x = 0;
        int y = 0;

        for(String d : directions.split(", ")){
            char leftOrRight = d.charAt(0);
            int blocks = Integer.parseInt(d.substring(1));

            switch (direction) {
                case NORTH:
                    direction = (leftOrRight == 'L') ? Direction.EAST : Direction.WEST;
                    break;
                case EAST:
                    direction = (leftOrRight == 'L') ? Direction.SOUTH : Direction.NORTH;
                    break;
                case WEST:
                    direction = (leftOrRight == 'L') ? Direction.NORTH : Direction.SOUTH;
                    break;
                case SOUTH:
                    direction = (leftOrRight == 'L') ? Direction.WEST : Direction.EAST;
                    break;
                default:
                    throw new IllegalArgumentException("Wrong Direction!");
            }

            switch (direction) {
                case NORTH:

                    for(int i = 0; i < blocks; i++){
                        y++;
                        if (updateLocationsCount(locationsCount, x, y)) return;
                    }
                    break;
                case EAST:
                    for(int i = 0; i < blocks; i++){
                        x--;
                        if (updateLocationsCount(locationsCount, x, y)) return;
                    }
                    break;
                case WEST:
                    for(int i = 0; i < blocks; i++){
                        x++;
                        if (updateLocationsCount(locationsCount, x, y)) return;
                    }
                    break;
                case SOUTH:
                    for(int i = 0; i < blocks; i++){
                        y--;
                        if (updateLocationsCount(locationsCount, x, y)) return;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Wrong Direction!");
            }
        }

        System.out.println("X: " + x + " Y: " + y);


    }

    private static boolean updateLocationsCount(Map<String, Integer> locationsCount, int x, int y) {
        String key = x + "," + y;
        Integer value = locationsCount.getOrDefault(key, 0) + 1;

        if(value == 2){
            System.out.println("Twice: " + key);
            return true;
        }
        locationsCount.put(key, value);
        return false;
    }

    enum Direction{
        NORTH,
        SOUTH,
        EAST,
        WEST
    }
}
