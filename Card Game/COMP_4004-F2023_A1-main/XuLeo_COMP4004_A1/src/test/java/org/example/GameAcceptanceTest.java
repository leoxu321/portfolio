package org.example;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GameAcceptanceTest {

    /*
    A-TEST 001:
    > User Enters: 2
    > User gets back "Invalid amount of players"
    > User Enters: 4
    > User Enters: John
    > User Enters: \n
    > User gets back "Invalid Name"
    > User Enters: Bob
    > User Enters: Bill
    > User Enters: Dan
    > User gets back "Player 1: John"
    > User gets back "Player 2: Bob"
    > User gets back "Player 3: Bill"
    > User gets back "Player 4: Dan"
     */
    @Test
    public void testCreatingPlayers(){
        String player_num = "2";
        String player_num2 = "4";
        String name  = "John";
        String falseName = "\n";
        String name2  = "Bob";
        String name3  = "Bill";
        String name4  = "Dan";
        StringWriter output = new StringWriter();
        assertEquals(0, Main.processNumPlayers(new Scanner(player_num), new PrintWriter(output)));
        assertTrue(output.toString().contains("Invalid amount of players"));

        assertEquals(4, Main.processNumPlayers(new Scanner(player_num2), new PrintWriter(output)));
        assertEquals(name, Main.processPlayerName(0, new Scanner(name), new PrintWriter(output)));

        assertEquals("", Main.processPlayerName(1, new Scanner(falseName), new PrintWriter(output)));
        assertTrue(output.toString().contains("Invalid Name"));

        assertEquals(name2, Main.processPlayerName(1, new Scanner(name2), new PrintWriter(output)));
        assertEquals(name3, Main.processPlayerName(2, new Scanner(name3), new PrintWriter(output)));
        assertEquals(name4, Main.processPlayerName(3, new Scanner(name4), new PrintWriter(output)));

        assertTrue(output.toString().contains("Player 1: "+ name));
        assertTrue(output.toString().contains("Player 2: "+ name2));
        assertTrue(output.toString().contains("Player 3: "+ name3));
        assertTrue(output.toString().contains("Player 4: "+ name4));
    }

    /*
    A-TEST 002:
    > User Enters: 3
    > User Enters: John
    > User Enters: Bob
    > User Enters: Bill
    > Deck Generated
    > Deck Shuffled
    > Player 1 John gets 12 weapon cards
    > Player 2 Bob gets 12 weapon cards
    > Player 3 Bill gets 12 weapon cards
     */
    @Test
    public void testDeckDealt(){
        Main.players = new ArrayList<>();
        Main.players.add(new Player("John"));
        Main.players.add(new Player("Bob"));
        Main.players.add(new Player("Bill"));
        List<Card> unshuffledDeck = Main.generateDeck();

        List<Card> deck = Main.generateDeck();
        assertEquals(deck.size(), 80);
        Main.shuffleDeck(deck);

        int count = 0;
        for(int i = 0; i < deck.size(); i++) {
            if(deck.get(i) == unshuffledDeck.get(i)){
                count++;
            }
        }
        assertFalse(count == deck.size());
        for (int i = 0; i < Main.players.size(); i++){
            Main.dealCards(Main.players.get(i), deck, i);
            assertEquals(12, Main.players.get(i).getHand().size());
        }
    }

    /*
    A-TEST 003:
    > Round 1: Player 1 John
    > Round 2: Player 2 Bob
    > Round 3: Player 3 Bill
    > Round 4: Player 1 John
    > ...
    > Round 12: Player 3 Bill
     */
    @Test
    public void testLeaderRounds(){
        Main.players = new ArrayList<>();
        Main.players.add(new Player("John"));
        Main.players.add(new Player("Bob"));
        Main.players.add(new Player("Bill"));

        int num_rounds = 12;
        for (int i = 0 ; i < num_rounds; i++){
            assertEquals(Main.players.get(i % Main.players.size()),
                    Main.players.get(Main.findStartingLeader(Main.players, i+1)));
        }

        // first round
        for(int i = 0; i < Main.players.size(); i++){
            assertEquals(Main.players.get(i)  ,
                    Main.findCurrPlayer(Main.players, Main.findStartingLeader(Main.players, 1), i));

        }
        // last round
        assertEquals(Main.players.get(2)  ,
                Main.findCurrPlayer(Main.players, Main.findStartingLeader(Main.players, 12), 0));
        assertEquals(Main.players.get(0)  ,
                Main.findCurrPlayer(Main.players, Main.findStartingLeader(Main.players, 12), 1));
        assertEquals(Main.players.get(1)  ,
                Main.findCurrPlayer(Main.players, Main.findStartingLeader(Main.players, 12), 2));
    }

    /*
    A-TEST 004:
    > Determined the suit of the melee by the first player/leader
    > All other players played cards matching current suit with exceptions to Merlin. Alchemy, and Apprentice
    > Played Card was removed from their hand
    > Removed all the same value cards from the current cards that were played
    > Determined which card is the loser
    > Added the player that lost with all the cards that were played in their injury deck
    > Leader is set to the new loser for the next melee
     */
    @Test
    public void testPlayMelee(){
        Main.players = new ArrayList<>();
        Main.players.add(new Player("John"));
        Main.players.add(new Player("Bob"));
        Main.players.add(new Player("Bill"));
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Merlin", "5");
        Card c3 = new Card("Alchemy", "2");
        Main.players.get(0).getHand().add(c);
        Main.players.get(1).getHand().add(c2);
        Main.players.get(2).getHand().add(c3);
        Main.currSuit = Main.players.get(0).getHand().get(0).getSuit();
        List<Card> currentCardsPlayed = new ArrayList<>();
        for(int i = 0; i < Main.players.size(); i++) {
            Main.players.get(i).getHand().get(0).setPlayer(i);
            if(Main.players.get(i).getHand().get(0).getSuit().equals(Main.currSuit)
                    || Main.players.get(i).getHand().get(0).getSuit().equals("Merlin")
                    || Main.players.get(i).getHand().get(0).getSuit().equals("Alchemy")
                    || Main.players.get(i).getHand().get(0).getSuit().equals("Apprentice")){
                Main.addCurrentCardsPlayed(currentCardsPlayed, Main.players.get(i).getHand().get(0));
                assertEquals(Main.players.get(i).getHand().get(0), currentCardsPlayed.get(i));
                Main.playedCard(Main.players.get(i), Main.players.get(i).getHand().get(0));
                assertTrue(Main.players.get(i).getHand().isEmpty());
            }
        }
        List<Card> injuryDeck = new ArrayList<>(currentCardsPlayed);
        Main.removeSameValueCards(currentCardsPlayed);
        assertEquals(1 , currentCardsPlayed.size());
        Card loserCard = Main.findLoserMelee(currentCardsPlayed);
        assertEquals(c3, Main.findLoserMelee(currentCardsPlayed));
        Main.addPlayerInjuryDeck(Main.players, injuryDeck, loserCard);
        for(int i = 0; i < currentCardsPlayed.size(); i++) {
            assertSame(Main.players.get(2).getInjuryDeck().get(i), injuryDeck.get(i));
        }
        Main.leader_count = Main.getLoserPlayerId(loserCard);
        assertEquals(Main.getLoserPlayerId(loserCard), Main.leader_count);
    }

    /*
        A-TEST 005:
        > Card Value is correct
        > Card Suit is correct
        > Card Damage is correct
    */
    @Test
    public void testCard(){
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Merlin", "Any");
        Card c3 = new Card("Swords", "6");

        assertEquals("Swords", c.getSuit());
        assertEquals(5, c.getValue());
        assertEquals("Merlin", c2.getSuit());
        assertEquals(16, c2.getValue());
        assertEquals("Swords", c3.getSuit());
        assertEquals(6, c3.getValue());
        assertEquals(5, c.cardDamage(c.getSuit(), Integer.toString(c.getValue())));
        assertEquals(25, c2.cardDamage(c2.getSuit(), Integer.toString(c2.getValue())));
        assertEquals(10, c3.cardDamage(c3.getSuit(), Integer.toString(c3.getValue())));
    }

    /*
        A-TEST 006:
        > Find the loser that is below or equal to 0 health
        > Find the winner(s) and print it out
    */
    @Test
    public void testGameEnd(){
        Main.players = new ArrayList<>();
        Main.players.add(new Player("John"));
        Main.players.add(new Player("Bob"));

        Main.players.get(1).setHealth(0);
        assertTrue(Main.haveLoser(Main.players));
        assertEquals("John is the winner of the game", Main.findWinner(Main.players));
        Main.players.get(1).setHealth(100);
        assertEquals("John and Bob is the winner of the game", Main.findWinner(Main.players));
    }

    /*
        A-TEST 007:
        > Leader has 2 cards, but only can play Merlin
        > Leader tries to play outside playable cards
        > Leader gets back invalid input
        > Leader plays a Merlin
        > Leader chooses invalid suit
        > Leader gets back invalid input for suit
        > Leader chooses suit for the match
        > Leader chooses invalid value for Merlin Card
        > Leader gets back invalid input for value
        > Leader chooses value of the Card
    */
    @Test
    public void testMerlinLeader(){
        Main.players = new ArrayList<>();
        Main.players.add(new Player("John"));
        Card c = new Card("Merlin", "Any");
        Card c2 = new Card("Alchemy", "5");
        Main.players.get(0).getHand().add(c);
        Main.players.get(0).getHand().add(c2);
        List<Card> playableCards = Main.findPlayableCards(Main.players.get(0), Main.players.get(0).getHand(), true, "");
        assertEquals(1, playableCards.size());
        String card_num = "0";
        String card_num2 = "3";
        StringWriter output = new StringWriter();
        Main.processPlayableCardInput(playableCards, new Scanner(card_num2), new PrintWriter(output));
        assertTrue(output.toString().contains("Invalid Input. Enter a number from 0-0"));
        assertEquals(0 , Main.processPlayableCardInput(playableCards, new Scanner(card_num), new PrintWriter(output)));
        String suit_num = "0";
        String suit_num2 = "4";
        Main.ProcessCurrentSuit(playableCards.get(0).getSuit(), new Scanner(suit_num2), new PrintWriter(output));
        assertTrue(output.toString().contains("Invalid Input. Choose suit from 0-3"));
        Main.ProcessCurrentSuit(playableCards.get(0).getSuit(), new Scanner(suit_num), new PrintWriter(output));
        String val = "5";
        String val2 = "0";
        Main.ProcessValue(playableCards.get(0).getSuit(), new Scanner(val2), new PrintWriter(output));
        assertTrue(output.toString().contains("Invalid Input. Enter a number from 1-15."));
        Main.ProcessValue(playableCards.get(0).getSuit(), new Scanner(val), new PrintWriter(output));
    }

    /*
        A-TEST 008:
        > Player has no playable cards
        > Player chooses to discard a card from hand
        > Player chooses invalid input to discard a card in hand
        > Player chooses a valid card to discard in hand
        > Card is discarded from Player's hand
        > Shamed person takes 5 injury points immediately
        > Shamed person dies
        > Winner is printed
    */
    @Test
    public void testShame(){
        Main.players = new ArrayList<>();
        Main.players.add(new Player("John"));
        Main.currSuit = "Swords";
        Card c = new Card("Arrows", "5");
        Main.players.get(0).getHand().add(c);
        Main.players.get(0).setHealth(5);
        String discard_num = "4";
        String discard_num2 = "0";
        StringWriter output = new StringWriter();
        assertEquals(-1 , Main.processDiscardInput(Main.players.get(0).getHand().size(), new Scanner(discard_num), new PrintWriter(output)));
        assertTrue(output.toString().contains("Invalid Input"));
        assertEquals(0 , Main.processDiscardInput(Main.players.get(0).getHand().size(), new Scanner(discard_num2), new PrintWriter(output)));
        Main.discardCard(Main.players.get(0), Main.players.get(0).getHand(), 0);
        assertTrue(Main.players.get(0).getHand().isEmpty());
        assertTrue(Main.isShamedPlayerDead(Main.players.get(0), Main.players));
        assertEquals("There are no winners in the game", Main.findWinner(Main.players));
    }

    /*
        A-TEST 009:
        > Player Loses round gets added injury cards from losing
        > Round ends and damage is calculated and taken away from player's health
        > Injury deck is reset for all players
    */
    @Test
    public void testRoundEnd() {
        Main.players = new ArrayList<>();
        Main.players.add(new Player("John"));
        Main.players.add(new Player("Bob"));
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Merlin", "5");
        Card lowestCard = new Card("Swords", "6");
        lowestCard.setPlayer(0);
        Main.players.get(0).addInjuryDeck(c);
        Main.players.get(0).addInjuryDeck(c2);
        Main.players.get(0).addInjuryDeck(lowestCard);
        Main.calculateInjuryDamage(Main.players);
        assertEquals(60, Main.players.get(0).getHealth());
        Main.resetAllPlayersInjuryDeck(Main.players);
        assertTrue(Main.players.get(0).getInjuryDeck().isEmpty());
    }

    /*
        A-TEST 010
        > Leader Plays the current suit
        > Print out the playable cards for the second player
        > 3rd player plays their card
        > Removes all the cards with the same value
        > No losers for the melee as all cards removed from cards played
     */
    @Test
    public void testCurrentPlayer() {
        Main.players = new ArrayList<>();
        Main.players.add(new Player("John"));
        Main.players.add(new Player("Bob"));
        Main.players.add(new Player("Bill"));
        Card c = new Card("Swords", "5");
        Card c2 = new Card("Merlin", "5");
        Card c3 = new Card("Alchemy", "5");
        Card c4 = new Card("Arrows", "5");
        Main.players.get(0).getHand().add(c);
        Main.players.get(1).getHand().add(c2);
        Main.players.get(1).getHand().add(c4);
        Main.players.get(2).getHand().add(c3);
        for(int i = 0; i < Main.players.size(); i++) {
            Main.players.get(i).getHand().get(0).setPlayer(i);
        }
        List<Card> currentCardsPlayed = new ArrayList<>();
        Main.addCurrentCardsPlayed(currentCardsPlayed, Main.players.get(0).getHand().get(0));
        Main.currSuit = Main.players.get(0).getHand().get(0).getSuit();
        assertEquals("Swords 5 is the current suit", Main.players.get(0).getHand().get(0).toString() + " is the current suit");
        List<Card> playableCardsExpected = new ArrayList<>();
        playableCardsExpected.add(c2);
        List<Card> playableCardsActual = Main.findPlayableCards(Main.players.get(1), Main.players.get(1).getHand(), false, Main.currSuit);
        assertEquals(playableCardsExpected.size(), playableCardsActual.size());
        Main.addCurrentCardsPlayed(currentCardsPlayed, Main.players.get(1).getHand().get(0));
        Main.addCurrentCardsPlayed(currentCardsPlayed, Main.players.get(2).getHand().get(0));
        Main.removeSameValueCards(currentCardsPlayed);
        assertTrue(Main.currentCardsEmpty(currentCardsPlayed));
    }
}
