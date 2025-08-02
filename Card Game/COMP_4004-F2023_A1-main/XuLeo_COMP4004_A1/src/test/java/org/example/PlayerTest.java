package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void testPlayer() {
        String name = "John Doe";
        List<Card> injuryDeck = new ArrayList<>();
        List<Card> hand = new ArrayList<>();
        String s = "Name: "+ name + "\nHand: "+ hand;
        int health = 100;

        Player p1 = new Player(name);
        assertEquals(p1.getName(), name);
        assertEquals(p1.toString(), s);
        assertEquals(p1.getHealth(), health);
        assertEquals(p1.getInjuryDeck(), injuryDeck);
        assertEquals(p1.getHand(), hand);
    }

    @Test
    public void testInjuryCardAdded(){
        Card c = new Card("Swords", "5");
        Player p1 = new Player("John Doe");
        p1.addInjuryDeck(c);

        assertEquals(c, p1.getInjuryDeck().get(0));
    }

    @Test
    public void testResetInjuryDeck() {
        Card c = new Card("Swords", "5");
        Player p1 = new Player("John Doe");
        p1.addInjuryDeck(c);

        p1.resetInjuryDeck();
        assertTrue(p1.getInjuryDeck().isEmpty());
    }

}