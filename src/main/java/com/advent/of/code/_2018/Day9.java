package com.advent.of.code._2018;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day9 {
    public static void main(String [] args){
        Map<Integer, Long> scores = new HashMap<>();
        List<Long> circle = new ArrayList<>();
        circle.add(0l);
        circle.add(2l);
        circle.add(1l);
        circle.add(3l);

        int currentPos = 3;
        int currentPlayer = 4;
        int totalPlayers = 465;
        for(int i = 4; i < 7194000; i++){
            if(i % 100000 == 0){
                System.out.println(i);
            }
            int insertPos = currentPos + 2;
            if(insertPos > circle.size()){
                insertPos = insertPos - circle.size();
            }

            if(i % 23 != 0){
                circle.add(insertPos, new Long(i));
                currentPos = insertPos;
            } else {
                int indexToRemove = currentPos - 7;

                if(indexToRemove < 0){
                    indexToRemove = circle.size() + indexToRemove;
                }

                Long score = scores.get(currentPlayer);

                if(score == null){
                    score = 0l;
                }

                scores.put(currentPlayer, score + circle.get(indexToRemove) + i);
                Long scoresToRemove = circle.remove(indexToRemove);
                currentPos = indexToRemove;

                System.out.println("Player " + currentPlayer + " scored " + i + " and took out " + scoresToRemove   + " total scored this time: " + (i + scoresToRemove));
            }

            currentPlayer++;

            if(currentPlayer > totalPlayers){
                currentPlayer = 1;
            }
        }

        Map.Entry<Integer, Long> winner = scores.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get();

        System.out.println("Player " + winner.getKey() + " won with " + winner.getValue() + " points");

    }
}
