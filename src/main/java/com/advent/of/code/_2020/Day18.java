package com.advent.of.code._2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Day18 extends Day
{
    public static void main(String[] args) throws IOException
    {
        long result = lines("src/main/resources/2020/Day18.txt")
            .stream().map(l -> {
                System.out.println(l);
                return parseExpression(l.replaceAll(" ", "").toCharArray());
            })
            .mapToLong(e -> e.calculate())
            .sum();

        System.out.println(result);

    }

    private static Expression parseExpression(char [] input){
        Expression expression = new Expression();

        for(int i = 0; i < input.length; i++){
            if(input[i] == '('){
                int end = findExpressionEnd(subArray(input, i, input.length - 1));

                Expression subExpression = parseExpression(subArray(input, i + 1, end + i -1));
                expression.subExpressions.add(subExpression);
                i = end + i;
            } else if(input[i] == '+' || input[i] == '*'){
                expression.operators.add(input[i]);
            } else {
                expression.subExpressions.add(new PrimitiveExpression(Character.getNumericValue(input[i])));
            }
        }

        return expression;
    }

    private static char[] subArray(char[] src, int start, int end){
        int length = end - start + 1;
        char[] subArray = new char[length];
        System.arraycopy(src, start, subArray, 0, length);

        return subArray;
    }

    private static int findExpressionEnd(char[] input)
    {
        Stack<Character> stack = new Stack<>();

        for(int i = 0; i < input.length; i++){
            char curr = input[i];

            if(curr == '('){
                stack.push(curr);
            }

            if(curr == ')'){
                stack.pop();
            }

            if(stack.isEmpty()){
                return i;
                //Found the end of expression!
            }
        }

        throw new IllegalArgumentException("Invalid expression!");
    }

    static class Expression{
        LinkedList<Expression> subExpressions = new LinkedList<>();
        LinkedList<Character> operators = new LinkedList<>();


        public long calculate(){
            //Merge each pair of + into one Expression. then multiply
            List<Expression> merged = new ArrayList<>();

            Expression left = subExpressions.poll();

            while (!operators.isEmpty()){
                Character operator = operators.poll();
                Expression right = subExpressions.poll();
                if(operator == '+'){
                    Expression sum = new PrimitiveExpression(left.calculate() + right.calculate());
                    left = sum;

                } else {
                    merged.add(left);
                    left = right;
                }
            }

            merged.add(left);
            long result = 1;

            for(Expression e : merged){
                result *= e.calculate();
            }

            return result;
        }
    }

    static class PrimitiveExpression extends Expression{
        long value;

        public PrimitiveExpression(long value)
        {
            this.value = value;
        }

        @Override
        public long calculate(){
            return value;
        }
    }
}
