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

public class featurePart2 {

    Player p1;
    Player p2;
    Player p3;

    @Given("game set up Part2")
    public void gameSetup() {
        Main.players = new ArrayList<>();
    }

    @When("Enter invalid number of players")
    public void enterInvalidNumberOfPlayers() {
        String invalid_player_num = "1";
        StringWriter output = new StringWriter();
        if (Main.processNumPlayers(new Scanner(invalid_player_num), new PrintWriter(output)) == 0){
           System.out.println("Invalid Input");
        }
    }


    @And("Enter {int} as number of players")
    public void enterAsNumberOfPlayers(int numPlayers) {
        StringWriter output = new StringWriter();
        if (Main.processNumPlayers(new Scanner(String.valueOf(numPlayers)), new PrintWriter(output)) == 3){
            System.out.println("3 Players will playing the game");
        }
    }

    @And("Enter blank as name for 1st player")
    public void enterBlankAsNameForFirstPlayer() {
        String name = "\n";
        StringWriter output = new StringWriter();
        if(Main.processPlayerName(0, new Scanner(name), new PrintWriter(output)).isEmpty()){
            System.out.println("Invalid Name");
        }
    }

    @And("Enter {string} as name for 1st player")
    public void enterFredAsNameForFirstPlayer(String name) {
        StringWriter output = new StringWriter();
        if(Main.processPlayerName(0, new Scanner(name), new PrintWriter(output)).equals(name)){
            p1 = new Player(name);
            Main.players.add(p1);
            System.out.println("Player Name: "+name);
        }
    }

    @And("Enter blank as name for 2nd player")
    public void enterBlankAsNameForSecondPlayer() {
        String name = "\n";
        StringWriter output = new StringWriter();
        if(Main.processPlayerName(1, new Scanner(name), new PrintWriter(output)).isEmpty()){
            System.out.println("Invalid Name");
        }
    }

    @And("Enter {string} as name of 2nd player")
    public void enterJoeAsNameOfSecondPlayer(String name) {
        StringWriter output = new StringWriter();
        if(Main.processPlayerName(1, new Scanner(name), new PrintWriter(output)).equals(name)){
            p2 = new Player(name);
            Main.players.add(p2);
            System.out.println("Player Name: "+name);
        }
    }

    @And("Enter blank as name for 3rd player")
    public void enterBlankAsNameForThirdPlayer() {
        String name = "\n";
        StringWriter output = new StringWriter();
        if(Main.processPlayerName(2, new Scanner(name), new PrintWriter(output)).isEmpty()){
            System.out.println("Invalid Name");
        }
    }

    @And("Enter {string} as name for 3rd player")
    public void enterPaulAsNameForThirdPlayer(String name) {
        StringWriter output = new StringWriter();
        if(Main.processPlayerName(2, new Scanner(name), new PrintWriter(output)).equals(name)){
            p3 = new Player(name);
            Main.players.add(p3);
            System.out.println("Player Name: "+name);
        }
    }

    @And("Input initial health points as {int}")
    public void inputInitialHealthPointsAs(int health) {
        StringWriter output = new StringWriter();
        if(!Main.processPlayerHealth(new Scanner(String.valueOf(health)), new PrintWriter(output))){
            System.out.println("Invalid Health");
        }
    }

    @And("Set initial health points of players at {int}")
    public void setInitialHealthPointsOfPlayersAt(int health) {
        StringWriter output = new StringWriter();
        if(Main.processPlayerHealth(new Scanner(String.valueOf(health)), new PrintWriter(output))){
            System.out.println("Players Health Set to "+health);
        }
    }

    @Then("Round {int}: Distribute {int} cards to each player")
    public void roundDistributeCardsToEachPlayer(int roundNum, int numCards) {
        System.out.println("Round "+ roundNum+" stars");
        List<Card> deck = Main.generateDeck();
        Main.shuffleDeck(deck);
        for(int i = 0; i < Main.players.size(); i++) {
            Main.dealCards(Main.players.get(i), deck, i);
            assertEquals(12, Main.players.get(i).getHand().size());
        }
    }


    @Given("Rig Hands Part2")
    public void rigHandsPart2() {
        List<Card> player1Hand = new ArrayList<>();
        Card c1 = new Card("Sorcery","11");
        Card c8 = new Card("Apprentice","Any");
        Card c9 = new Card("Swords","3");
        Card c10 = new Card("Deception","9");
        Card c = new Card("Alchemy","6");
        Card c14 = new Card("Merlin","Any");
        player1Hand.add(c1);
        player1Hand.add(c8);
        player1Hand.add(c9);
        player1Hand.add(c10);
        player1Hand.add(c);
        player1Hand.add(c14);
        Main.players.get(0).setHand(player1Hand, 0);
        System.out.println("Rigging Hands for Player 1: "+Main.players.get(0).getHand());

        List<Card> player2Hand = new ArrayList<>();
        Card c6 = new Card("Sorcery","6");
        Card c4 = new Card("Arrows","8");
        Card c2 = new Card("Swords","9");
        Card c3 = new Card("Deception","6");
        Card c5 = new Card("Alchemy","7");
        player2Hand.add(c6);
        player2Hand.add(c4);
        player2Hand.add(c2);
        player2Hand.add(c3);
        player2Hand.add(c5);
        Main.players.get(1).setHand(player2Hand, 1);
        System.out.println("Rigging Hands for Player 2: "+Main.players.get(1).getHand());

        List<Card> player3Hand = new ArrayList<>();
        Card c7 = new Card("Sorcery","7");
        Card c11 = new Card("Merlin","Any");
        Card c12 = new Card("Swords","7");
        Card c13 = new Card("Deception","1");
        player3Hand.add(c7);
        player3Hand.add(c11);
        player3Hand.add(c12);
        player3Hand.add(c13);
        Main.players.get(2).setHand(player3Hand, 2);
        System.out.println("Rigging Hands for Player 3: "+Main.players.get(2).getHand());
    }

    @And("{string} tries to play an {string} when he has basic weapons and Merlin or Apprentice")
    public void fredTriesToPlayAnAlchemyWhenHeHasBasicWeaponsAndMerlinOrApprentice(String playerName, String suit) {
        System.out.println("Player: "+playerName+" turn");
        List<Card> playableCards = Main.findPlayableCards(Main.players.get(0), Main.players.get(0).getHand(), true, "");
        int count = 0;
        for(Card card : playableCards) {
            if(card.getSuit().equals(suit)){
                count++;
            }
        }
        if (count == 0) {
           System.out.println("Invalid playable card");
        }
    }

    @And("{string} plays {string}")
    public void playerPlays(String name, String card) {
        System.out.println("Player: "+name+"'s turn");
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

        for(Card cards : Main.players.get(playerIndex).getHand()) {
            if(cards.getValue() == Integer.parseInt(cardValue) && cards.getSuit().equals(cardSuit)) {
                Main.playedCard(Main.players.get(playerIndex), cards);
                Main.addCurrentCardsPlayed(Main.currentCardsPlayed, cards);
                System.out.println("Card Played: "+card);
                break;
            }
        }

    }

    @And("{string} tries to play a {string} card when he has a {string} card")
    public void joeTriesToPlayACardWhenHeHasACard(String name, String suit, String correctSuit) {
        System.out.println("Player: "+name+" turn");
        List<Card> playableCards = Main.findPlayableCards(Main.players.get(1), Main.players.get(1).getHand(), false, correctSuit);
        int count = 0;
        for(Card card : playableCards) {
            if(card.getSuit().equals(suit)){
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Invalid playable card");
        }
    }

    @And("{string} plays his {string}")
    public void PlayerPlaysHis(String name, String card) {
        System.out.println("Player: "+name+"'s turn");
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

        for(Card cards : Main.players.get(playerIndex).getHand()) {
            if(cards.getValue() == Integer.parseInt(cardValue) && cards.getSuit().equals(cardSuit)) {
                Main.playedCard(Main.players.get(playerIndex), cards);
                Main.addCurrentCardsPlayed(Main.currentCardsPlayed, cards);
                System.out.println("Card Played: "+card);
                break;
            }
        }
    }

    @And("{string} starts with {string}")
    public void joeStartsWith(String name, String card) {
        System.out.println("Player: "+name+"'s turn");
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

        if(cardSuit.equals("Merlin")) {
            System.out.println("Merlin is played at the start, need to choose a suit");
        }
        else {
            for (Card cards : Main.players.get(playerIndex).getHand()) {
                if (cards.getValue() == Integer.parseInt(cardValue) && cards.getSuit().equals(cardSuit)) {
                    Main.playedCard(Main.players.get(playerIndex), cards);
                    Main.addCurrentCardsPlayed(Main.currentCardsPlayed, cards);
                    System.out.println("Card Played: " + card);
                    break;
                }
            }
        }
    }

    @And("{string} plays {string} and tries to assign {int} to it")
    public void fredPlaysAndTriesToAssignToIt(String name, String card, int value) {
        System.out.println("Player: "+name+"'s turn");
        String[] cardSplit = card.split(" ");
        String cardSuit = cardSplit[0];
        StringWriter output = new StringWriter();
        if(cardSuit.equals("Merlin") || cardSuit.equals("Apprentice")) {
            if(Main.ProcessValue("Arrows", new Scanner(String.valueOf(value)), new PrintWriter(output)) == -1) {
                System.out.println("Invalid Input. Enter a number from 1-15.");
            }
        }
    }

    @And("{string} assigns {int} to {string}.")
    public void assignsTo(String name, int value, String suit) {
        System.out.println("Player: "+name+"'s turn");
        int playerIndex = 0;
        for(Player player : Main.players) {
            if(player.getName().equals(name)){
                break;
            }
            playerIndex++;
        }
        StringWriter output = new StringWriter();

        for(Card cards : Main.players.get(playerIndex).getHand()) {
            if(suit.equals("Merlin") || suit.equals("Apprentice")) {
                if(Main.ProcessValue(suit, new Scanner(String.valueOf(value)), new PrintWriter(output)) == value) {
                    cards.setValue(String.valueOf(value));
                    cards.setPlayer(playerIndex);
                    System.out.println(playerIndex);
                    Main.playedCard(Main.players.get(playerIndex), cards);
                    Main.addCurrentCardsPlayed(Main.currentCardsPlayed, cards);
                    System.out.println("Card Played: " + cards.toString());
                    break;
                }
            }
        }
    }

    @And("{string} plays {string} and tries to assign {int} to it.")
    public void playsAndTriesToAssignToIt(String name, String card, int value) {
        System.out.println("Player: "+name+"'s turn");
        String[] cardSplit = card.split(" ");
        String cardSuit = cardSplit[0];
        StringWriter output = new StringWriter();
        if(cardSuit.equals("Merlin") || cardSuit.equals("Apprentice")) {
            if(Main.ProcessValue("Arrows", new Scanner(String.valueOf(value)), new PrintWriter(output)) == -1) {
                System.out.println("Invalid Input. Enter a number from 1-15.");
            }
        }
    }

    @And("{string} tries to play an {string} when he has {string}")
    public void triesToPlayAnWhenHeHas(String playerName, String suit, String correctCard) {
        System.out.println("Player: "+playerName+" turn");
        List<Card> playableCards = Main.findPlayableCards(Main.players.get(0), Main.players.get(0).getHand(), true, "");
        int count = 0;
        for(Card card : playableCards) {
            if(card.getSuit().equals(suit)){
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Invalid playable card");
        }
    }

    @And("{string} tries to shame but can play {string}")
    public void triesToShameButCanPlay(String name, String card) {
        System.out.println("Player: "+name+" tries to shame");
        int playerIndex = 0;
        for(Player player : Main.players) {
            if(player.getName().equals(name)){
                break;
            }
            playerIndex++;
        }
        String[] cardSplit = card.split(" ");
        String cardSuit = cardSplit[0];

        Player player = Main.players.get(playerIndex);
        List<Card> playableCards = Main.findPlayableCards(player, player.getHand(), false, cardSuit);
        if(!playableCards.isEmpty()) {
            System.out.println("Player: "+name+" still has playable cards");
        }
    }
    @Then("{string} is the loser with {int} points of injury for this melee, with total injury points {int}")
    public void isTheLoserWithPointsOfInjuryForThisMeleeWithTotalInjuryPoints(String name, int injuryPoints, int totalInjury) {
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

        int totalInjuryDeck = 0;
        for(Card injuryCard : Main.players.get(Main.getLoserPlayerId(loserCard)).getInjuryDeck()) {
            totalInjuryDeck += injuryCard.cardDamage(injuryCard.getSuit(),
                    Integer.toString(injuryCard.getValue()));
        }
        assertEquals(totalInjuryDeck, totalInjury);
    }
}
