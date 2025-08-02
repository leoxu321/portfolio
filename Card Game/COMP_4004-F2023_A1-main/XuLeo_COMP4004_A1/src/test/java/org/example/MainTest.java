package org.example;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    public void testAddPlayers() {
        String player_num = "4";
        String player_num2 = "7";
        StringWriter output = new StringWriter();

        assertEquals(4, Main.processNumPlayers(new Scanner(player_num), new PrintWriter(output)));
        assertEquals(0, Main.processNumPlayers(new Scanner(player_num2), new PrintWriter(output)));
    }

    @Test
    public void testPlayersName() {
        String name  = "John Doe";
        String name2 = "\n";
        StringWriter output = new StringWriter();

        assertEquals(name, Main.processPlayerName(0, new Scanner(name), new PrintWriter(output)));
        assertEquals("", Main.processPlayerName(1, new Scanner(name2), new PrintWriter(output)));
    }

    @Test
    public void testDeckGeneration() {
        List<Card> Swords = new ArrayList<>();
        List<Card> Arrows = new ArrayList<>();
        List<Card> Sorcery = new ArrayList<>();
        List<Card> Deception = new ArrayList<>();
        List<Card> Merlin = new ArrayList<>();
        List<Card> Apprentice = new ArrayList<>();
        List<Card> Alchemy = new ArrayList<>();

        List<Card> deck = Main.generateDeck();
        assertEquals(deck.size(), 80);

        for (int i = 0; i < deck.size(); i++) {
            if(deck.get(i).getSuit().equals("Swords")) {
                Swords.add(deck.get(i));
            }
            if(deck.get(i).getSuit().equals("Arrows")) {
                Arrows.add(deck.get(i));
            }
            if(deck.get(i).getSuit().equals("Sorcery")) {
                Sorcery.add(deck.get(i));
            }
            if(deck.get(i).getSuit().equals("Deception")) {
                Deception.add(deck.get(i));
            }
            if(deck.get(i).getSuit().equals("Merlin")) {
                Merlin.add(deck.get(i));
            }
            if(deck.get(i).getSuit().equals("Apprentice")) {
                Apprentice.add(deck.get(i));
            }
            if(deck.get(i).getSuit().equals("Alchemy")) {
                Alchemy.add(deck.get(i));
            }
        }

        assertEquals(Swords.size(), 15);
        assertEquals(Arrows.size(), 15);
        assertEquals(Sorcery.size(), 15);
        assertEquals(Deception.size(), 15);
        assertEquals(Merlin.size(), 3);
        assertEquals(Apprentice.size(), 2);
        assertEquals(Alchemy.size(), 15);
    }

    @Test
    public void testDeckGenerationValue() {
        int swordCount = 0;
        int arrowsCount = 0;
        int sorceryCount = 0;
        int deceptionCount = 0;
        int alchemyCount = 0;
        List<Card> deck = Main.generateDeck();

        for (Card card : deck) {
            if (card.getSuit().equals("Swords")) {
                swordCount++;
                assertEquals(card.getValue(), swordCount);
            }
            if (card.getSuit().equals("Arrows")) {
                arrowsCount++;
                assertEquals(card.getValue(), arrowsCount);
            }
            if (card.getSuit().equals("Sorcery")) {
                sorceryCount++;
                assertEquals(card.getValue(), sorceryCount);
            }
            if (card.getSuit().equals("Deception")) {
                deceptionCount++;
                assertEquals(card.getValue(), deceptionCount);
            }
            if (card.getSuit().equals("Alchemy")) {
                alchemyCount++;
                assertEquals(card.getValue(), alchemyCount);
            }
            if (card.getSuit().equals("Merlin") || card.getSuit().equals("Apprentice")) {
                assertEquals(card.getValue(), 16);
            }
        }
    }

    @Test
    public void testDeckShuffled() {
        List<Card> deck = Main.generateDeck();
        List<Card> shuffledDeck = Main.generateDeck();
        Main.shuffleDeck(shuffledDeck);
        int count = 0;
        for(int i = 0; i < deck.size(); i++) {
            if(deck.get(i) == shuffledDeck.get(i)){
                count++;
            }
        }
        assertFalse(count == deck.size());
    }

    @Test
    public void testDealCards() {
        Player p1 = new Player("John Doe");
        List<Card> deck = Main.generateDeck();
        List<Card> deck2 = Main.generateDeck();
        Main.dealCards(p1, deck, 1);

        for(int i = 0; i < Main.MAX_CARDS_PER_PLAYER; i++) {
            assertEquals(deck2.get(i).getSuit(), p1.getHand().get(i).getSuit());
            assertEquals(deck2.get(i).getValue(), p1.getHand().get(i).getValue());
            assertEquals(1, p1.getHand().get(i).getPlayerId());
        }
    }

    @Test
    public void testCalculateInjuryDamage() {
        Card c = new Card("Sword", "5");
        Card c2 = new Card("Merlin", "5");
        Player p1 = new Player("John Doe");
        Player p2 = new Player("Bob");
        p1.addInjuryDeck(c);
        p2.addInjuryDeck(c2);
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        Main.calculateInjuryDamage(players);
        assertEquals(95, p1.getHealth());
        assertEquals(75, p2.getHealth());
    }

    @Test
    public void testHaveLoser() {
        Player p1 = new Player("John Doe");
        Player p2 = new Player("Bob");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        assertFalse(Main.haveLoser(players));
        players.get(1).setHealth(0);
        assertTrue(Main.haveLoser(players));
    }

    @Test
    public void testFindWinner() {
        Player p1 = new Player("John Doe");
        Player p2 = new Player("Bob");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        assertEquals("John Doe and Bob is the winner of the game", Main.findWinner(players));
        players.get(1).setHealth(0);
        assertEquals("John Doe is the winner of the game", Main.findWinner(players));
        players.get(0).setHealth(0);
        assertEquals("There are no winners in the game", Main.findWinner(players));
    }

    @Test
    public void testStartingLeader() {
        Player p1 = new Player("John Doe");
        Player p2 = new Player("Bob");
        Player p3 = new Player("Bill");
        Player p4 = new Player("Bib");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        assertEquals( 0, Main.findStartingLeader(players, 1));
        assertEquals( 3, Main.findStartingLeader(players, 4));
        assertEquals( 1, Main.findStartingLeader(players, 10));
        assertEquals( 3, Main.findStartingLeader(players, 12));
    }

    @Test
    public void testPlayableCards() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        Card c3 = new Card("Alchemy", "5");
        Card c4 = new Card("Merlin", "Any");
        List<Card> playerHand = new ArrayList<>();
        playerHand.add(c);
        playerHand.add(c2);
        playerHand.add(c3);
        playerHand.add(c4);
        Player p1 = new Player("John Doe");

        List<Card> playableCardsExpected = new ArrayList<>();
        playableCardsExpected.add(c);
        playableCardsExpected.add(c2);
        playableCardsExpected.add(c4);
        List<Card> playableCardsActual = Main.findPlayableCards(p1, playerHand, true, "");
        assertEquals(playableCardsExpected.size(), playableCardsActual.size());
        for (int i = 0; i < playableCardsActual.size(); i++) {
            assertEquals(playableCardsExpected.get(i).getSuit(), playableCardsActual.get(i).getSuit());
            assertEquals(playableCardsExpected.get(i).getValue(), playableCardsActual.get(i).getValue());
        }
        List<Card> playableCardsExpected2 = new ArrayList<>();
        playableCardsExpected2.add(c);
        playableCardsExpected2.add(c4);
        List<Card> playableCardsActual2 = Main.findPlayableCards(p1, playerHand, false, "Swords");
        for (int j = 0; j < playableCardsActual2.size(); j++) {
            assertEquals(playableCardsExpected2.get(j).getSuit(), playableCardsActual2.get(j).getSuit());
            assertEquals(playableCardsExpected2.get(j).getValue(), playableCardsActual2.get(j).getValue());
        }
    }

    @Test
    public void testFindCurrentPlayer() {
        Player p1 = new Player("John Doe");
        Player p2 = new Player("Bob");
        Player p3 = new Player("Bill");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        assertEquals(p1, Main.findCurrPlayer(players, 0, 0));
        assertEquals(p1, Main.findCurrPlayer(players, 2, 1));
        assertEquals(p2, Main.findCurrPlayer(players, 2, 2));
    }

    @Test
    public void testPlayableCardsInput() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Merlin", "Any");
        List<Card> playableCards = new ArrayList<>();
        playableCards.add(c);
        playableCards.add(c2);

        String card_num = "0";
        String card_num2 = "3";
        StringWriter output = new StringWriter();
        assertEquals(0 , Main.processPlayableCardInput(playableCards, new Scanner(card_num), new PrintWriter(output)));
        assertEquals(-1, Main.processPlayableCardInput(playableCards, new Scanner(card_num2), new PrintWriter(output)));
    }

    @Test
    public void testCurrentSuitInput() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Merlin", "Any");
        String currSuit = "2";
        String currSuit2 = "4";

        String c_suit = c.getSuit();
        String c2_suit = c2.getSuit();
        StringWriter output = new StringWriter();
        assertEquals("Swords" , Main.ProcessCurrentSuit(c_suit, new Scanner(c_suit), new PrintWriter(output)));
        assertEquals("Sorcery" , Main.ProcessCurrentSuit(c2_suit, new Scanner(currSuit), new PrintWriter(output)));
        assertEquals("" , Main.ProcessCurrentSuit(c2_suit, new Scanner(currSuit2), new PrintWriter(output)));

    }

    @Test
    public void testValueInput() {
        Card c = new Card("Merlin", "Any");
        String newVal = "2";
        String newVal2 = "16";

        String c_suit = c.getSuit();
        StringWriter output = new StringWriter();
        assertEquals(2 , Main.ProcessValue(c_suit, new Scanner(newVal), new PrintWriter(output)));
        assertEquals(-1 , Main.ProcessValue(c_suit, new Scanner(newVal2), new PrintWriter(output)));
    }

    @Test
    public void testDiscardInput() {
        int playerHandSize = 5;

        String discard_num = "4";
        String discard_num2 = "7";
        StringWriter output = new StringWriter();
        assertEquals(4 , Main.processDiscardInput(playerHandSize, new Scanner(discard_num), new PrintWriter(output)));
        assertEquals(-1 , Main.processDiscardInput(playerHandSize, new Scanner(discard_num2), new PrintWriter(output)));
    }

    @Test
    public void testDiscardCard() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Merlin", "Any");
        List<Card> playerHand = new ArrayList<>();
        Player p1 = new Player("John Doe");
        p1.getHand().add(c);
        p1.getHand().add(c2);
        playerHand = p1.getHand();

        Main.discardCard(p1, playerHand, 0);
        assertEquals(1, p1.getHand().size());
        Main.discardCard(p1, playerHand, 0);
        assertTrue(p1.getHand().isEmpty());
    }

    @Test
    public void testShamedPlayerHealth() {
        Player p1 = new Player("John Doe");

        Main.shamedPlayer(p1);
        assertEquals(95, p1.getHealth());
    }

    @Test
    public void testShamedPlayerDead() {
        Player p1 = new Player("John Doe");
        Player p2 = new Player("Bob");
        Player p3 = new Player("Bill");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        List<Card> playerHand = new ArrayList<>();
        p1.getHand().add(c);
        p1.getHand().add(c2);

        playerHand = p1.getHand();

        p1.setHealth(5);

        Main.discardCard(p1, playerHand, 0);
        assertTrue(Main.isShamedPlayerDead(p1, players));
        assertFalse(Main.isShamedPlayerDead(p2, players));
    }

    @Test
    public void testCurrentCardsPlayed() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        Card c3 = new Card("Merlin", "Any");
        List<Card> currentCardsPlayed = new ArrayList<>();

        Main.addCurrentCardsPlayed(currentCardsPlayed, c);
        Main.addCurrentCardsPlayed(currentCardsPlayed, c2);
        Main.addCurrentCardsPlayed(currentCardsPlayed, c3);

        assertEquals(c , currentCardsPlayed.get(0));
        assertEquals(c2 , currentCardsPlayed.get(1));
        assertEquals(c3 , currentCardsPlayed.get(2));
    }

    @Test
    public void testPlayedCard() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        List<Card> currentCardsPlayed = new ArrayList<>();
        Player p1 = new Player("John Doe");
        p1.getHand().add(c);
        p1.getHand().add(c2);

        Main.addCurrentCardsPlayed(currentCardsPlayed, c);
        Main.addCurrentCardsPlayed(currentCardsPlayed, c2);

        Main.playedCard(p1, c);
        assertEquals(1, p1.getHand().size());
        Main.playedCard(p1, c2);
        assertTrue(p1.getHand().isEmpty());
    }

    @Test
    public void testRemovedSameValueCards() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        Card c3 = new Card("Merlin", "2");
        List<Card> cardsPlayed = new ArrayList<>();
        c.setPlayer(0);
        c2.setPlayer(1);
        c3.setPlayer(2);
        cardsPlayed.add(c);
        cardsPlayed.add(c2);
        cardsPlayed.add(c3);

        Main.removeSameValueCards(cardsPlayed);
        assertEquals(1 ,cardsPlayed.size());
        assertEquals(c3, cardsPlayed.get(0));
    }

    @Test
    public void testCurrentCardsEmpty() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        Card c3 = new Card("Merlin", "5");
        List<Card> cardsPlayed = new ArrayList<>();
        c.setPlayer(0);
        c2.setPlayer(1);
        c3.setPlayer(2);
        cardsPlayed.add(c);
        cardsPlayed.add(c2);
        cardsPlayed.add(c3);

        Main.removeSameValueCards(cardsPlayed);
        assertEquals(0 ,cardsPlayed.size());
        assertTrue(Main.currentCardsEmpty(cardsPlayed));
    }

    @Test
    public void testFindLosersMelee() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        Card c3 = new Card("Merlin", "2");
        List<Card> cardsPlayed = new ArrayList<>();
        c.setPlayer(0);
        c2.setPlayer(1);
        c3.setPlayer(2);
        cardsPlayed.add(c);
        cardsPlayed.add(c2);
        cardsPlayed.add(c3);

        Main.removeSameValueCards(cardsPlayed);
        assertEquals(1 ,cardsPlayed.size());
        assertEquals(c3, Main.findLoserMelee(cardsPlayed));
    }

    @Test
    public void testAddPlayerInjuryDeck() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        Card c3 = new Card("Merlin", "2");
        List<Card> cardsPlayed = new ArrayList<>();
        c.setPlayer(0);
        c2.setPlayer(1);
        c3.setPlayer(2);
        cardsPlayed.add(c);
        cardsPlayed.add(c2);
        cardsPlayed.add(c3);
        Player p1 = new Player("John Doe");
        Player p2 = new Player("Bob");
        Player p3 = new Player("Bill");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        List<Card> injuryDeck = new ArrayList<>(cardsPlayed);

        Main.removeSameValueCards(cardsPlayed);
        Card lowest = Main.findLoserMelee(cardsPlayed);
        Main.addPlayerInjuryDeck(players,injuryDeck,lowest);
        for(int i = 0; i < cardsPlayed.size(); i++) {
            assertSame(players.get(2).getInjuryDeck().get(i), injuryDeck.get(i));
        }
    }

    @Test
    public void testLoserPlayerId() {
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        Card c3 = new Card("Merlin", "2");
        List<Card> cardsPlayed = new ArrayList<>();
        c.setPlayer(0);
        c2.setPlayer(1);
        c3.setPlayer(2);
        cardsPlayed.add(c);
        cardsPlayed.add(c2);
        cardsPlayed.add(c3);
        Player p1 = new Player("John Doe");
        Player p2 = new Player("Bob");
        Player p3 = new Player("Bill");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Main.removeSameValueCards(cardsPlayed);
        Card lowest = Main.findLoserMelee(cardsPlayed);
        assertEquals(2, Main.getLoserPlayerId(lowest));
    }

    @Test
    public void testPlayerInjuryDeckReset() {
        Player p1 = new Player("John Doe");
        Player p2 = new Player("Bob");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Arrows", "5");
        p1.getInjuryDeck().add(c);
        p1.getInjuryDeck().add(c2);
        p2.getInjuryDeck().add(c);

        Main.resetAllPlayersInjuryDeck(players);
        assertTrue(p1.getInjuryDeck().isEmpty());
        assertTrue(p2.getInjuryDeck().isEmpty());
    }
}