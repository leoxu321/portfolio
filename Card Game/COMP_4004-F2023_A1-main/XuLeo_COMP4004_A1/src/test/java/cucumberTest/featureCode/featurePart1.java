package cucumberTest.featureCode;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Card;
import org.example.Main;
import org.example.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class featurePart1 {
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    List<Card> injuryCards;
    String losers;

    public featurePart1() {
        Main.currentCardsPlayed = new ArrayList<>();
        Main.players = new ArrayList<>();
        injuryCards = new ArrayList<>();
    }

    @Given("game setup adding players")
    public void gameSetup() {
        p1 = new Player("p1");
        Main.players.add(p1);
        p2 = new Player("p2");
        Main.players.add(p2);
        p3 = new Player("p3");
        Main.players.add(p3);
        p4 = new Player("p4");
        Main.players.add(p4);
    }

    @When("First Player Card Played {string}")
    public void firstPlayerCardPlayed(String p1Card) {
        String[] values = p1Card.split(" ");
        String suit = values[0];
        String value = values[values.length-1];
        Card c = new Card(suit,value);
        c.setPlayer(0);
        Main.addCurrentCardsPlayed(Main.currentCardsPlayed, c);
        injuryCards.add(c);
    }

    @And("Second Player Card Played {string}")
    public void secondPlayerCardPlayed(String p2Card) {
        String[] values = p2Card.split(" ");
        String suit = values[0];
        String value = values[1];
        Card c = new Card(suit,value);
        c.setPlayer(1);
        Main.addCurrentCardsPlayed(Main.currentCardsPlayed, c);
        injuryCards.add(c);
    }

    @And("Third Player Card Played {string}")
    public void thirdPlayerCardPlayed(String p3Card) {
        String[] values = p3Card.split(" ");
        String suit = values[0];
        String value = values[1];
        Card c = new Card(suit,value);
        c.setPlayer(2);
        Main.addCurrentCardsPlayed(Main.currentCardsPlayed, c);
        injuryCards.add(c);
    }

    @And("Fourth Player Card Played {string}")
    public void fourthPlayerCardPlayed(String p4Card) {
        String[] values = p4Card.split(" ");
        String suit = values[0];
        String value = values[1];
        Card c = new Card(suit,value);
        c.setPlayer(3);
        Main.addCurrentCardsPlayed(Main.currentCardsPlayed, c);
        injuryCards.add(c);
    }

    @Then("player is a {string}")
    public void playerIsA(String loser) {
        Main.removeSameValueCards(Main.currentCardsPlayed);
        if(Main.currentCardsEmpty(Main.currentCardsPlayed)){
            losers = "-";
            assertEquals(loser, losers);
        } else {
            Card loserCard = Main.findLoserMelee(Main.currentCardsPlayed);
            Player loserPlayer = Main.players.get(Main.getLoserPlayerId(loserCard));
            losers = loserPlayer.getName();
            assertEquals(loser, losers);
        }
    }

    @And("They should get {int} points in that category")
    public void theyShouldGetInjuryPointsInThatCategory(int injuryPoints) {
        int totalInjuryPoints = 0;
        if(!losers.equals("-")){
            for (Card injuryCard : injuryCards) {
                totalInjuryPoints += injuryCard.cardDamage(injuryCard.getSuit(),
                        Integer.toString(injuryCard.getValue()));
            }
        }
        assertEquals(injuryPoints, totalInjuryPoints);
    }
}
