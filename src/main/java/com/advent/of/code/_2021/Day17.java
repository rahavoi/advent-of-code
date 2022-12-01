package com.advent.of.code._2021;

import java.util.HashSet;
import java.util.Set;

public class Day17 {
//    static int minX = 20;
//    static int maxX = 30;
//
//    static int minY = -10;
//    static int maxY = -5;

    static int minX = 185;
    static int maxX = 221;

    static int minY = -122;
    static int maxY = -74;

    public static void main(String[] args){
        System.out.println("Area: " + (maxX - minX) + " by " + (Math.abs(maxY - minY)));


        //int acceptableXVelocity = findAcceptableXVelocity();
        //int acceptableYVelocity = findAcceptableYVelocity(acceptableXVelocity);

        //System.out.println("Acceptable x: " + acceptableXVelocity);

        //System.out.println("Acceptable y: " + acceptableYVelocity);

        //For part 1 i just found an acceptable x and binary-searched the optimal y velocity by hand. This is how lazy I am :)

        //Part 2: find all possible velocity combinations.

        //attempt(optimalX, optimalY);

        Set<String> allAcceptableCombos = new HashSet<>();
        Set<Integer> allAcceptableX = findAllAcceptableX();

        for(int acceptableXVelocity : allAcceptableX){
            //Again.. super lazy.. hardcoded min and max values.. need to figure out a proper way
            for(int y = -500; y < 500; y++){
                if(attempt(acceptableXVelocity, y) == 0){
                    allAcceptableCombos.add(acceptableXVelocity + "," + y);
                }
            }
        }

        allAcceptableCombos.forEach(System.out::println);

        //allAcceptableX.forEach(System.out::println);
        System.out.println(allAcceptableCombos.size());


    }


    private static int findAcceptableYVelocity(int xVelocity){
        int high = 10000;
        int low = 0;

        while (low <= high){
            int mid = low + ((high - low) / 2);

            int result = attempt(xVelocity, mid);

            if(result == 0){
                return mid;
            }

            if(result < 0){
                //try harder
                low = mid + 1;
            } else {
                //not so hard
                high = mid - 1;
            }

        }

        return -1;
    }

    private static Set<Integer> findAllAcceptableX(){
        Set<Integer> result = new HashSet<>();

        for(int x = 0; x < maxX + 1; x++){
            int cur = 0;
            int curVelocity = x;
            while(x > 0 && curVelocity > 0){
                cur += curVelocity--;

                if(x > maxX){
                    //Over shot
                    break;
                }

                if(cur >= minX && cur <= maxX) {
                    //Found acceptable x
                    result.add(x);
                }
            }
        }

        return result;
    }

    private static Set<Integer> findAllOptimalXVelocities(){
        Set<Integer> result = new HashSet<>();
        int first = findAcceptableXVelocity();


        //find all smaller
        int cur = first;
        while(isXVelocityAcceptable(cur)){
            result.add(cur--);
        }

        //find all bigger
        cur = first;
        while(isXVelocityAcceptable(cur)){
            result.add(cur++);
        }
        
        return result;
    }

    private static boolean isXVelocityAcceptable(int x){
        int pos = 0;
        int xVelocity = x;
        while(xVelocity > 0){
            pos += xVelocity--;
        }

        return pos >= minX && pos <= maxX;
    }

    private static int findAcceptableXVelocity(){
        //find such x velocity that drag will result in velocity = 0 over the needed area
        //bin search it?

        int start = 0;
        int end = maxX;
        while(start <= end){
            int mid = start + ((end - start) / 2);

            //calculate where it will be when drag makes it 0;
            int pos = 0;
            int xVelocity = mid;
            while(xVelocity > 0){
                pos += xVelocity--;
            }

            if(pos > maxX){
                end = mid;
            } else if(pos < minX){
                start = mid;
            } else {
                //Found acceptable velocity x
                return mid;
            }

        }

        return -1;

    }

    private static int attempt(int xVelocity, int yVelocity){
        int x = 0;
        int y = 0;

        //System.out.println("Throwing with initial velocity of " + xVelocity + "," + yVelocity);

        while(xVelocity > 0){
            if(xVelocity == 0){
                //System.out.println("Reached max y: " + y + " At x=" + x);
                break;
            }

            x = x >= 0 ? x + xVelocity : x - xVelocity;
            y += yVelocity;

            if(x >= minX && x <= maxX && y >= minY && y <= maxY){
                return 0;
            }

            xVelocity--;
            yVelocity--;
        }

        while(yVelocity >= 0){
            y += yVelocity;
            yVelocity--;

            if(x >= minX && x <= maxX && y >= minY && y <= maxY) {
                return 0;
            }
        }

        //System.out.println("Reached max y: " + y + " At x=" + x);

        //drag has reached maximum. dropping now
        yVelocity = 0;

        while(y > maxY){
            y -= yVelocity;
            yVelocity++;

            if(x >= minX && x <= maxX && y >= minY && y <= maxY) {
                return 0;
            }
        }

        if(y >= minY && y <= maxY && x >= minX && x <= maxX){
            //System.out.println("Probe successfully made it to the target area!");
            return 0;
        } else if(y < minY){
            //System.out.println("Probe did not make it to the target area!:)");
            return 1;
        } else {
           //System.out.println("Probe overshot the target area!:)");
            return -1;
        }
    }
}
