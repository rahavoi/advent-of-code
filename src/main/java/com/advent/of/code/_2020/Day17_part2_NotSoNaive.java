package com.advent.of.code._2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day17_part2_NotSoNaive extends Day {
    public static void main(String[] args) throws IOException {
        //The universe is probably not needed at all..
        // We can work with active cubes only
        //But hey, it executes in under 2seconds. Off to sleep..
        Set<HyperCube> universe = new HashSet<>();
        Set<HyperCube> active = new HashSet<>();

        List<String> lines = lines("src/main/resources/2020/Day17.txt");


        for(int x = 0; x < lines.size(); x++){
            char[] chars = lines.get(x).toCharArray();

            for(int y = 0 ; y < chars.length; y++){
                HyperCube c = new HyperCube(x, y, 0, 0);

                universe.add(c);
                universe.addAll(c.getNeighbors());

                if(chars[y] == '#'){
                    active.add(c);
                }
            }
        }

        long now = System.currentTimeMillis();
        for(int i = 0; i < 6; i++){
            active = siimulate(universe, active);
            //System.out.println("Round: " + (1 + i));
            //System.out.println("Universe size: " + universe.size());
            //System.out.println("Total active cubes: " + active.size());
        }

        System.out.println("Total active cubes: " + active.size());
        System.out.println(System.currentTimeMillis() - now + "ms");


    }

    static Set<HyperCube> siimulate(Set<HyperCube> universe, Set<HyperCube> active) {
        Set<HyperCube> newActive = new HashSet<>();
        Set<HyperCube> newAll = new HashSet<>();

        universe.forEach(cube -> {
            List<HyperCube> neighbors = cube.getNeighbors();

            int activeNeighbors = neighbors.stream().filter(active::contains).collect(Collectors.toSet()).size();

            if(active.contains(cube) && (activeNeighbors == 2 || activeNeighbors == 3)){
                newActive.add(cube);
            }

            if(!active.contains(cube) && activeNeighbors == 3){
                newActive.add(cube);
            }

            newAll.addAll(neighbors);
            newAll.add(cube);
        });

        universe.addAll(newAll);
        //System.out.println("New size: " + all.size());

        return newActive;
    }

    static class Cube {
        int x;
        int y;
        int z;

        public Cube(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o){
            if(this == o){
                return true;
            }

            Cube other = (Cube) o;

            return this.x == other.x && this.y == other.y && this.z == other.z;
        }

        @Override
        public int hashCode(){
            int[] arr = {x,y,z};
            return Arrays.hashCode(arr);
        }
    }


    static class HyperCube extends Cube{
        int w;

        public HyperCube(int x, int y, int z, int w)
        {
            super(x,y,z);
            this.w = w;
        }

        @Override
        public boolean equals(Object o){
            if(this == o){
                return true;
            }

            HyperCube other = (HyperCube) o;

            return super.equals(other) && this.w == other.w;
        }

        @Override
        public int hashCode(){
            int[] arr = {x,y,z,w};
            return Arrays.hashCode(arr);
        }

        @Override
        public String toString()
        {
            return "HyperCube{" + "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + '}';
        }

        public List<HyperCube> getNeighbors(){
            List<HyperCube> result = new ArrayList<>();
            IntStream.rangeClosed(-1, 1).forEach(x ->
                IntStream.rangeClosed(-1, 1).forEach(y ->
                    IntStream.rangeClosed(-1,1).forEach(z ->
                        IntStream.rangeClosed(-1,1).forEach(w ->{
                            HyperCube neighbor = new HyperCube(x + this.x, y + this.y, z + this.z, w + this.w);
                            if(!this.equals(neighbor)){
                                result.add(neighbor);
                            }
                }))));

            return result;

        }
    }
}