package com.advent.of.code._2020;

import java.io.IOException;
import java.util.List;

public class Day11 extends Day
{

    public static void main(String[] args) throws IOException
    {
        List<String> lines = lines("src/main/resources/2020/Day11.txt");

        char[][] input = new char[lines.size()][lines.get(0).length()];

        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[0].length; j++){
                input[i][j] = lines.get(i).charAt(j);
            }
        }

        Day11 day = new Day11();
        int prevCount = 0;
        for(;;){
            int count = day.process(input);
            if(prevCount == count){
                break;
            }
            prevCount = count;
            //day.print(input);
            System.out.println(prevCount);
        }

        System.out.println(prevCount);
    }

    public int process(char[][] board) {
        char[][] temp = new char[board.length][board[0].length];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                int occupied = getOccupied(i, j, board);

                if(board[i][j] == 'L' && occupied == 0){
                        temp[i][j] = '#';
                } else if(board[i][j] == '#' && occupied >= 5){
                        temp[i][j] = 'L';
                } else {
                    temp[i][j] = board[i][j];
                }
            }
        }

        int count = 0;

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                board[i][j] = temp[i][j];
                if(board[i][j] == '#'){
                    count++;
                }
            }
        }

        return  count;
    }

    private int getOccupied(int row, int cell, char[][] board){
        int countOccupied = 0;

        //West
        int tmpRow = row;
        int tmpCell = cell -1;
        while (isInBounds(tmpRow, tmpCell, board)){
            if(isOccupied(tmpRow, tmpCell, board)){
                countOccupied++;
                break;
            }
            if(isEmpty(tmpRow,tmpCell, board)){
                break;
            }
            tmpCell--;
        }

        //West North
        tmpRow = row - 1;
        tmpCell = cell -1;
        while (isInBounds(tmpRow, tmpCell, board)){
            if(isOccupied(tmpRow, tmpCell, board)){
                countOccupied++;
                break;
            }
            if(isEmpty(tmpRow,tmpCell, board)){
                break;
            }
            tmpCell--;
            tmpRow--;
        }

        //North
        tmpRow = row - 1;
        tmpCell = cell;
        while (isInBounds(tmpRow, tmpCell, board)){
            if(isOccupied(tmpRow, tmpCell, board)){
                countOccupied++;
                break;
            }
            if(isEmpty(tmpRow,tmpCell, board)){
                break;
            }
            tmpRow--;
        }

        //North East
        tmpRow = row - 1;
        tmpCell = cell + 1;
        while (isInBounds(tmpRow, tmpCell, board)){
            if(isOccupied(tmpRow, tmpCell, board)){
                countOccupied++;
                break;
            }
            if(isEmpty(tmpRow,tmpCell, board)){
                break;
            }
            tmpRow--;
            tmpCell++;
        }

        //East
        tmpRow = row;
        tmpCell = cell + 1;
        while (isInBounds(tmpRow, tmpCell, board)){
            if(isOccupied(tmpRow, tmpCell, board)){
                countOccupied++;
                break;
            }
            if(isEmpty(tmpRow,tmpCell, board)){
                break;
            }
            tmpCell++;
        }

        //South East
        tmpRow = row + 1;
        tmpCell = cell - 1;
        while (isInBounds(tmpRow, tmpCell, board)){
            if(isOccupied(tmpRow, tmpCell, board)){
                countOccupied++;
                break;
            }
            if(isEmpty(tmpRow,tmpCell, board)){
                break;
            }
            tmpRow++;
            tmpCell--;
        }

        //South
        tmpRow = row + 1;
        tmpCell = cell;
        while (isInBounds(tmpRow, tmpCell, board)){
            if(isOccupied(tmpRow, tmpCell, board)){
                countOccupied++;
                break;
            }
            if(isEmpty(tmpRow,tmpCell, board)){
                break;
            }
            tmpRow++;
        }

        //South West
        tmpRow = row + 1;
        tmpCell = cell + 1;
        while (isInBounds(tmpRow, tmpCell, board)){
            if(isOccupied(tmpRow, tmpCell, board)){
                countOccupied++;
                break;
            }
            if(isEmpty(tmpRow,tmpCell, board)){
                break;
            }
            tmpRow++;
            tmpCell++;
        }

        return countOccupied;
    }

    private boolean isOccupied(int row, int cell, char[][] board){
        return isInBounds(row, cell, board) && board[row][cell] == '#';
    }

    private boolean isEmpty(int row, int cell, char[][] board){
        return isInBounds(row, cell, board) && board[row][cell] == 'L';
    }

    private boolean isInBounds(int row, int cell, char[][] board){
        return row >= 0 && row < board.length &
            cell >= 0 && cell < board[0].length;
    }

    private void print(char[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}

