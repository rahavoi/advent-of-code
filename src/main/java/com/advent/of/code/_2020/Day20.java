package com.advent.of.code._2020;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day20 extends Day
{
    static Map<Integer, char[][]> tiles;
    static Map<Integer, List<Integer>> tileToNeighbors;
    static Set<Integer> processed = new HashSet<>();


    static Queue<ProcessingInfo> queue = new LinkedList<>();

    static Integer firstCornerId;


    static int[][] arrangedIds;

    public static void main(String[] args){
        tiles = parse();
        tileToNeighbors = getTileNeighbors(tiles);


        List<Integer> cornerIds = tileToNeighbors.entrySet().stream()
            .filter(e -> e.getValue().size() == 2)
            .map(e -> e.getKey())
            .collect(Collectors.toList());

        long result = 1;

        for(int i :cornerIds){
            result *= i;
        }

        System.out.println("Part 1: " + result);

        int size = (int) Math.sqrt((double) tiles.size());
        arrangedIds = new int[size][size];

        firstCornerId = cornerIds.get(3);
        //Needed for test input:
        //tiles.put(firstCornerId, mirrorHorizontally(tiles.get(firstCornerId)));

        while (findBottom(firstCornerId) == null){
            System.out.println("Bummer");
            rotateMatrix(tiles.get(firstCornerId));
        }


        queue.add(new ProcessingInfo(firstCornerId, 0, 0));
        while (!queue.isEmpty()){
            process();
        }

        print(arrangedIds);

        List<List<Character>> assembledPicture = new ArrayList<>();
        for(int i = 0; i < arrangedIds.length; i++){

            int[] ids = arrangedIds[i];
            for(int j = 0; j < 10; j++){
                List<Character> row = new ArrayList<>();
                for(int id : ids){
                    if(j != 0 && j != 9){
                        char[] chars = tiles.get(id)[j];

                        for(int x = 1; x < 9; x++){
                            row.add(chars[x]);
                        }
                    }
                }
                if(row.size() > 0){
                    assembledPicture.add(row);
                }
            }

        }

        //Picture:
        char[][] monochrome = new char[assembledPicture.size()][assembledPicture.size()];
        for(int i = 0; i < assembledPicture.size(); i++){
            for(int j = 0; j < assembledPicture.size(); j++){
                monochrome[i][j] = assembledPicture.get(i).get(j);
            }
        }

        int countPounds = 0;
        for(List<Character> row : assembledPicture){
            for(Character c : row){
                if(c == '#'){
                    countPounds++;
                }
                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println("Size: " + assembledPicture.size() + " by " + assembledPicture.get(0).size());

        monochrome = mirrorHorizontally(monochrome);
        rotateMatrix(monochrome);
        rotateMatrix(monochrome);


        int oneMonster = 15;
        int countMonsters = 0;
        for(int y = 0; y < monochrome.length - 2; y++){
            for(int x = 0; x< monochrome.length - 19; x++){

                char[] firstRow = monochrome[y];
                char[] secondRow = monochrome[y + 1];
                char[] thirdRow = monochrome[y + 2];

                if(firstRow[x + 18] == '#' &&
                    secondRow[x] == '#' &&
                    secondRow[x + 5] == '#' &&
                    secondRow[x + 6] == '#' &&
                    secondRow[x + 11] == '#' &&
                    secondRow[x + 12] == '#' &&
                    secondRow[x + 17] == '#' &&
                    secondRow[x + 18] == '#' &&
                    secondRow[x + 19] == '#'  &&
                    thirdRow[x + 1] == '#' &&
                    thirdRow[x + 4] == '#' &&
                    thirdRow[x + 7] == '#' &&
                    thirdRow[x + 10] == '#' &&
                    thirdRow[x + 13] == '#' &&
                    thirdRow[x + 16] == '#')

                {
                    countMonsters++;
                }
            }
        }

        System.out.println("Found Monsters: " + countMonsters);

        System.out.println("Pounds: " + (countPounds - countMonsters * oneMonster));

    }

    static void process(){
        ProcessingInfo processingInfo = queue.poll();
        Integer cornerId = processingInfo.tileId;
        int cornerY = processingInfo.y;
        int cornerX = processingInfo.x;
        List<Integer> neighbors = tileToNeighbors.get(cornerId);
        Assert.assertTrue(neighbors.size() <= 2);

        if(processed.contains(cornerId)){
            //System.out.println("Already processed.. " + cornerId);
            return;
        }

        arrangedIds[cornerY][cornerX] = cornerId;
        processed.add(cornerId);


        //System.out.println("Corner pos : " + cornerX + " " + cornerY);
        if(processed.size() == arrangedIds.length * arrangedIds.length){
            //System.out.println("Processed all");
            return;
        }

        //Find bottom piece
        Integer bottom = null;
        if(cornerY < arrangedIds.length - 1){
            bottom = findBottom(cornerId);
            arrangedIds[cornerY + 1][cornerX] = bottom;
            //processed.add(bottom);
            neighbors.remove(bottom);

            List<Integer> bottomNeighbors = tileToNeighbors.get(bottom);
            bottomNeighbors.remove(Integer.valueOf(cornerId));
        }

        //Find right:
        Integer right = null;
        if(cornerX < arrangedIds.length - 1){
            right = findRight(cornerId, bottom);
            arrangedIds[cornerY][cornerX + 1] = right;
            //processed.add(right);
            neighbors.remove(right);

            List<Integer> rightNeigbors = tileToNeighbors.get(right);
            rightNeigbors.remove(Integer.valueOf(cornerId));
        }

        if(right != null){
            queue.add(new ProcessingInfo(right, cornerY, cornerX + 1));
        }

        if(bottom != null){
            queue.add(new ProcessingInfo(bottom, cornerY + 1, cornerX));
        }
    }

    static Integer findRight(Integer id, Integer bottomId){
        char[][] corner = tiles.get(id);

        //temp rotate to put right side to the top..
        rotateMatrix(corner);
        List<Integer> neighbors = tileToNeighbors.get(id);

        for(Integer neighborId : neighbors){
            char [][] candidate = tiles.get(neighborId);
            for(int r = 0; r < 2; r++){
                for(int i = 0; i < 2; i++){
                    for(int j = 0; j < 4; j++){
                        if(Arrays.equals(corner[0], candidate[0])){
                            //System.out.println("To the right of " + id + " is " + neighborId);

                            //Rotate corner 3 more times to put it in initial position:
                            rotateMatrix(corner);
                            rotateMatrix(corner);
                            rotateMatrix(corner);

                            //Orient right piece properly
                            char [][] oriented = mirrorHorizontally(candidate);
                            rotateMatrix(oriented);
                            tiles.put(neighborId, oriented);

                            return  neighborId;
                        } else {
                            //System.out.println("Didn't match:" + neighborId);
                            //print(corner);
                            //print(candidate);

                            //System.out.println("Rotating " + neighborId);
                            rotateMatrix(candidate);
                        }
                    }

                    //System.out.println("Flipping " + neighborId);
                    candidate = mirrorHorizontally(candidate);
                }
                //Bad corner orientation
                throw new IllegalArgumentException("Damnit. could not match " + id + " with " + neighborId);
            }
        }

        throw new IllegalArgumentException("Could not find right for " + id);
    }

    static Integer findBottom(Integer id){
        char[][] corner = tiles.get(id);
        List<Integer> neighbors = tileToNeighbors.get(id);

        //Temp move bottom row to the top. Will be undone once the matching tile is found
        rotateMatrix(corner);
        rotateMatrix(corner);

        outer:
        for(int neighborId : neighbors){
            char [][] candidate = tiles.get(neighborId);

            for(int r = 0; r < 2; r++){
                for(int i = 0; i < 4; i++){
                    if(Arrays.equals(corner[0], candidate[0])){
                        //System.out.println("To the bottom of " + id + " is " + neighborId);
                        //Rotate corner twice to move the bottom part where it belongs.
                        rotateMatrix(corner);
                        rotateMatrix(corner);


                        //Flip right part to position it properly:
                        char[][] flipped = mirrorHorizontally(candidate);
                        tiles.put(neighborId, flipped);

                        return neighborId;
                    } else {
                        //System.out.println("Rotating " + neighborId);
                        rotateMatrix(candidate);
                    }
                }
                //Flip candidate and try to rotate again..
                //System.out.println("Flipping " + neighborId);
                candidate = mirrorHorizontally(candidate);
            }
        }

        rotateMatrix(corner);
        rotateMatrix(corner);
        return null;
    }

    private static Map<Integer, List<Integer>> getTileNeighbors(Map<Integer, char[][]> tiles)
    {
        Set<Map.Entry<Integer, char[][]>> pieces = tiles.entrySet();
        Map<Integer, List<Integer>> tileToNeighbors = new HashMap<>();
        tiles.entrySet().forEach(entry -> {
            int pieceId = entry.getKey();
            char[][] currentPiece = entry.getValue();

            List<Integer> neighbors = new ArrayList<>();

            pieces.forEach(candidate -> {
                if(candidate != entry){
                    //System.out.println("Trying to match " + pieceId + " with " + candidate.getKey());
                    char[][] candidatePiece = candidate.getValue();
                    outer:
                    for(int m = 0; m < 2; m++){
                        for(int i = 0; i < 4; i++){
                            for(int j = 0; j < 4; j++){
                                if(Arrays.equals(currentPiece[0], candidatePiece[0]) ||
                                    Arrays.equals(currentPiece[0], candidatePiece[candidatePiece.length - 1]) ||
                                    Arrays.equals(currentPiece[currentPiece.length - 1], candidatePiece[0])){
                                    //System.out.println("Found Neighbor!");
                                    neighbors.add(candidate.getKey());

                                    //mirrorHorizontally(candidate);

                                    break outer;
                                }

                                //System.out.println("Rotating candidate " + candidate.getKey());
                                rotateMatrix(candidatePiece);
                            }

                            //System.out.println("Rotating current " + pieceId);
                            rotateMatrix(currentPiece);
                        }
                        //System.out.println("Flipping candidate " + candidate.getKey());
                        mirrorHorizontally(candidate);
                    }
                } else {
                    //System.out.println("Ignore.. same piece");
                }
            });

            tileToNeighbors.put(entry.getKey(), neighbors);
        });
        return tileToNeighbors;
    }

    private static void printRow(char[] row){
        for(char c : row){
            System.out.print(c);
        }
        System.out.println();
    }

    private static Map<Integer, char[][]> parse()
    {
        Map<Integer, char[][]> tiles = new HashMap<>();
        Integer id = null;
        char[][] tile = null;
        int currentRowIdx = 0;
        for(String line : lines("src/main/resources/2020/Day20.txt")){
            if(line.startsWith("Tile ")){
                id = Integer.parseInt(line.split("Tile ")[1].replace(":", ""));
                tile = new char[10][10];
                //System.out.println(id);
                currentRowIdx = 0;
            } else if(line.trim().length() == 0){
                tiles.put(id, tile);
                tile = new char[10][10];
            } else {
                tile[currentRowIdx] = line.toCharArray();
                currentRowIdx++;
            }
        }

        tiles.put(id, tile);
        System.out.println("Found tiles: " + tiles.size());
        return tiles;
    }

    static void mirrorHorizontally(Map.Entry<Integer, char[][]> entry)
    {
        char[][] tile = entry.getValue();

        char[][] horizontalImage = mirrorHorizontally(tile);

        entry.setValue(horizontalImage);
        //print(horizontalImage);
    }

    private static char[][] mirrorHorizontally(char[][] in)
    {
        int height = in.length;
        int width = in.length;
        char[][] out = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                out[i][width - j - 1] = in[i][j];
            }
        }
        return out;
    }

    static void rotateMatrix(char mat[][])
    {
        int N = mat.length;
        // Consider all squares one by one
        for (int x = 0; x < N / 2; x++) {
            // Consider elements in group
            // of 4 in current square
            for (int y = x; y < N - x - 1; y++) {
                // Store current cell in
                // temp variable
                char temp = mat[x][y];

                // Move values from right to top
                mat[x][y] = mat[y][N - 1 - x];

                // Move values from bottom to right
                mat[y][N - 1 - x]
                    = mat[N - 1 - x][N - 1 - y];

                // Move values from left to bottom
                mat[N - 1 - x][N - 1 - y] = mat[N - 1 - y][x];

                // Assign temp to left
                mat[N - 1 - y][x] = temp;
            }
        }
    }

    static void print(int[][] pic){
        for(int[] row : pic){
            for(int c : row){
                System.out.print(c);
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println();
    }

    static class ProcessingInfo{
        Integer tileId;
        int y;
        int x;

        public ProcessingInfo(Integer tileId, int y, int x)
        {
            this.tileId = tileId;
            this.y = y;
            this.x = x;
        }
    }
}
