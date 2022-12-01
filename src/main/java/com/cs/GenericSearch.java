package com.cs;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class GenericSearch {
    public static <T> Node<T> astar(T initial, Predicate<T> goalTest, Function<T, List<T>> successors, ToDoubleFunction<T> heuristic){
        //Frontier is where we've yet to go
        PriorityQueue<Node<T>> frontier = new PriorityQueue<>();
        frontier.offer(new Node<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));

        Map<T, Double> explored = new HashMap<>();
        explored.put(initial, 0.0);


        //Keep going until there is nowhere to go
        while(!frontier.isEmpty()){
            Node<T> current = frontier.poll();

            T currentState = current.state;

            if(goalTest.test(currentState)){
                return current;
            }

            for(T child : successors.apply(currentState)){
                //1 here assumes grid. need a cost function for more sophisticated apps
                double newCost = current.cost + 1;

                if(!explored.containsKey(child) || explored.get(child) > newCost){
                    explored.put(child, newCost);
                }

                frontier.offer(new Node<>(child, current, newCost, heuristic.applyAsDouble(child)));
            }
        }

        //Have been everywhere, goal not found
        return null;

    }

    public static <T> Node<T> bfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors){
        Queue<Node<T>> frontier = new LinkedList<>();
        frontier.offer(new Node<T>(initial, null));

        Set<T> explored = new HashSet<>();
        explored.add(initial);

        while(!frontier.isEmpty()){
            Node<T> current = frontier.poll();
            T currentState = current.state;

            if(goalTest.test(currentState)){
                return current;
            }

            for(T child : successors.apply(currentState)){
                if(explored.contains(child)){
                    continue;
                }

                explored.add(child);
                frontier.offer(new Node<>(child, current));
            }
        }

        return null;


    }
    public static <T> Node<T> dfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors){
        //frontier is where we're yet to go
        Stack<Node<T>> frontier = new Stack<>();

        frontier.push(new Node<>(initial, null));

        //explores is where we've been:
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        while(!frontier.isEmpty()){
            Node<T> currentNode = frontier.pop();


            T currentState = currentNode.state;

            //If we found the goal, we are done:
            if(goalTest.test(currentState)){
                return currentNode;
            }

            for(T child : successors.apply(currentState)){
                if(explored.contains(child)){
                    //skip if already visited
                    continue;
                }
                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }

        //Could not find the goal
        return null;
    }

    public static <T> List<T> nodeToPath(Node<T> node){
        List<T> path = new ArrayList<>();
        path.add(node.state);

        while(node.parent != null){
            node = node.parent;
            path.add(0, node.state);
        }

        return path;
    }
}
