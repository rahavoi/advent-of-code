package com.advent.of.code._2019;

public class Day12 {

    public static final int moon0X = -6;
    public static final int moon1X = 0;
    public static final int moon2X = -15;
    public static final int moon3X = -3;
    public static final int moon0Y = -5;
    public static final int moon1Y = -3;
    public static final int moon2Y = 10;
    public static final int moon3Y = -8;
    public static final int moon0Z = -8;
    public static final int moon1Z = -13;
    public static final int moon2Z = -11;
    public static final int moon3Z = 3;

    public static void main(String[] args){
        Day12 task = new Day12();

        Point[] moons = task.initMoons();

        int count = 0;
        for(int i = 0; i < 1000000000; i++){
            //Applying gravity:
            for(Point moon : moons){
                applyGravity(moon, moons);
            }

            //Applying velocity:
            for(Point moon : moons){
                applyVelocity(moon);
            }


            //System.out.println("After " + count + " iterations:");
            System.out.println(moons[0]);

            if(moons[0].x == moon0X && moons[0].y == moon0Y && moons[0].z == moon0Z &&
                    moons[1].x == moon1X && moons[1].y == moon1Y && moons[1].z == moon1Z &&
                    moons[2].x == moon2X && moons[2].y == moon2Y && moons[2].z == moon2Z &&
                    moons[2].x == moon2X && moons[2].y == moon2Y && moons[2].z == moon2Z){
                System.out.println("Returned back to initial positions after: " + (i + 1) + " steps");
            }
        }

        int total = 0;

        for(Point p : moons){
            total += p.getAbsoluteEnergy();
        }

        System.out.println("Total Energy after 1000 steps: " + total);

    }

    private static void applyGravity(Point moon, Point[] moons) {
        for(Point other : moons){
            if(moon != other){
                if(moon.x > other.x){
                    moon.velX--;
                } else if(moon.x < other.x){
                    moon.velX++;
                }

                if(moon.y > other.y){
                    moon.velY--;
                } else if(moon.y < other.y){
                    moon.velY++;
                }

                if(moon.z > other.z){
                    moon.velZ--;
                } else if(moon.z < other.z){
                    moon.velZ++;
                }
            }
        }
    }

    private static void applyVelocity(Point moon) {
        moon.x += moon.velX;
        moon.y += moon.velY;
        moon.z += moon.velZ;
    }

    public Point[] initMoons(){
        Point[] moons = new Point[] {
                new Point(moon0X, moon0Y, moon0Z,0,0,0),
                new Point(moon1X, moon1Y, moon1Z,0,0,0),
                new Point(moon2X, moon2Y, moon2Z,0,0,0),
                new Point(moon3X, moon3Y, moon3Z,0,0,0)
        };

        return moons;
    }


    class Point{
        int x;
        int y;
        int z;

        int velX;
        int velY;
        int velZ;

        public Point(int x, int y, int z, int velX, int velY, int velZ) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.velX = velX;
            this.velY = velY;
            this.velZ = velZ;
        }

        @Override
        public String toString(){
            return "pos=<x=" + x + ", y=" + y + ", z=" + z + ">, vel=<x=" + velX + ", y=" + velY + ", z=" + velZ + ">";
        }

        int getPotentialEnergy(){
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }

        int getKineticEnergy(){
            return Math.abs(velX) + Math.abs(velY) + Math.abs(velZ);
        }

        int getAbsoluteEnergy(){
            return getKineticEnergy() * getPotentialEnergy();
        }

    }
}
