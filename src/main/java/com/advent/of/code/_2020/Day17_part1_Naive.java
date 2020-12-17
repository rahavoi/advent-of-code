package com.advent.of.code._2020;

import java.util.ArrayList;
import java.util.List;

public class Day17_part1_Naive extends Day
{
    public static void main(String[] args) throws Exception
    {
        List<List<Character>> initDimension = new ArrayList<>();


        lines("src/main/resources/2020/Day17.txt").forEach(line -> {
            char[] chars = line.toCharArray();
            List<Character> characters = new ArrayList<>();

            for (char c : chars)
            {
                characters.add(c);
            }

            initDimension.add(characters);
        });

        //System.out.println("Before any cycles: ");
        //printLayer(initDimension);

        List<List<Character>> topDimension = new ArrayList<>();
        List<List<Character>> bottomDimension = new ArrayList<>();
        List<List<List<Character>>> cube = new ArrayList<>();
        cube.add(topDimension);
        cube.add(initDimension);
        cube.add(bottomDimension);


        for (int i = 0; i < initDimension.size(); i++)
        {
            List<Character> topRow = new ArrayList<>();
            List<Character> bottomRow = new ArrayList<>();
            for (int j = 0; j < initDimension.get(0)
                .size(); j++)
            {
                topRow.add('.');
                bottomRow.add('.');
            }

            topDimension.add(topRow);
            bottomDimension.add(bottomRow);
        }

        for (int i = 0; i < 6; i++)
        {
            cube = grow(cube);
            cube = grow(cube);
            cube = grow(cube);
            cube = grow(cube);
            cube = grow(cube);

            cube = playRound(cube);
            //printCube(cube);
        }

        long sum = 0;
        for (int x = 0; x < cube.size(); x++)
        {
            for (int y = 0; y < cube.get(0)
                .size(); y++)
            {
                for (int z = 0; z < cube.get(0)
                    .get(0)
                    .size(); z++)
                {
                    if (isInBounds(x, y, z, cube) && cube.get(x)
                        .get(y)
                        .get(z)
                        .equals('#'))
                    {
                        sum++;
                    }
                }
            }
        }

        System.out.println(sum);
    }

    private static List<List<List<Character>>> grow(List<List<List<Character>>> cube)
    {
        List<List<List<Character>>> grown = new ArrayList<>();
        int newSize = cube.size() + 2;

        List<List<Character>> topLayer = new ArrayList<>();
        List<List<Character>> bottomLayer = new ArrayList<>();

        for (int x = 0; x < newSize; x++)
        {
            List<Character> topRow = new ArrayList<>();
            List<Character> bottomRow = new ArrayList<>();

            for (int y = 0; y < newSize; y++)
            {
                topRow.add('.');
                bottomRow.add('.');
            }

            topLayer.add(topRow);
            bottomLayer.add(bottomRow);
        }

        grown.add(topLayer);


        for (List<List<Character>> layer : cube)
        {
            List<List<Character>> grownDimension = new ArrayList<>();

            List<Character> upperRow = new ArrayList<>();
            List<Character> lowerRow = new ArrayList<>();

            for (int i = 0; i < newSize; i++)
            {
                upperRow.add('.');
                lowerRow.add('.');
            }

            grownDimension.add(upperRow);
            for (List<Character> row : layer)
            {
                List<Character> grownRow = new ArrayList<>();
                grownRow.add('.');

                for (char c : row)
                {
                    grownRow.add(c);
                }

                grownRow.add('.');
                grownDimension.add(grownRow);
            }
            grownDimension.add(lowerRow);
            grown.add(grownDimension);
        }

        grown.add(bottomLayer);

        return grown;
    }

    public static List<List<List<Character>>> playRound(List<List<List<Character>>> cube)
    {
        List<List<List<Character>>> copy = new ArrayList<>();

        for (int x = 0; x < cube.size(); x++)
        {
            List<List<Character>> dimension = new ArrayList<>();
            copy.add(dimension);
            for (int y = 0; y < cube.size(); y++)
            {
                List<Character> row = new ArrayList<>();
                dimension.add(row);
                for (int z = 0; z < cube.size(); z++)
                {
                    char current = cube.get(x)
                        .get(y)
                        .get(z);
                    int active = getActive(x, y, z, cube);

                    char newState;

                    if (current == '#')
                    {
                        if (active == 2 || active == 3)
                        {
                            newState = '#';
                        }
                        else
                        {
                            newState = '.';
                        }
                    }
                    else
                    {
                        if (active == 3)
                        {
                            newState = '#';
                        }
                        else
                        {
                            newState = '.';
                        }
                    }

                    row.add(newState);
                }
            }
        }

        return copy;
    }

    private static void printCube(List<List<List<Character>>> cube)
    {
        int count = 1;
        for (List<List<Character>> layer : cube)
        {
            System.out.println(count + " layer: ");
            printLayer(layer);
            System.out.println();
            count++;
        }
    }

    private static void printLayer(List<List<Character>> layer)
    {
        for (List<Character> row : layer)
        {
            for (char c : row)
            {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static int getActive(int x, int y, int z, List<List<List<Character>>> cube)
    {
        int active = 0;

        //Upper layer
        active += isActive(x + 1, y - 1, z - 1, cube);
        active += isActive(x + 1, y - 1, z, cube);
        active += isActive(x + 1, y - 1, z + 1, cube);

        active += isActive(x + 1, y, z - 1, cube);
        active += isActive(x + 1, y, z, cube);
        active += isActive(x + 1, y, z + 1, cube);

        active += isActive(x + 1, y + 1, z - 1, cube);
        active += isActive(x + 1, y + 1, z, cube);
        active += isActive(x + 1, y + 1, z + 1, cube);


        //Same layer:
        active += isActive(x, y - 1, z - 1, cube);
        active += isActive(x, y - 1, z, cube);
        active += isActive(x, y - 1, z + 1, cube);

        active += isActive(x, y, z - 1, cube);
        //active += isActive(x, y, z, cube);
        active += isActive(x, y, z + 1, cube);

        active += isActive(x, y + 1, z - 1, cube);
        active += isActive(x, y + 1, z, cube);
        active += isActive(x, y + 1, z + 1, cube);

        //Lower Layer
        active += isActive(x - 1, y - 1, z - 1, cube);
        active += isActive(x - 1, y - 1, z, cube);
        active += isActive(x - 1, y - 1, z + 1, cube);

        active += isActive(x - 1, y, z - 1, cube);
        active += isActive(x - 1, y, z, cube);
        active += isActive(x - 1, y, z + 1, cube);

        active += isActive(x - 1, y + 1, z - 1, cube);
        active += isActive(x - 1, y + 1, z, cube);
        active += isActive(x - 1, y + 1, z + 1, cube);

        return active;
    }

    static int isActive(int x, int y, int z, List<List<List<Character>>> cube)
    {
        return isInBounds(x, y, z, cube) && cube.get(x)
            .get(y)
            .get(z)
            .equals('#') ? 1 : 0;
    }

    private static boolean isInBounds(int x, int y, int z, List<List<List<Character>>> cube)
    {
        return x >= 0 && x < cube.size() && y >= 0 && y < cube.get(0)
            .size() && z >= 0 && z < cube.get(0)
            .get(0)
            .size();
    }
}