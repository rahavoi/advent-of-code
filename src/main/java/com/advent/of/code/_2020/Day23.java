package com.advent.of.code._2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class Day23
{
    public static void main(String[] args){
        CircularLinkedList cups = new CircularLinkedList();

        //My input:
        List<Integer> input = Arrays.asList(9,6,2, 7,1,3, 8,5,4);


        //Example input:
        //List<Integer> input = Arrays.asList(3,8,9, 1,2,5, 4,6,7);
        for(int i : input){
            cups.addNode(i);
        }

        for(int i = 10; i < 1_000_000 + 1; i++){
            cups.addNode(i);
        }


        Node currentNode = cups.getByValue(input.get(0));

        for (int i = 0; i < 10_000_000; i++){
            if(i % 100000 == 0){
                //System.out.println("Round " + (i + 1));
            }
            currentNode = round(cups, currentNode);
        }

        Node one = cups.getByValue(1);
        long next = one.nextNode.value;
        long nextNext = one.nextNode.nextNode.value;

        System.out.println(next);
        System.out.println(nextNext);



        Long result = next * nextNext;
        System.out.println("Part 2: " + result);

    }

    private static Node round(CircularLinkedList cups, Node curr)
    {
        //cups.print();
        //System.out.println("Current: " + curr.value);

        Node next1 = curr.nextNode;
        Node next2 = next1.nextNode;
        Node next3 = next2.nextNode;

        //Remove next 3 nodes from the chain.
        curr.nextNode = next3.nextNode;


        //System.out.println("Picked up: " + next1.value  + ", " + next2.value  + ", "  + next3.value);

        //find destination:
        Integer dest = curr.value - 1;

        while (next1.value == dest || next2.value == dest || next3.value == dest){
            dest--;
        }

        if(!cups.valuesToNodes.containsKey(dest)){
            Integer max = cups.valuesToNodes.size();

            while(next1.value == max || next2.value == max || next3.value == max){
                max--;
            }

            dest = max;
        }

        //System.out.println("Destination: " + dest);

        Node destNode = cups.valuesToNodes.get(dest);
        Node nextAfterDest = destNode.nextNode;

        destNode.nextNode = next1;
        next3.nextNode = nextAfterDest;

        //cups.print();

        return curr.nextNode;
    }


    static class Node {
        int value;
        Node nextNode;

        public Node(int value) {
            this.value = value;
        }
    }


    static class CircularLinkedList {
        Map<Integer, Node> valuesToNodes = new HashMap<>();
        private Node head = null;
        private Node tail = null;

        public void addNode(int value) {
            Node newNode = new Node(value);

            if (head == null) {
                head = newNode;
            } else {
                tail.nextNode = newNode;
            }

            tail = newNode;
            tail.nextNode = head;

            valuesToNodes.put(value, newNode);
        }

        public Node getByValue(int value){
            return valuesToNodes.get(value);
        }

        public void print(){
            StringJoiner sj = new StringJoiner(", ");
            Node current = head;
            while(current!= tail){
                sj.add(Integer.toString(current.value));
                current = current.nextNode;
            }

            sj.add(Integer.toString(tail.value));
            System.out.println(sj.toString());
            System.out.println();
        }
    }
}
