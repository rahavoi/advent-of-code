package com.advent.of.code._2020;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day22 extends Day
{
    static LinkedList<Integer> originalDeck1 = new LinkedList<>();
    static LinkedList<Integer> originalDeck2 = new LinkedList<>();

    public static void main(String[] args){
        List<String> lines = lines("src/main/resources/2020/Day22.txt");

        LinkedList<Integer> deck1 = new LinkedList<>();
        LinkedList<Integer> deck2 = new LinkedList<>();

        List<List<Integer>> decks = new ArrayList<>();
        decks.add(deck1);
        decks.add(deck2);
        int deckPos = -1;

        for(String l : lines){
            if(l.startsWith("Player")){
                deckPos++;
            } else if(l.trim().isEmpty()){
                //carry on
            } else {
                decks.get(deckPos).add(Integer.parseInt(l));
            }
        }

        //part1(deck1, deck2);

        originalDeck1 = deck1;
        originalDeck2 = deck2;

        part2(deck1, deck2);

    }

    private static void part2(LinkedList<Integer> deck1, LinkedList<Integer> deck2){
        gamePart2(deck1, deck2);
    }

    private static boolean gamePart2(LinkedList<Integer> deck1, LinkedList<Integer> deck2){
        int round = 1;
        List<List<Integer>> deck1Memo = new ArrayList<>();
        List<List<Integer>> deck2Memo = new ArrayList<>();
        try{
            while (true){
                //System.out.println("-- Round " + round++);
                roundPart2(deck1, deck2, deck1Memo, deck2Memo);
            }
        } catch (GameOverException e){
            return e.player1Wins;
        }
    }

    private static boolean roundPart2(LinkedList<Integer> deck1, LinkedList<Integer> deck2, List<List<Integer>> deck1Memo,
        List<List<Integer>> deck2Memo)
    {
        if(deck1.isEmpty() || deck2.isEmpty()){
            return checkGameOver(deck1, deck2);
        }

        //System.out.println("Player 1's deck: " + deck1);
        //System.out.println("Player 2's deck: " + deck2);

        if(deck1Memo.contains(deck1) && deck2Memo.contains(deck2)){
            //System.out.println("Same configuration detected. Player one wins game");
            countWinningDeck(deck1);

            throw new GameOverException("Same config", true);
        }

        deck1Memo.add(new ArrayList<>(deck1));
        deck2Memo.add(new ArrayList<>(deck2));

        Integer card1 = deck1.poll();
        Integer card2 = deck2.poll();

        if(deck1.size() >= card1 && deck2.size() >= card2){
            //System.out.println("Playing a sub-game to determine the winner...");
            //Recursive Combat!
            //Each player creates a new deck by making a copy of the next cards in their deck.
            LinkedList<Integer> subDeck1 = new LinkedList<>();

            for(int i = 0; i < card1; i++){
                subDeck1.add(deck1.get(i));
            }

            LinkedList<Integer> subDeck2 = new LinkedList<>();

            for(int i = 0; i < card2; i++){
                subDeck2.add(deck2.get(i));
            }

            boolean subResult = gamePart2(subDeck1, subDeck2);
            if(subResult){
                //System.out.println("Player 1 wins the round!");
                deck1.add(card1);
                deck1.add(card2);
            } else {
                //System.out.println("Player 2 wins the round!");
                deck2.add(card2);
                deck2.add(card1);
            }

            if(deck1.isEmpty() || deck2.isEmpty()){
                return checkGameOver(deck1, deck2);
            }

            return subResult;
        } else {
            if(card1 > card2){
                //System.out.println("Player 1 wins the round!");
                deck1.add(card1);
                deck1.add(card2);
            } else if(card1 < card2){
                //System.out.println("Player 2 wins the round!");
                deck2.add(card2);
                deck2.add(card1);
            }

            if(deck1.isEmpty() || deck2.isEmpty()){
                return checkGameOver(deck1, deck2);
            }

            return card1 > card2;
        }
    }

    private static boolean checkGameOver(LinkedList<Integer> deck1, LinkedList<Integer> deck2)
    {
        if(deck1.isEmpty()){
            //System.out.println("Player 2 wins the game");
            countWinningDeck(deck2);
            throw new GameOverException("PLayer 2 wins the game", false);
        }

        if(deck2.isEmpty()){
            //System.out.println("Player 1 wins the game");
            countWinningDeck(deck1);
            throw new GameOverException("PLayer 2 wins the game", true);
        }

        throw new IllegalArgumentException("Should never happen");
    }

    private static void part1(LinkedList<Integer> deck1, LinkedList<Integer> deck2)
    {
        boolean gameOver = roundPart1(deck1, deck2);

        while (!gameOver){
            gameOver = roundPart1(deck1, deck2);
        }

        LinkedList<Integer> winningDeck;
        if(deck1.isEmpty()){
            System.out.println("Player 2 won");
            winningDeck = deck2;
        } else {
            System.out.println("Player 1 won");
            winningDeck = deck1;
        }

        countWinningDeck(winningDeck);
    }

    private static long countWinningDeck(LinkedList<Integer> winningDeck)
    {
        long multiplier = 1;
        long result = 0;

        while (!winningDeck.isEmpty()){
            Integer card = winningDeck.pollLast();
            //System.out.println("+ "  + card + " * " + multiplier);
            result += card * multiplier++;
        }

        System.out.println(result);
        return result;
    }

    static boolean roundPart1(LinkedList<Integer> deck1 , LinkedList<Integer> deck2){
        System.out.println("Player 1's deck: " + deck1);
        System.out.println("Player 2's deck: " + deck2);

        Integer card1 = deck1.poll();
        Integer card2 = deck2.poll();

        System.out.println("Player 1 plays " + card1);
        System.out.println("Player 2 plays " + card2);

        if(card1 > card2){
            System.out.println("Player 1 wins the roundPart1!");
            deck1.add(card1);
            deck1.add(card2);
        } else if(card1 < card2){
            System.out.println("Player 2 wins the roundPart1!");
            deck2.add(card2);
            deck2.add(card1);
        } else {
            System.out.println("War!");
        }


        return deck1.isEmpty() || deck2.isEmpty();
    }

    static class GameOverException extends RuntimeException{
        boolean player1Wins;

        public GameOverException(String message, boolean player1Wins)
        {
            super(message);
            this.player1Wins = player1Wins;
        }
    }
}
