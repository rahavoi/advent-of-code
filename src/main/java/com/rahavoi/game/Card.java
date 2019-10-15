package com.rahavoi.game;

public class Card {
    Rank rank;
    Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString(){
        return new StringBuilder().append(rank.getCode()).append(suit.getCode()).toString();
    }

    public enum Rank{
        TWO("2", 2),
        THREE("3", 3),
        FOUR("4", 4),
        FIVE("5", 5),
        SIX("6", 6),
        SEVEN("7", 7),
        EIGHT("8", 8),
        NINE("9", 9),
        TEN("10", 10),
        JACK("J", 11),
        QUEEN("Q", 12),
        KING("K", 13),
        ACE("A", 10);

        private String code;
        private int weight;

        Rank(String code, int weight) {
            this.code = code;
            this.weight = weight;
        }

        public String getCode() {
            return code;
        }

        public int getWeight() {
            return weight;
        }
    }

    public enum Suit{
        DIAMONDS('D'),
        CLUBS('C'),
        HEARTS('H'),
        SPADES('S');

        char code;

        Suit(char code) {
            this.code = code;
        }

        public char getCode() {
            return code;
        }
    }
}
