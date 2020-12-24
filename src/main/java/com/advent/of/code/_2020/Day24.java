package com.advent.of.code._2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Day24 extends Day
{
    public static void main(String[] args){
        Map<Tile, Integer> tiles = new HashMap<>();

        Tile reference = new Tile(0,0,0);
        lines("src/main/resources/2020/Day24.txt")
            .stream().map(Day24::parseDirections)
            .map(dirs -> getTile(reference, dirs))
            .forEach(tile -> tiles.put(tile, tiles.getOrDefault(tile, 0) + 1));

        System.out.println("Part 1: " + tiles.values().stream().mapToInt(i -> i % 2).sum());


        Map<Tile, Integer> mutatingTiles = tiles;
        for(int i = 0; i < 100; i++){
            mutatingTiles = mutate(mutatingTiles);
            System.out.println("Day " + (i + 1) + ": " + mutatingTiles.values().stream().mapToInt(n -> n % 2).sum());
        }
    }

    private static Map<Tile, Integer> mutate(Map<Tile, Integer> tiles)
    {
        //For each tile add white adjacent tiles
        Set<Tile> t = new HashSet<>(tiles.keySet());

        t.forEach(tile -> {
                tiles.putIfAbsent(getTile(tile, List.of("e")), 0);
                tiles.putIfAbsent(getTile(tile, List.of("se")), 0);
                tiles.putIfAbsent(getTile(tile, List.of("sw")), 0);
                tiles.putIfAbsent(getTile(tile, List.of("w")), 0);
                tiles.putIfAbsent(getTile(tile, List.of("nw")), 0);
                tiles.putIfAbsent(getTile(tile, List.of("ne")), 0);
            });


        Map<Tile, Integer> updated = new HashMap<>();

        tiles.keySet().forEach(tile -> {
            Integer countBlack = 0;

            countBlack += tiles.getOrDefault(getTile(tile, List.of("e")), 0) % 2;
            countBlack += tiles.getOrDefault(getTile(tile, List.of("se")), 0) % 2;
            countBlack += tiles.getOrDefault(getTile(tile, List.of("sw")), 0) % 2;
            countBlack += tiles.getOrDefault(getTile(tile, List.of("w")), 0) % 2;
            countBlack += tiles.getOrDefault(getTile(tile, List.of("nw")), 0) % 2;
            countBlack += tiles.getOrDefault(getTile(tile, List.of("ne")), 0) % 2;

            if(tiles.get(tile) % 2 != 0 ){
                //Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
                updated.put(tile, countBlack == 0 || countBlack > 2 ? 0 : 1);
            } else{
                //Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
                updated.put(tile, countBlack == 2 ? 1 : 0);
            }
        });
        return updated;
    }

    private static Tile getTile(Tile reference, List<String> directions){
        Tile tile = new Tile(reference.x, reference.y, reference.z);

        for(String direction : directions){
            switch (direction){
                case "e":
                    tile.x--;
                    tile.y++;
                    break;
                case "w":
                    tile.x++;
                    tile.y--;
                    break;
                case "ne":
                    tile.z--;
                    tile.y++;
                    break;
                case "sw":
                    tile.z++;
                    tile.y--;
                    break;
                case "nw":
                    tile.x++;
                    tile.z--;
                    break;
                case "se":
                    tile.x--;
                    tile.z++;
                    break;

                default:
                    throw new IllegalArgumentException("Invalid direction:" + direction );
            }
        }

        return tile;

    }

    private static List<String> parseDirections(String l)
    {
        char[] chars = l.toCharArray();
        List<String> directions = new ArrayList<>();

        for(int i = 0; i < chars.length; i++){
            char ch = chars[i];

            if(ch =='n' || ch == 's'){
                directions.add(new StringBuilder().append(ch).append(chars[i + 1]).toString());
                i++;
            } else {
                directions.add(new StringBuilder().append(chars[i]).toString());
            }
        }
        return directions;
    }

    static class Tile {
        int x;
        int y;
        int z;

        public Tile(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX()
        {
            return x;
        }

        public void setX(int x)
        {
            this.x = x;
        }

        public int getY()
        {
            return y;
        }

        public void setY(int y)
        {
            this.y = y;
        }

        public int getZ()
        {
            return z;
        }

        public void setZ(int z)
        {
            this.z = z;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }
            Tile tile = (Tile) o;
            return x == tile.x && y == tile.y && z == tile.z;
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(x, y, z);
        }
    }
}
