package com.advent.of.code._2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;


//Full rounds completed: 69. Sum of remaining hit points: 2804. Puzzle answer: 193476
public class Day15 {
    private final int elfPower = 34; //Found by manually binary-searching the optimal value :)
    private final char WALL = '#';
    private final char EMPTY_SPACE = '.';
    private final char ELF = 'E';
    private final char GOBLIN = 'G';

    int rounds;

    private Graph graph;
    private DijkstraAlgorithm alg;

    private char map[][];

    private List<Unit> allUnits = new ArrayList<>();
    private List<Unit> elves = new ArrayList<>();
    private List<Unit> goblins = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        Day15 day15 = new Day15();

        day15.initializeMap();

        while(true){
            day15.round();
        }
    }

    private void round(){
        rounds++;
        Collections.sort(allUnits,
                Comparator.comparingInt((Unit u) -> u.y).thenComparingInt(u -> u.x));

        Iterator<Unit> copy = new ArrayList<>(allUnits).iterator();

        while (copy.hasNext()){
            Unit u = copy.next();

            if(u.life >= 0){
                u.move();
            }
        }

        printMap(true, Optional.empty());
        System.out.println("---------------------------------------------");
    }

    private class Unit {
        private int x;
        private int y;
        private char type;
        private int life = 200;
        private int power;

        public Unit(int x, int y, char type, int power) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.power = power;
        }

        public void move(){
            List<Unit> enemies = type == ELF ? goblins : elves;

            if(enemies.isEmpty()){
                System.out.println(type + ": We won!!!!");

                int hitPointsLeft = allUnits.stream()
                        .mapToInt(u -> u.life).sum();

                System.out.println((rounds - 1) * hitPointsLeft);
                printMap(true, Optional.empty());
                System.exit(1);

            }

            //Find Enemies standing on the neighborSquare:
            List<Unit> enemiesWithinReach = getEnemiesWithinReach(enemies);

            if(enemiesWithinReach.size() > 0){
                attack(enemies, enemiesWithinReach);
            } else {
                buildGraph(this);
                alg = new DijkstraAlgorithm(graph);

                Vertex source = getCurrentVertex();
                alg.execute(source);

                //Keep Moving.
                List<List<Vertex>> paths = enemies.stream()
                        .map(e -> findNeighbors(e.x, e.y, graph.getVertexes()))
                        .flatMap(l -> l.stream())
                        .map(square -> alg.getPath(square))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());


                OptionalInt shortestPathSize = paths.stream().mapToInt(List::size).min();

                Optional<List<Vertex>> shortestPath = Optional.empty();
                if(shortestPathSize.isPresent() && shortestPathSize.getAsInt() > 1){
                    List<List<Vertex>> shortestPaths = paths.stream()
                            .filter(p -> p.size() == shortestPathSize.getAsInt()).collect(Collectors.toList());


                    //Find the 1st destination in the reading order:
                    Vertex closestDest = shortestPaths.stream().map(p -> p.get(p.size() - 1))
                            .min(Comparator.comparingInt(Vertex::getY).thenComparingInt(Vertex::getX)).get();

                    List<List<Vertex>> shortestPathsWith1stDestInReadOrder = new ArrayList<>();
                    List<Vertex> shortestPathWith1stDestInReadOrder = shortestPaths.stream().filter(p -> p.get(p.size() - 1) == closestDest).collect(Collectors.toList()).get(0);
                    shortestPathsWith1stDestInReadOrder.add(shortestPathWith1stDestInReadOrder);

                    //Checking if it's possible to get there using any other way.
                    graph.getVertexes().remove(shortestPathWith1stDestInReadOrder.get(1));
                    Edge edgeToRemove = graph.getEdges().stream().filter(edge -> edge.getDestination() == shortestPathWith1stDestInReadOrder.get(1)
                            && edge.getSource() == shortestPathWith1stDestInReadOrder.get(0)).findFirst().get();
                    graph.getEdges().remove(edgeToRemove);

                    alg = new DijkstraAlgorithm(graph);

                    alg.execute(source);
                    List<Vertex> anotherPath = alg.getPath(closestDest);

                    if(anotherPath != null && anotherPath.size() == shortestPathWith1stDestInReadOrder.size()){
                        shortestPathsWith1stDestInReadOrder.add(anotherPath);
                    }

                    if(shortestPathsWith1stDestInReadOrder.size() > 1){
                        //Find the 1st step in the reading order:
                        shortestPath = shortestPathsWith1stDestInReadOrder.stream()
                                .min(Comparator.comparingInt((List<Vertex> p) -> p.get(1).getY()).thenComparingInt(p -> p.get(1).getX()));

                    } else {
                        shortestPath = Optional.of(shortestPathsWith1stDestInReadOrder.get(0));
                    }


                }

                if(shortestPath.isPresent()){
                    Vertex nextStep = shortestPath.get().get(1);

                    System.out.println(type + " moving from (" + x + "," +  y + ") to ("  + nextStep.getX() + "," + nextStep.getY() + ")" );

                    x = nextStep.getX();
                    y = nextStep.getY();
                    //printMap(true, pathToTheNearestEnemy.get());

                    List<Unit> currentEnemiesWithinReach = getEnemiesWithinReach(enemies);
                    if(currentEnemiesWithinReach.size() > 0){
                        attack(enemies, currentEnemiesWithinReach);
                    }

                } else {
                    System.out.println(type + "(" + x + "," + y + ") " + life + ": I am blocked!");
                }
            }

            printMap(true, Optional.empty());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
        }

        private void attack(List<Unit> enemies, List<Unit> enemiesWithinReach) {
            System.out.println(type + "(" + x + "," + y + ") " + life + ": Attack!");
            //Hit the weakest.

            Unit weakestEnemy = enemiesWithinReach.stream()
                    .min(Comparator.comparing((Unit u) -> u.life)
                            .thenComparing(u -> u.y).thenComparingInt(u -> u.x)).get();

            System.out.println("Enemy hurt: " + weakestEnemy.type + ": (" + weakestEnemy.x + "," + weakestEnemy.y + ") " + weakestEnemy.life + " - 3" );
            weakestEnemy.life -= this.power;

            if(weakestEnemy.life <= 0){
                System.out.println("Enemy dies: " + weakestEnemy.type + ": (" + weakestEnemy.x + "," + weakestEnemy.y + ") " + weakestEnemy.life );

                if(weakestEnemy.type == ELF){
                    System.out.println("You lost an elf! GAME OVER!");
                    System.exit(1);
                }
                enemies.remove(weakestEnemy);
                allUnits.remove(weakestEnemy);
            }
        }

        private List<Unit> getEnemiesWithinReach(List<Unit> enemies) {
            return enemies.stream().filter(enemy -> (x == enemy.x && Math.abs(y - enemy.y) == 1) ||
                    (y == enemy.y && Math.abs(x - enemy.x) == 1))
                    .filter(e -> e.life > 0)
                    .collect(Collectors.toList());
        }

        private Vertex getCurrentVertex() {
            return graph.getVertexes().stream().filter(v -> v.getX() == x && v.getY() == y).findFirst().get();
        }

    }

    private void initializeMap() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2018/Day15.txt"));
        map = new char[lines.get(0).length()][lines.size()];

        for(int y = 0; y < lines.size(); y++){
            String line = lines.get(y);

            for(int x = 0; x < line.length(); x++){
                char c = line.charAt(x);

                if(c == ELF){
                    Unit elf = new Unit(x, y, c, elfPower);
                    allUnits.add(elf);
                    elves.add(elf);
                    c = EMPTY_SPACE;
                }

                if(c == GOBLIN){
                    Unit goblin = new Unit(x, y, c, 3);
                    allUnits.add(goblin);
                    goblins.add(goblin);
                    c = EMPTY_SPACE;
                }

                map[x][y] = c;
            }
        }
    }

    private void buildGraph(Unit unitBuildingGraph) {
        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                final int xCoord = x;
                final int yCoord = y;
                char type = map[x][y];

                Optional<Unit> unitInTheWay = allUnits.stream().filter(e -> e.x == xCoord && e.y == yCoord).findFirst();

                if(type != WALL && (!unitInTheWay.isPresent() || unitInTheWay.get() == unitBuildingGraph || unitInTheWay.get().life <= 0)){
                    vertices.add(new Vertex(x, y));
                }
            }
        }

        for(Vertex vertex : vertices){
            List<Vertex> neighbors = findNeighbors(vertex.getX(), vertex.getY(), vertices);

            for(Vertex neighbor : neighbors){
                edges.add(new Edge(vertex, neighbor, 1));
            }
        }

        graph = new Graph(vertices, edges);

    }

    private List<Vertex> findNeighbors(int x, int y, List<Vertex> vertices) {

        List<Vertex> neighbors = new ArrayList<>();
        Optional<Vertex> northNeighbor = vertices.stream().filter(v -> x == v.getX() && y == v.getY() - 1).findFirst();
        Optional<Vertex> southNeighbor = vertices.stream().filter(v -> x == v.getX() && y == v.getY() + 1).findFirst();
        Optional<Vertex> eastNeighbor = vertices.stream().filter(v -> x == v.getX() + 1 && y == v.getY()).findFirst();
        Optional<Vertex> westNeighbor = vertices.stream().filter(v -> x == v.getX() - 1 && y == v.getY()).findFirst();

        northNeighbor.ifPresent(neighbors::add);
        southNeighbor.ifPresent(neighbors::add);
        eastNeighbor.ifPresent(neighbors::add);
        westNeighbor.ifPresent(neighbors::add);

        return neighbors;
    }

    private void printMap(boolean showUnits, Optional<List<Vertex>> path){

        for(int y = 0; y < map[0].length; y++){
            for(int x = 0; x < map.length; x++){
                char c = map[x][y];

                if(showUnits && c == EMPTY_SPACE){
                    final int xCoord = x;
                    final int yCoord = y;
                    Optional<Unit> elf = elves.stream().filter(e -> e.x == xCoord && e.y == yCoord).findFirst();

                    if(elf.isPresent()){
                        c = ELF;
                    } else {
                        Optional<Unit> goblin = goblins.stream().filter(e -> e.x == xCoord && e.y == yCoord).findFirst();

                        if(goblin.isPresent()){
                            c = GOBLIN;
                        }
                    }

                    if(path.isPresent() && path.get().stream().anyMatch(vertex -> vertex.getX() == xCoord && vertex.getY() == yCoord)){
                        c = 'X';
                    }
                }

                System.out.print(c);
            }
            System.out.println();
        }
    }



}
