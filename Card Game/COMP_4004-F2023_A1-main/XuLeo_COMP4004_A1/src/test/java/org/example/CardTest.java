package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    public void testSuit() {
        String suit = "Swords";
        String number = "5";
        Card c = new Card(suit, number);
        assertEquals(c.getSuit(), "Swords");
    }

    @Test
    public void testValue() {
        String suit = "Swords";
        String number = "5";
        Card c = new Card(suit,number);
        assertEquals(c.getValue(), 5);
    }

    @Test
    public void testCardDamage() {
        List<Card> deck = Main.generateDeck();
        for (Card card : deck) {
            String value = Integer.toString(card.getValue());
            switch (card.getSuit()) {
                case "Swords" -> {
                    if (value.equals("6") || value.equals("7") || value.equals("8") || value.equals("9")) {
                        assertEquals(card.cardDamage("Swords", value), 10);
                    } else {
                        assertEquals(card.cardDamage("Swords", value), 5);
                    }
                }
                case "Arrows" -> {
                    if (value.equals("8") || value.equals("9") || value.equals("10") || value.equals("11")) {
                        assertEquals(card.cardDamage("Arrows", value), 10);
                    } else {
                        assertEquals(card.cardDamage("Arrows", value), 5);
                    }
                }
                case "Sorcery" -> {
                    if (value.equals("5") || value.equals("6") || value.equals("11") || value.equals("12")) {
                        assertEquals(card.cardDamage("Sorcery", value), 10);
                    } else {
                        assertEquals(card.cardDamage("Sorcery", value), 5);
                    }
                }
                case "Deception" -> {
                    if (value.equals("6") || value.equals("7") || value.equals("9") || value.equals("10")) {
                        assertEquals(card.cardDamage("Deception", value), 10);
                    } else {
                        assertEquals(card.cardDamage("Deception", value), 5);
                    }
                }
                case "Merlin" -> assertEquals(card.cardDamage("Merlin", value), 25);
                default -> {
                    assertEquals(card.cardDamage("Apprentice", value), 5);
                    assertEquals(card.cardDamage("Alchemy", value), 5);
                }
            }
        }
    }

    @Test
    public void testToString() {
        String suit = "Swords";
        String number = "5";
        Card c = new Card(suit,number);
        assertEquals(c.toString(), "Swords 5");
    }
}

