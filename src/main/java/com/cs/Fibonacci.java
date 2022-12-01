package com.cs;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Fibonacci {
    public static void main(String[] args){
        //new Fibonacci().genUsingStream(10);
        //new Fibonacci().genIterative(10);
        //new Fibonacci().genRecursiveNaive(100);
        new Fibonacci().genRecurse(100);
    }

    private int last = 0, next = 1;

    private Map<Integer, Integer> memo = new HashMap<>();

    private void genRecursiveNaive(int size){
        for(int i = 0; i < size; i++){
            System.out.println(recurseNaive(i));
        }
    }

    private void genRecurse(int size){
        for(int i = 0; i < size; i++){
            System.out.println(recurse(i));
        }
    }

    private int recurse(int n){
        if(n < 2){
            return n;
        }

        if(memo.get(n) != null){
            return memo.get(n);
        }

        int result = recurse(n - 1) + recurse(n - 2);
        memo.put(n, result);
        return result;
    }

    private int recurseNaive(int n){
        if(n < 2){
            return n;
        }

        return recurseNaive(n - 1) + recurseNaive(n - 2);
    }

    private void genIterative(int size){
        int last = 0;
        int next = 1;

        for(int i = 0; i < size; i++){
            int oldLast = last;
            last = next;
            next = next + oldLast;
            System.out.println(oldLast);
        }
    }

    private void genUsingStream(int size){
        IntStream stream = IntStream.generate(() -> {
            int oldLast = last;
            last = next;
            next = next + oldLast;
            return oldLast;
        });

        stream.limit(size).forEach(System.out::println);
    }
}
