package com.advent.of.code._2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day21 {
    static int dice = 1;
    static int timesRolled = 0;

    static int[] possibleRolls = {1,2,3};
    static List<String> possibleCombinations = new ArrayList<>();

    static Map<GameState, GameStats> stateStats = new HashMap<>();


    public static void main(String[] args){
        //part1();
        initAllPossibleCombinations();

        System.out.println("Each round results in  up to " + possibleCombinations.size() + " combos for each player");

        part2();
    }

    private static void part2(){
        GameState gameState = new GameState(10, 4, 0, 0);

        GameStats stats = play(gameState);

        System.out.println(stats.player1Wins);
        System.out.println(stats.player2Wins);
        //444356092776315
        //11997614504960505
    }

    private static GameStats play(GameState gameState){
        if(gameState.player1Score >= 21){
            //Player 1 wins
            return new GameStats(1, 0);
        }

        if(gameState.player2Score >= 21){
            return new GameStats(0, 1);
        }

        if(stateStats.get(gameState) != null){
            return stateStats.get(gameState);
        }

        //All possible next steps:
        GameStats stats = new GameStats(0, 0);

        getAllPossibleNextStates(gameState)
            .forEach(s -> {
                GameStats gs = play(s);
                stats.player1Wins += gs.player1Wins;

                stats.player2Wins += gs.player2Wins;
                stateStats.put(s, gs);
            });

        if(stats.player1Wins == 444356092776315L){
            System.out.println();
        }

        return stats;

    }

    static List<GameState> getAllPossibleNextStates(GameState gameState){
        List<GameState> states = new ArrayList<>();
        possibleCombinations.stream().forEach(combo -> {
            int player1Roll = Character.getNumericValue(combo.charAt(0)) + Character.getNumericValue(combo.charAt(1)) + Character.getNumericValue(combo.charAt(2));

            GameState newState = new GameState(gameState);

            newState.player1Pos = newState.player1Pos + player1Roll;
            if(newState.player1Pos > 10){ newState.player1Pos -= 10; }
            newState.player1Score += newState.player1Pos;

            if(newState.player1Score >= 21){
                //Case when player one won already. No need to multiply universes anymore, as game is over
                states.add(newState);
            } else {
                //Multiplying universes for each possible player 2 combo
                states.addAll(getAllPossibleStatesForPlayer2(newState));
            }
        });

        return states;
    }

    private static List<GameState> getAllPossibleStatesForPlayer2(GameState state){
        return possibleCombinations.stream().map(c ->{
            int player2Roll = Character.getNumericValue(c.charAt(0)) + Character.getNumericValue(c.charAt(1)) + Character.getNumericValue(c.charAt(2));
            GameState bothPlayersState = new GameState(state);

            bothPlayersState.player2Pos = bothPlayersState.player2Pos + player2Roll;
            if(bothPlayersState.player2Pos > 10){ bothPlayersState.player2Pos -= 10; }

            bothPlayersState.player2Score += bothPlayersState.player2Pos;
            return bothPlayersState;
        }).collect(Collectors.toList());
    }

    private static void initAllPossibleCombinations(){
        combine("");
    }

    private static void combine(String current){
        if(current.length() == 3){
            possibleCombinations.add(current);
        } else {
            Arrays.stream(possibleRolls).forEach(i -> combine(current + i));
        }
    }

    static class GameState {
        int player1Pos;
        int player2Pos;

        int player1Score;
        int player2Score;

        public GameState(int player1Pos, int player2Pos, int player1Score, int player2Score) {
            this.player1Pos = player1Pos;
            this.player2Pos = player2Pos;
            this.player1Score = player1Score;
            this.player2Score = player2Score;
        }

        public GameState(GameState a) {
            this.player1Pos = a.player1Pos;
            this.player2Pos = a.player2Pos;
            this.player1Score = a.player1Score;
            this.player2Score = a.player2Score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            GameState gameState = (GameState) o;
            return player1Pos == gameState.player1Pos && player2Pos == gameState.player2Pos
                && player1Score == gameState.player1Score && player2Score == gameState.player2Score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(player1Pos, player2Pos, player1Score, player2Score);
        }
    }

    static class GameStats{
        long player1Wins;
        long player2Wins;

        public GameStats(long player1Wins, long player2Wins) {
            this.player1Wins = player1Wins;
            this.player2Wins = player2Wins;
        }

        public long getPlayer1Wins() {
            return player1Wins;
        }

        public long getPlayer2Wins() {
            return player2Wins;
        }
    }

    private static void part1() {
        long playerOneScore = 0;
        long playerTwoScore = 0;

        int playerOnePosition = 10;
        int playerTwoPosition = 4;


        boolean playerOneTurn = true;

        while(playerOneScore < 1000 && playerTwoScore < 1000){
            //Roll dice three times:
            int rollResult = roll() + roll() + roll();

            if(playerOneTurn){
                playerOnePosition += rollResult;

                while(playerOnePosition > 10){
                    playerOnePosition -=10;
                }
                playerOneScore += playerOnePosition;
            } else{
                playerTwoPosition += rollResult;

                while(playerTwoPosition > 10){
                    playerTwoPosition -=10;
                }
                playerTwoScore += playerTwoPosition;
            }

            playerOneTurn = !playerOneTurn;

        }

        System.out.println(timesRolled * Math.min(playerOneScore, playerTwoScore));
    }

    static int roll(){
        timesRolled++;
        if(dice > 100){
            dice = 1;
        }

        return dice++;
    }
}
