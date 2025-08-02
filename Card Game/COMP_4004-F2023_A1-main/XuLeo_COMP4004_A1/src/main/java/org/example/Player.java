package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private int health;
    private List<Card> hand;

    private List<Card> injuryDeck;

    private static final int MAX_HEALTH = 100;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.injuryDeck = new ArrayList<>();
        this.health = MAX_HEALTH;
    }

    public String toString() {
        return "Name: "+ name + "\nHand: "+ hand;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public void addInjuryDeck(Card injuryCard) {
        injuryDeck.add(injuryCard);
    }
    public List<Card> getInjuryDeck() {
        return injuryDeck;
    }

    public void resetInjuryDeck() {
        injuryDeck = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> newHand, int playerId) {
        hand = newHand;
        for(Card card: newHand) {
            card.setPlayer(playerId);
        }
    }

}
