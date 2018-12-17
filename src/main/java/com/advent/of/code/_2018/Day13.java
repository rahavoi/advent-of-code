package com.advent.of.code._2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Day13 {
    char[][] roadMap;
    final char DIRECTION_NORTH = '^';
    final char DIRECTION_SOUTH = 'v';
    final char DIRECTION_EAST = '>';
    final char DIRECTION_WEST = '<';
    char INTERSECT = '+';
    char CURVE_SLASH = '/';
    char CURVE_BACKSLASH = '\\';
    int lapCounter = 0;

    List<Car> cars = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        new Day13().task1();
    }

    private void task1() throws Exception{
        initRoadMap();
        go();
    }

    private void go(){
        while(cars.size() > 1){
            Collections.sort(cars, (car1, car2) -> {
                int result;

                result = car1.getY().compareTo(car2.getY());

                if(result == 0){
                    result = car1.getX().compareTo(car2.getX());
                }

                return result;
            });

            Iterator<Car> it = cars.iterator();

            long remaining = cars.stream().filter(cart -> !cart.isCrashed()).count();
            boolean oneCartLeft = remaining == 1;
            while (it.hasNext()){
                Car car = it.next();
                if(car.isCrashed()){
                    it.remove();
                } else {

                    if(!oneCartLeft){
                        car.move();
                    }

                    long carsInSameSpot = cars.stream().filter(c -> c.getY().equals(car.getY()) && car.getX().equals(c.getX())).count();

                    if(carsInSameSpot > 1){
                        cars.stream().filter(c -> c.getY().equals(car.getY()) && car.getX().equals(c.getX())).forEach(c -> c.setCrashed(true));
                        System.out.println("Crashed: " + car.getX() + "," + car.getY() + " Lap: " + (lapCounter + 1));
                    }
                }
            }

            ++lapCounter;
        }

        System.out.println("Survivor: " + cars.stream().findAny().get().getX() + "," + cars.stream().findAny().get().getY() );
    }

    private void initRoadMap() throws IOException {
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/2018/Day13.txt"));

        int maxX = input.stream().max(Comparator.comparing(String::length)).get().length();
        roadMap = new char[maxX][input.size()];

        for(int y = 0; y < input.size(); y++){
            String line =  input.get(y);

            for(int x = 0; x < line.length(); x++){

                roadMap[x][y] = line.charAt(x);

                if(line.charAt(x) == DIRECTION_EAST ||
                        line.charAt(x) == DIRECTION_WEST ||
                        line.charAt(x) == DIRECTION_NORTH ||
                        line.charAt(x) == DIRECTION_SOUTH) {
                    cars.add(new Car(line.charAt(x), x,y, 0));
                }
            }
        }

        System.out.println("Initialized map. Found cars: " + cars.size());
    }

    private class Car{
        private char direction;
        private int x;
        private int y;
        private int intersectionCount;
        boolean crashed;

        public Car(char direction, int x, int y, int intersectionCount) {
            this.direction = direction;
            this.x = x;
            this.y = y;
            this.intersectionCount = intersectionCount;
        }

        public char getDirection() {
            return direction;
        }

        public void setDirection(char direction) {
            this.direction = direction;
        }

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }

        public int getIntersectionCount() {
            return intersectionCount;
        }

        public void setIntersectionCount(int intersectionCount) {
            this.intersectionCount = intersectionCount;
        }

        public boolean isCrashed() {
            return crashed;
        }

        public void setCrashed(boolean crashed) {
            this.crashed = crashed;
        }

        public void move(){
            char roadElement = roadMap[x][y];

            if(roadElement == INTERSECT){
                if(intersectionCount == 0){
                    //left
                    switch (direction){
                        case DIRECTION_SOUTH:
                            direction = DIRECTION_EAST;
                            break;
                        case DIRECTION_NORTH:
                            direction = DIRECTION_WEST;
                            break;
                        case DIRECTION_EAST:
                            direction = DIRECTION_NORTH;
                            break;
                        case DIRECTION_WEST:
                            direction = DIRECTION_SOUTH;
                            break;
                    }
                    setIntersectionCount(1);
                } else if(intersectionCount == 1){
                    //straight;
                    setIntersectionCount(2);

                } else {
                    //right
                    switch (direction) {
                        case DIRECTION_SOUTH:
                            direction = DIRECTION_WEST;
                            break;
                        case DIRECTION_NORTH:
                            direction = DIRECTION_EAST;
                            break;
                        case DIRECTION_EAST:
                            direction = DIRECTION_SOUTH;
                            break;
                        case DIRECTION_WEST:
                            direction = DIRECTION_NORTH;
                            break;
                    }
                    setIntersectionCount(0);
                }

            } else if(roadElement == CURVE_SLASH) {
                switch (direction) {
                    case DIRECTION_SOUTH:
                        direction = DIRECTION_WEST;
                        break;
                    case DIRECTION_NORTH:
                        direction = DIRECTION_EAST;
                        break;
                    case DIRECTION_EAST:
                        direction = DIRECTION_NORTH;
                        break;
                    case DIRECTION_WEST:
                        direction = DIRECTION_SOUTH;
                        break;
                }
            } else if (roadElement == CURVE_BACKSLASH) {
                switch (direction) {
                    case DIRECTION_SOUTH:
                        direction = DIRECTION_EAST;
                        break;
                    case DIRECTION_NORTH:
                        direction = DIRECTION_WEST;
                        break;
                    case DIRECTION_EAST:
                        direction = DIRECTION_SOUTH;
                        break;
                    case DIRECTION_WEST:
                        direction = DIRECTION_NORTH;
                        break;
                }
            }

            switch (direction){
                case DIRECTION_SOUTH:
                    y++;
                    break;
                case DIRECTION_NORTH:
                    y--;
                    break;
                case DIRECTION_EAST:
                    x++;
                    break;
                case DIRECTION_WEST:
                    x--;
                    break;
            }

            setDirection(direction);
            setX(x);
            setY(y);
        }
    }
}
