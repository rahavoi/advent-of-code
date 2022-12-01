package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class Day18 {
    static int pos = 0;
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day18.txt"));

        part1(lines);
        part2(lines);
    }

    private static void part2(List<String> lines){
        long maxMagnitude = 0;
        for(int i = 0; i < lines.size(); i++){
            for(int j = 0; j < lines.size(); j++){
                if(i != j){
                    Node a = parse(lines.get(i));
                    Node b = parse(lines.get(j));

                    maxMagnitude = Math.max(a.add(b).getMagnitude(), maxMagnitude);
                }
            }
        }

        System.out.println("Max Magnitude: " + maxMagnitude);
    }

    private static void part1(List<String> lines) {
        Node sum = parse(lines.get(0));

        for(int i = 1; i < lines.size(); i++){
            sum = sum.add(parse(lines.get(i)));
        }

        System.out.println("Sum of magnitudes: " + sum.getMagnitude());
    }

    private static Node parse(String input){
        pos = 0;
        return parse(input.substring(1, input.length() -1).toCharArray());
    }

    static Node parse(char[] input){
        Node node = new Node();
        while(pos < input.length){
            char c = input[pos++];
            if(c == '['){
                Node child = parse(input);
                child.parent = node;

                if(node.left == null){
                    node.left = child;
                } else {
                    node.right = child;
                }

            } else if(c == ']'){
                break;
            } else if(c == ','){
                //ignore
            } else {
                Node child = new Node(Character.getNumericValue(c));
                child.parent = node;

                if(node.left == null){
                    node.left = child;
                } else {
                    node.right = child;
                }
            }
        }

        return node;
    }

    static class Node {
        Node parent;
        Node left;
        Node right;
        int val; // only leaf nodes have it

        public Node(){}

        public Node(int val) {
            this.val = val;
        }

        boolean isLeaf(){
            return left == null && right == null;
        }

        void reduce(){
            boolean explode = explodeLeftMostPairAtLevel4();
            boolean split = false;

            if(!explode){
                split = splitLeftMostLeafGreaterThan10();
            }

            if(explode || split){
                //keep reducing
                reduce();
            }
        }

        long getMagnitude(){
            /**
             * The magnitude of a pair is 3 times the magnitude of its left element plus 2 times the magnitude of its right element.
             * The magnitude of a regular number is just that number.
             */
            if(isLeaf()){
                return val;
            }

            return (this.left.getMagnitude() * 3) + (this.right.getMagnitude() * 2);
        }

        boolean explodeLeftMostPairAtLevel4(){
            //find left-most pair at level 4.
            Optional<Node> leftMostPairAtLevel4 = this.findLeftMostPairAtLevel4(0);

            if(leftMostPairAtLevel4.isPresent()){
                Node leftMost4 = leftMostPairAtLevel4.get();

                int leftVal = leftMost4.left.val;

                Optional<Node> nodeToAddLeftVal = leftMost4.parent.left != leftMost4 ?
                    Optional.of(leftMost4.parent.left) :
                    findClosestAnsestorWithLeftNode(leftMost4.parent);

                nodeToAddLeftVal.ifPresent(n -> addToRightMostLeaf(n, leftVal));

                int rightVal = leftMost4.right.val;
                Optional<Node> nodeToAddRightVal = leftMost4.parent.right != leftMost4 ?
                    Optional.of(leftMost4.parent.right) :
                    findClosestAnsestorWithRightNode(leftMost4.parent);

                nodeToAddRightVal.ifPresent(n -> addToLeftMostLeaf(n, rightVal));

                //Turn this node into a leaf
                leftMost4.left = null;
                leftMost4.right = null;
                leftMost4.val = 0;

                return true;
            }

            return false;
        }

        void addToRightMostLeaf(Node node, int value){
            if(node.isLeaf()){
                node.val = node.val + value;
            } else{
                addToRightMostLeaf(node.right, value);
            }
        }

        void addToLeftMostLeaf(Node node, int value){
            if(node.isLeaf()){
                node.val = node.val + value;
            } else{
                addToLeftMostLeaf(node.left, value);
            }
        }

        Optional<Node> findClosestAnsestorWithLeftNode(Node node){
            if(node.parent == null){
                //reached root
                return Optional.empty();
            }

            if(node.parent.left != node){
                return Optional.of(node.parent.left);
            }

            return findClosestAnsestorWithLeftNode(node.parent);
        }

        Optional<Node> findClosestAnsestorWithRightNode(Node node){
            if(node.parent == null){
                //reached root
                return Optional.empty();
            }

            if(node.parent.right != node){
                return Optional.of(node.parent.right);
            }

            return findClosestAnsestorWithRightNode(node.parent);
        }

        boolean splitLeftMostLeafGreaterThan10(){
            Optional<Node> node = findLeftMostLeafWithValueGreaterThan10();

            if(node.isPresent()){
                Node n = node.get();
                int val = n.val;
                n.left =  new Node(val / 2);
                n.left.parent = n;
                n.right = new Node((int) Math.ceil((double) val / 2));
                n.right.parent = n;
                n.val = 0;

                return true;
            } else {
                return false;
            }
        }

        Optional<Node> findLeftMostLeafWithValueGreaterThan10(){
            if(this.isLeaf() && this.val > 9){
                return Optional.of(this);
            }

            if(this.isLeaf()){
                return Optional.empty();
            }

            Optional<Node> l = left.findLeftMostLeafWithValueGreaterThan10();

            if(l.isPresent()){
                return l;
            }

            return right.findLeftMostLeafWithValueGreaterThan10();
        }

        Optional<Node> findLeftMostPairAtLevel4(int level){
            if(isLeaf()){
                return Optional.empty();
            }

            if(level == 4){
                return Optional.of(this);
            }

            Optional<Node> l = left.findLeftMostPairAtLevel4(level + 1);
            if(l.isPresent()){
                return l;
            }

            return right.findLeftMostPairAtLevel4(level + 1);
        }

        Node add(Node node){
            Node newNode = new Node();
            this.parent = newNode;
            node.parent = newNode;
            newNode.left = this;
            newNode.right = node;
            newNode.reduce();

            return newNode;
        }

        void print(int level){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < level; i++){
                sb.append(" ");
            }

            if(!isLeaf()){
                //System.out.println(sb + "At level " + level + ": ");
                left.print(level + 1);
                right.print(level + 1);
            } else{
                System.out.println(sb.toString() + val);
            }
        }
    }
}
