package org.example;

public class Card {
    private final String suit;
    private String number;
    private int playerId;
    private int indexHand;

    public Card(String suit, String number) {
        this.suit = suit;
        this.number = number;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return switch (number) {
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            case "6" -> 6;
            case "7" -> 7;
            case "8" -> 8;
            case "9" -> 9;
            case "10" -> 10;
            case "11" -> 11;
            case "12" -> 12;
            case "13" -> 13;
            case "14" -> 14;
            case "15" -> 15;
            case "Any" -> 16;
            default -> 0;
        };
    }

    public void setValue(String newVal) {
        this.number = newVal;
    }

    public String toString() {
        return suit +" "+ number;
    }

    public int cardDamage(String suit, String value) {
        return switch (suit) {
            case "Swords" -> switch (value) {
                case "6", "7", "8", "9" -> 10;
                default -> 5;
            };
            case "Arrows" -> switch (value) {
                case "8", "9", "10", "11" -> 10;
                default -> 5;
            };
            case "Sorcery" -> switch (value) {
                case "5", "6", "11", "12" -> 10;
                default -> 5;
            };
            case "Deception" -> switch (value) {
                case "6", "7", "9", "10" -> 10;
                default -> 5;
            };
            case "Merlin" -> 25;
            default -> 5;
        };
    }

    public void setPlayer(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setIndexHand(int indexHand) {
        this.indexHand = indexHand;
    }

    public int getIndexHand() {
        return indexHand;
    }
}
