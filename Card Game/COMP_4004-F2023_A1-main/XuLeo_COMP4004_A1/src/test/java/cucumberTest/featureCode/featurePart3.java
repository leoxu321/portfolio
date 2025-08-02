package cucumberTest.featureCode;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Card;
import org.example.Main;
import org.example.Player;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class featurePart3 {

    public void rigHand(Card card,int playerNum){
        List<Card> playerHand = new ArrayList<>();
        playerHand.add(card);
        Main.players.get(playerNum).setHand(playerHand, playerNum);
        System.out.println("Rigging Hands for Player "+playerNum+": " + Main.players.get(playerNum).getHand());
    }


    @Given("Game Set Up Part3")
    public void gameSetup() {
        Main.players = new ArrayList<>();
    }

    @When("Melee {int}")
    public void melee(int arg0) {
        Main.currentCardsPlayed = new ArrayList<>();
        System.out.println("Melee "+arg0+" has begun");
    }

    @Then("no one is the loser")
    public void noOneIsTheLoser() {
        Main.removeSameValueCards(Main.currentCardsPlayed);
        assertTrue(Main.currentCardsEmpty(Main.currentCardsPlayed));
        System.out.println("no one is the loser");
    }

    @Then("{string} is loser with {int} points of injury for this melee")
    public void isLoserWithPointsOfInjuryForThisMelee(String name, int injuryPoints) {
        List<Card> injuryDeck = new ArrayList<>(Main.currentCardsPlayed);
        System.out.println("These are the current cards played: "+Main.currentCardsPlayed);
        Main.removeSameValueCards(Main.currentCardsPlayed);

        if(Main.currentCardsEmpty(Main.currentCardsPlayed)){
            System.out.println("All values are the same, no losers");
        }

        Card loserCard = Main.findLoserMelee(Main.currentCardsPlayed);
        Main.addPlayerInjuryDeck(Main.players, injuryDeck, loserCard);
        System.out.println("Loser Card: "+Main.getLoserPlayerId(loserCard));

        int totalInjuryPoints = 0;
        for (Card injuryCard : injuryDeck) {
            totalInjuryPoints += injuryCard.cardDamage(injuryCard.getSuit(),
                    Integer.toString(injuryCard.getValue()));
        }

        assertEquals(name, Main.players.get(Main.getLoserPlayerId(loserCard)).getName());
        assertEquals(injuryPoints, totalInjuryPoints);
    }

    @And("Round {int}")
    public void round(int arg0) {
        System.out.println("Round "+arg0+" has started");
    }

    @Then("new leader is assigned in round {int}")
    public void newLeaderIsAssignedInRound(int arg0) {
        System.out.println("new leader for round 2 is "+ Main.players.get(Main.findStartingLeader(Main.players, arg0)).getName());
        assertEquals(Main.findStartingLeader(Main.players, arg0), 1);
    }

    @And("{string} tries to shame with no playable {string} cards")
    public void triesToShame(String name, String cardSuit) {
        System.out.println("Player: "+name+" tries to shame");
        int playerIndex = 0;
        for(Player player : Main.players) {
            if(player.getName().equals(name)){
                break;
            }
            playerIndex++;
        }
        Player player = Main.players.get(playerIndex);
        List<Card> playableCards = new ArrayList<>();
        for(Card cards : player.getHand()) {
            if (cards.getSuit().equals(cardSuit)) {
                playableCards.add(cards);
            }
        }

        if(playableCards.isEmpty()) {
            System.out.println("Player: "+name+" is being shamed");
        }
    }

    @And("{string} choose suit {string} for {string}")
    public void chooseSuit(String name, String suit, String currSuit) {
        StringWriter output = new StringWriter();
        String[] suits = { "Swords", "Arrows", "Sorcery", "Deception" };
        int count = 0;
        for(int i = 0; i < suits.length; i++) {
            if(suits[i].equals(suit)){
                count = i;
                break;
            }
        }
        String s = Main.ProcessCurrentSuit(currSuit, new Scanner(String.valueOf(count)), new PrintWriter(output));
        System.out.println(name+" chose this suit: "+s+" for this melee");
    }

    @And("{string} choose value {int} for {string}")
    public void chooseValue(String name, int value, String card) {
        int playerIndex = 0;
        for(Player player : Main.players) {
            if(player.getName().equals(name)){
                break;
            }
            playerIndex++;
        }
        StringWriter output = new StringWriter();
        for(Card cards : Main.players.get(playerIndex).getHand()) {
            if(cards.getSuit().equals(card)) {
                int val = Main.ProcessValue(card, new Scanner(String.valueOf(value)), new PrintWriter(output));
                cards.setValue(String.valueOf(val));
                Main.playedCard(Main.players.get(playerIndex), cards);
                Main.addCurrentCardsPlayed(Main.currentCardsPlayed, cards);
                System.out.println("Card Played: " + card);
                break;
            }
        }
    }

    @And("{string} chooses to discard {string}")
    public void choosesToDiscard(String name, String card) {
        System.out.println("Player: "+name+" chooses to discard a card");
        int playerIndex = 0;
        for(Player player : Main.players) {
            if(player.getName().equals(name)){
                break;
            }
            playerIndex++;
        }
        String[] cardSplit = card.split(" ");
        String cardSuit = cardSplit[0];
        String cardValue = cardSplit[1];

        int count = 0;
        for(Card cards : Main.players.get(playerIndex).getHand()) {
            if(cards.getValue() == Integer.parseInt(cardValue) && cards.getSuit().equals(cardSuit)) {
                break;
            }
            count++;
        }
        StringWriter output = new StringWriter();
        int discardCard = Main.processDiscardInput(Main.players.get(playerIndex).getHand().size(),
                new Scanner(String.valueOf(count)), new PrintWriter(output));
        Main.discardCard(Main.players.get(playerIndex), Main.players.get(playerIndex).getHand(), discardCard);
        System.out.println("Player: "+name+" discards "+ card);
    }

    @Then("{string} dies from the shame and game ends")
    public void diesFromTheShame(String name) {
        int playerIndex = 0;
        for(Player player : Main.players) {
            if(player.getName().equals(name)){
                break;
            }
            playerIndex++;
        }
        assertTrue(Main.isShamedPlayerDead(Main.players.get(playerIndex), Main.players));
        for(Player player: Main.players) {
            if(player.getName().equals("Fred") || player.getName().equals("Joe")) {
                assertEquals(player.getHealth(), 5);
            }
            else {
                assertEquals(player.getHealth(), 0);
            }
        }
    }

    @Then("{string} and {string} both win")
    public void andBothWin(String winner1, String winner2) {
        String winners = Main.findWinner(Main.players);
        assertTrue(winners.contains(winner1));
        assertTrue(winners.contains(winner2));
        assertEquals(winner1 + " and "+winner2+" is the winner of the game", winners);
        System.out.println(winners);
    }


    @And("Round end damage is calculated")
    public void roundEndDamageIsCalculated() {
        Main.calculateInjuryDamage(Main.players);
        Main.resetAllPlayersInjuryDeck(Main.players);
    }


    @Then("There are losers found with 0 or below health and {string} is the winner")
    public void thereAreLosersFoundWithOrBelowHealthAndIsTheWinner(String winner) {
        assertTrue(Main.haveLoser(Main.players));
        String findWinner = Main.findWinner(Main.players);
        String[] winnerSplit = findWinner.split(" ");
        String winner2 = winnerSplit[0];
        assertEquals(winner2, winner);
        assertEquals(winner+" is the winner of the game", findWinner);
        System.out.println(findWinner);
    }

    @And("Enter {string} as name for 4th player")
    public void enterAsNameForThPlayer(String name) {
        StringWriter output = new StringWriter();
        if(Main.processPlayerName(3, new Scanner(name), new PrintWriter(output)).equals(name)){
            Player p4 = new Player(name);
            Main.players.add(p4);
            System.out.println("Player Name: "+name);
        }
    }

    @When("Round {int} is over")
    public void roundIsOver(int arg0) {
        System.out.println("Round "+arg0+" is over");
    }

    @Then("game has no winners")
    public void gameHasNoWinners() {
        String winner = Main.findWinner(Main.players);
        assertEquals("There are no winners in the game", winner);
    }

    @Given("Rig Hands D")
    public void rigHandsD() {
        List<Card> player1Hand = new ArrayList<>();
        Card c = new Card("Sorcery", "3");
        Card c1 = new Card("Swords", "5");
        Card c6 = new Card("Deception", "4");
        player1Hand.add(c);
        player1Hand.add(c1);
        player1Hand.add(c6);
        Main.players.get(0).setHand(player1Hand, 0);
        System.out.println("Rigging Hands for Player 1: " + Main.players.get(0).getHand());

        List<Card> player2Hand = new ArrayList<>();
        Card c2 = new Card("Alchemy", "7");
        Card c3 = new Card("Alchemy", "5");
        Card c7 = new Card("Arrows", "10");
        player2Hand.add(c2);
        player2Hand.add(c3);
        player2Hand.add(c7);
        Main.players.get(1).setHand(player2Hand, 1);
        System.out.println("Rigging Hands for Player 2: " + Main.players.get(1).getHand());

        List<Card> player3Hand = new ArrayList<>();
        Card c4 = new Card("Sorcery", "11");
        Card c5 = new Card("Merlin", "Any");
        Card c8 = new Card("Alchemy", "10");
        player3Hand.add(c4);
        player3Hand.add(c5);
        player3Hand.add(c8);
        Main.players.get(2).setHand(player3Hand, 2);
        System.out.println("Rigging Hands for Player 3: " + Main.players.get(2).getHand());
    }

    @Given("Rig Hands Round {int}A")
    public void rigRoundA(int arg0) {
        if(arg0 == 1) {
            List<Card> player1Hand = new ArrayList<>();
            Card c = new Card("Sorcery", "3");
            Card c1 = new Card("Swords", "5");
            Card c9 = new Card("Deception", "15");
            player1Hand.add(c);
            player1Hand.add(c1);
            player1Hand.add(c9);
            Main.players.get(0).setHand(player1Hand, 0);
            System.out.println("Rigging Hands for Player 1: " + Main.players.get(0).getHand());

            List<Card> player2Hand = new ArrayList<>();
            Card c2 = new Card("Alchemy", "7");
            Card c3 = new Card("Alchemy", "5");
            Card c10 = new Card("Deception", "11");
            player2Hand.add(c2);
            player2Hand.add(c3);
            player2Hand.add(c10);
            Main.players.get(1).setHand(player2Hand, 1);
            System.out.println("Rigging Hands for Player 2: " + Main.players.get(1).getHand());

            List<Card> player3Hand = new ArrayList<>();
            Card c4 = new Card("Sorcery", "11");
            Card c5 = new Card("Merlin", "Any");
            Card c11 = new Card("Deception", "13");
            player3Hand.add(c4);
            player3Hand.add(c5);
            player3Hand.add(c11);
            Main.players.get(2).setHand(player3Hand, 2);
            System.out.println("Rigging Hands for Player 3: " + Main.players.get(2).getHand());
        }
        else if(arg0 == 2) {
            List<Card> player1Hand = new ArrayList<>();
            Card c = new Card("Sorcery", "10");
            Card c1 = new Card("Merlin", "Any");
            Card c9 = new Card("Deception", "3");
            player1Hand.add(c);
            player1Hand.add(c1);
            player1Hand.add(c9);
            Main.players.get(0).setHand(player1Hand, 0);
            System.out.println("Rigging Hands for Player 1: " + Main.players.get(0).getHand());

            List<Card> player2Hand = new ArrayList<>();
            Card c2 = new Card("Sorcery", "2");
            Card c10 = new Card("Deception", "11");
            Card c3 = new Card("Alchemy", "13");
            player2Hand.add(c2);
            player2Hand.add(c10);
            player2Hand.add(c3);
            Main.players.get(1).setHand(player2Hand, 1);
            System.out.println("Rigging Hands for Player 2: " + Main.players.get(1).getHand());

            List<Card> player3Hand = new ArrayList<>();
            Card c5 = new Card("Merlin", "Any");
            Card c11 = new Card("Deception", "3");
            Card c4 = new Card("Deception", "13");
            player3Hand.add(c5);
            player3Hand.add(c11);
            player3Hand.add(c4);
            Main.players.get(2).setHand(player3Hand, 2);
            System.out.println("Rigging Hands for Player 3: " + Main.players.get(2).getHand());
        }

    }

    @Given("Rig Hands B")
    public void rigHandsB() {
        List<Card> player1Hand = new ArrayList<>();
        Card c = new Card("Sorcery", "3");
        Card c1 = new Card("Merlin", "Any");
        player1Hand.add(c);
        player1Hand.add(c1);
        Main.players.get(0).setHand(player1Hand, 0);
        System.out.println("Rigging Hands for Player 1: " + Main.players.get(0).getHand());

        List<Card> player2Hand = new ArrayList<>();
        Card c2 = new Card("Alchemy", "7");
        Card c3 = new Card("Alchemy", "5");
        player2Hand.add(c2);
        player2Hand.add(c3);
        Main.players.get(1).setHand(player2Hand, 1);
        System.out.println("Rigging Hands for Player 2: " + Main.players.get(1).getHand());

        List<Card> player3Hand = new ArrayList<>();
        Card c4 = new Card("Swords", "5");
        Card c5 = new Card("Sorcery", "11");
        player3Hand.add(c4);
        player3Hand.add(c5);
        Main.players.get(2).setHand(player3Hand, 2);
        System.out.println("Rigging Hands for Player 3: " + Main.players.get(2).getHand());
    }

    @Given("Rig Hands C")
    public void rigHandsC() {
        List<Card> player1Hand = new ArrayList<>();
        Card c = new Card("Sorcery", "3");
        Card c1 = new Card("Merlin", "Any");
        player1Hand.add(c);
        player1Hand.add(c1);
        Main.players.get(0).setHand(player1Hand, 0);
        System.out.println("Rigging Hands for Player 1: " + Main.players.get(0).getHand());

        List<Card> player2Hand = new ArrayList<>();
        Card c2 = new Card("Alchemy", "7");
        Card c3 = new Card("Alchemy", "5");
        player2Hand.add(c2);
        player2Hand.add(c3);
        Main.players.get(1).setHand(player2Hand, 1);
        System.out.println("Rigging Hands for Player 2: " + Main.players.get(1).getHand());

        List<Card> player3Hand = new ArrayList<>();
        Card c4 = new Card("Swords", "5");
        Card c5 = new Card("Sorcery", "11");
        player3Hand.add(c4);
        player3Hand.add(c5);
        Main.players.get(2).setHand(player3Hand, 2);
        System.out.println("Rigging Hands for Player 3: " + Main.players.get(2).getHand());
    }

    @Given("Rig Hands E")
    public void rigHandsE() {
        List<Card> player1Hand = new ArrayList<>();
        Card c = new Card("Sorcery", "3");
        Card c1 = new Card("Swords", "5");
        Card c8 = new Card("Alchemy", "10");
        Card c9 = new Card("Arrows", "4");
        player1Hand.add(c);
        player1Hand.add(c1);
        player1Hand.add(c8);
        player1Hand.add(c9);
        Main.players.get(0).setHand(player1Hand, 0);
        System.out.println("Rigging Hands for Player 1: " + Main.players.get(0).getHand());

        List<Card> player2Hand = new ArrayList<>();
        Card c2 = new Card("Alchemy", "7");
        Card c3 = new Card("Deception", "5");
        Card c10 = new Card("Sorcery", "11");
        Card c11 = new Card("Arrows", "5");
        player2Hand.add(c2);
        player2Hand.add(c3);
        player2Hand.add(c10);
        player2Hand.add(c11);
        Main.players.get(1).setHand(player2Hand, 1);
        System.out.println("Rigging Hands for Player 2: " + Main.players.get(1).getHand());

        List<Card> player3Hand = new ArrayList<>();
        Card c4 = new Card("Arrows", "2");
        Card c5 = new Card("Merlin", "Any");
        Card c12 = new Card("Deception", "10");
        Card c13 = new Card("Arrows", "7");
        player3Hand.add(c4);
        player3Hand.add(c5);
        player3Hand.add(c12);
        player3Hand.add(c13);
        Main.players.get(2).setHand(player3Hand, 2);
        System.out.println("Rigging Hands for Player 3: " + Main.players.get(2).getHand());

        List<Card> player4Hand = new ArrayList<>();
        Card c6 = new Card("Sorcery", "5");
        Card c7 = new Card("Swords", "3");
        Card c14 = new Card("Merlin", "Any");
        Card c15 = new Card("Arrows", "10");
        player4Hand.add(c6);
        player4Hand.add(c7);
        player4Hand.add(c14);
        player4Hand.add(c15);
        Main.players.get(3).setHand(player4Hand, 3);
        System.out.println("Rigging Hands for Player 4: " + Main.players.get(3).getHand());
    }
}
