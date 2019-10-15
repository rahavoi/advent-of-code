package com.rahavoi.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class War {
    public static void main(String[] args){
        new War().play();
    }

    private void play(){
        List<Card> cards = getDeck();

        cards.forEach(System.out::println);
        System.out.println("Total cards: " + cards.size());

        Collections.shuffle(cards);

        LinkedList<Card> player1 = new LinkedList<>();
        LinkedList<Card> player2 = new LinkedList<>();

        LinkedList<Card> current = player1;

        for(Card card : cards){
            current.add(card);
            current = current == player1 ? player2 : player1;
        }

        System.out.println("Player1 has " + player1.size());
        System.out.println("Player2 has " + player2.size());

        int counter = 0;
        while (true){
            if(isGameFinished(player1, player2)){
                break ;
            }

            Card player1Card = player1.pop();
            Card player2Card = player2.pop();

            if(player1Card.rank.getWeight() > player2Card.rank.getWeight()){
                player1.addLast(player1Card);
                player1.addLast(player2Card);
            } else if(player2Card.rank.getWeight() > player1Card.rank.getWeight()) {
                player2.addLast(player2Card);
                player2.addLast(player1Card);
            } else {
                //TODO: refactor me
                List<Card> prize = new ArrayList<>();
                prize.add(player1Card);
                prize.add(player2Card);

                war(player1, player2, prize);
            }

            counter++;
        }

        System.out.println("Player" + (player1.isEmpty() ? 2 : 1) + " won after " + counter + " iterations");
    }

    private void war(LinkedList<Card> player1, LinkedList<Card> player2, List<Card> prize){
        System.out.println("War!!!!");
        if(isGameFinished(player1, player2)) {
            return;
        }

        prize.add(player1.pop());
        prize.add(player2.pop());

        if(isGameFinished(player1, player2)) {
            return;
        }

        Card player1Card = player1.pop();
        Card player2Card = player2.pop();

        if(player1Card.rank.getWeight() > player2Card.rank.getWeight()){
            player1.addLast(player1Card);
            player1.addLast(player2Card);
            player1.addAll(prize);
            return;
        } else if(player2Card.rank.getWeight() > player1Card.rank.getWeight()) {
            player2.addLast(player2Card);
            player2.addLast(player1Card);
            player2.addAll(prize);
            return;
        } else {
            System.out.print("X2!!! ");
            prize.add(player1Card);
            prize.add(player2Card);

            war(player1, player2, prize);
        }
    }

    private boolean isGameFinished(List<Card> player1, List<Card> player2){
        return player1.isEmpty() || player2.isEmpty();
    }

    private List<Card> getDeck(){
        List<Card> cards = new ArrayList<>();

        for(Card.Rank rank : Card.Rank.values()){
            for(Card.Suit suit : Card.Suit.values()){
                cards.add(new Card(rank, suit));
            }
        }

        return cards;
    }
}