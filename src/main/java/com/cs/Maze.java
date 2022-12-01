package com.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Maze {
    private final int rows, columns;
    private final MazeLocation start, goal;
    private final Cell[][] grid;

    public static void main(String[] args){
        Maze maze = new Maze();
        System.out.println(maze);

        Node<MazeLocation> solution1 = GenericSearch.dfs(maze.start, maze::goalTest, maze::successors);

        if(solution1 == null){
            System.out.println("No solution found for bfs :(");
        } else {
            List<MazeLocation> path1 = GenericSearch.nodeToPath(solution1);
            maze.mark(path1);
            System.out.println(maze);
            maze.clear(path1);
        }

        Node<MazeLocation> solution2 = GenericSearch.bfs(maze.start, maze::goalTest, maze::successors);

        if(solution2 == null){
            System.out.println("No solution found for dfs :(");
        } else {
            List<MazeLocation> path2 = GenericSearch.nodeToPath(solution2);
            maze.mark(path2);
            System.out.println(maze);
            maze.clear(path2);
        }

        Node<MazeLocation> solution3 = GenericSearch.astar(
                maze.start,
                maze::goalTest,
                maze::successors,
                maze::manhattanDistance);

        if(solution3 == null){
            System.out.println("No solution found for A* :(");
        } else {
            List<MazeLocation> path3 = GenericSearch.nodeToPath(solution3);
            maze.mark(path3);
            System.out.println(maze);
            maze.clear(path3);
        }
    }

    public Maze(int rows, int columns, MazeLocation start, MazeLocation goal, double sparseness) {
        this.rows = rows;
        this.columns = columns;
        this.start = start;
        this.goal = goal;

        //fill the grid with empty cells
        grid = new Cell[rows][columns];

        for(Cell[] row : grid){
            Arrays.fill(row, Cell.EMPTY);
        }

        randomlyFill(sparseness);

        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public double manhattanDistance(MazeLocation ml){
        return Math.abs(ml.row - goal.row) + Math.abs(ml.column - goal.column);
    }

    public boolean goalTest(MazeLocation location){
        return Objects.equals(location, goal);
    }

    public List<MazeLocation> successors(MazeLocation location){
        List<MazeLocation> locations = new ArrayList<>();

        if(location.row + 1 < this.rows && grid[location.row + 1][location.column] != Cell.BLOCKED){
            locations.add(new MazeLocation(location.row + 1, location.column));
        }
        if(location.row - 1 >= 0 && grid[location.row -1][location.column] != Cell.BLOCKED){
            locations.add(new MazeLocation(location.row - 1, location.column));
        }
        if(location.column - 1 >= 0 && grid[location.row][location.column - 1] != Cell.BLOCKED) {
            locations.add(new MazeLocation(location.row, location.column - 1));
        }

        if(location.column + 1 < this.columns && grid[location.row][location.column + 1] != Cell.BLOCKED) {
            locations.add(new MazeLocation(location.row, location.column + 1));
        }

        return locations;
    }

    public Maze(){
        this(10, 10, new MazeLocation(0, 0), new MazeLocation(9,9), 0.2f);
    }

    private void randomlyFill(double sparseness){
        for(int row = 0; row < this.rows; row++){
            for(int column = 0; column < this.columns; column++){
                if(Math.random() < sparseness){
                    grid[row][column] = Cell.BLOCKED;
                }
            }
        }
    }

    public void mark(List<MazeLocation> path){
        path.forEach(l -> grid[l.row][l.column] = Cell.PATH);
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public void clear(List<MazeLocation> path){
        path.forEach(l -> grid[l.row][l.column] = Cell.EMPTY);
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(Cell[] row : grid){
            for(Cell cell : row){
                sb.append(cell);
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public enum Cell {
        EMPTY(" "),
        BLOCKED("X"),
        START("S"),
        GOAL("G"),
        PATH("*");

        private String code;

        Cell(String code){
            this.code = code;
        }

        @Override
        public String toString(){
            return code;
        }
    }

    public static class MazeLocation {
        public final int row;
        public final int column;

        public MazeLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MazeLocation mazeCell = (MazeLocation) o;
            return row == mazeCell.row && column == mazeCell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }
}
