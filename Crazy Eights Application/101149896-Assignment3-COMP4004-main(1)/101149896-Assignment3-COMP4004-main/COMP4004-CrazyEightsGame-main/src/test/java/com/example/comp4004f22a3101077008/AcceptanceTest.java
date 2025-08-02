package com.example.comp4004f22a3101077008;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
public class AcceptanceTest {
    @Autowired
    GameData gd;
    @Autowired
    GameLogic game;
    @LocalServerPort
    int port;
    List<WebDriver> drivers = new ArrayList<>();
    private final static int NUM_PLAYERS = 4;
    private static int DISPLAY_WIDTH = 2560/2;
    private static int DISPLAY_HEIGHT = 1440/2;

    @BeforeEach
    @DirtiesContext
    public void driverSetUp(){
        System.setProperty("webdriver.gecko.driver", "./src/geckodriver.exe");

        for(int i = 0; i < NUM_PLAYERS; i++) {
            WebDriver newDriver = new FirefoxDriver();
            drivers.add(newDriver);
            newDriver.get("http://localhost:"+port);

            int windowX;
            int windowY;
            if (i == 0){
                windowX = 0;
                windowY = 0;
            } else if (i == 1) {
                windowX = DISPLAY_WIDTH;
                windowY = 0;
            } else if (i == 2) {
                windowX = 0;
                windowY = DISPLAY_HEIGHT;
            }else{
                windowX = DISPLAY_WIDTH;
                windowY = DISPLAY_HEIGHT;
            }
            drivers.get(i).manage().window().setPosition(new org.openqa.selenium.Point(windowX, windowY));
            drivers.get(i).manage().window().setSize(new org.openqa.selenium.Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

            registerPlayer(newDriver, i);
        }
    }

    public void registerPlayer(WebDriver driver, int playerId){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Timeout duration
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameBtn")));
        button.click();
    }

    public void startGame(){
        WebDriverWait wait = new WebDriverWait(drivers.get(0), Duration.ofSeconds(20));
        WebElement startButton = wait.until(ExpectedConditions.elementToBeClickable((By.id("startBtn"))));
        startButton.click();
    }
    public Card convertStringToCard(String card){
        return new Card(card.substring(1,2), card.substring(0,1));
    }


    @AfterEach
    @DirtiesContext
    public void closeDrivers(){
        for(int i = 0; i < NUM_PLAYERS; i++) {
            drivers.get(i).quit();
        }
    }

    public void rigTestRow25(String topCard, String cardPlay){

        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (int i = 0; i < deckSplit.length; i++) {
            if(!deckSplit[i].equals(topCard) && !deckSplit[i].equals(cardPlay)){
                newDeck.add(convertStringToCard(deckSplit[i]));
            }
        }

        Card top = convertStringToCard(topCard);
        Card p1Card = convertStringToCard(cardPlay);
        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        gd.getCards().add(0, p1Card);
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow25() throws InterruptedException {
        rigTestRow25("4C", "3C");
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("4C",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("3C"));
        playCard.click();

        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("3C",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 1){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
    }

    public void rigTestRow27(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String p1CardPlay = cardPlay[0];
        String p4CardPlay = cardPlay[1];

        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard) && !s.equals(p1CardPlay) && !s.equals(p4CardPlay)) {
                newDeck.add(convertStringToCard(s));
            }
        }

        Card top = convertStringToCard(topCard);
        Card[] playerCards = new Card[cardPlay.length];
        for(int i = 0; i < cardPlay.length; i++){
            playerCards[i] = convertStringToCard(cardPlay[i]);
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.length; i++){
            if(i == 0) {
                gd.getCards().add(0, playerCards[i]);
            }
            else{
                gd.getCards().add(15, playerCards[i]);
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow27() throws InterruptedException {
        String[] playerCards = {"AH", "7H"};
        rigTestRow27("3H", playerCards);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("3H",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("AH"));
        playCard.click();

        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("AH",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 4",playerTurn);

            String direction = drivers.get(i).findElement(By.id("direction")).getText();
            assertEquals("right", direction);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 3){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }

        TimeUnit.SECONDS.sleep(1);
        WebElement playCard2 = drivers.get(3).findElement(By.id("7H"));
        playCard2.click();

        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("7H",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 3",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 2){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
    }

    public void rigTestRow28(String topCard, String cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";

        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard) && !s.equals(cardPlay)) {
                newDeck.add(convertStringToCard(s));
            }
        }

        Card top = convertStringToCard(topCard);
        Card p1Card = convertStringToCard(cardPlay);
        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        gd.getCards().add(0, p1Card);
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow28() throws InterruptedException {
        rigTestRow28("4C", "QC");
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("4C",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("QC"));
        playCard.click();

        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("QC",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 3",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 2){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
    }

    public void rigTestRow29(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String p1CardPlay = cardPlay[0];
        String p2CardPlay = cardPlay[1];
        String p3CardPlay = cardPlay[2];
        String p4CardPlay = cardPlay[3];

        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard) && !s.equals(p1CardPlay) &&
                    !s.equals(p2CardPlay) && !s.equals(p3CardPlay) &&
                    !s.equals(p4CardPlay)) {
                newDeck.add(convertStringToCard(s));
            }
        }

        Card top = convertStringToCard(topCard);
        Card[] playerCards = new Card[cardPlay.length];
        for(int i = 0; i < cardPlay.length; i++){
            playerCards[i] = convertStringToCard(cardPlay[i]);
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.length; i++){
            if(i == 0) {
                gd.getCards().add(0, playerCards[i]);
            }
            else if(i == 1) {
                gd.getCards().add(5, playerCards[i]);
            }
            else if(i == 2) {
                gd.getCards().add(10, playerCards[i]);
            }
            else{
                gd.getCards().add(15, playerCards[i]);
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow29() throws InterruptedException {
        String[] playerCards = {"5C", "6C", "7C", "3C"};
        rigTestRow29("4C", playerCards);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("4C",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("5C"));
        playCard.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("5C",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 1){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard2 = drivers.get(1).findElement(By.id("6C"));
        playCard2.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("6C",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 3",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 2){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard3 = drivers.get(2).findElement(By.id("7C"));
        playCard3.click();

        TimeUnit.SECONDS.sleep(2);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("7C",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 4",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 3){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard4 = drivers.get(3).findElement(By.id("3C"));
        playCard4.click();

        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("3C",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 1",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 0){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
    }

    public void rigTestRow31(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String p1CardPlay = cardPlay[0];
        String p2CardPlay = cardPlay[1];
        String p3CardPlay = cardPlay[2];
        String p4CardPlay = cardPlay[3];
        String p3CardPlayReverse = cardPlay[4];

        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard) && !s.equals(p1CardPlay) &&
                    !s.equals(p2CardPlay) && !s.equals(p3CardPlay) &&
                    !s.equals(p4CardPlay) && !s.equals(p3CardPlayReverse)) {
                newDeck.add(convertStringToCard(s));
            }
        }

        Card top = convertStringToCard(topCard);
        Card[] playerCards = new Card[cardPlay.length];
        for(int i = 0; i < cardPlay.length; i++){
            playerCards[i] = convertStringToCard(cardPlay[i]);
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());
        for(int i = 0; i < playerCards.length; i++){
            if(i == 0) {
                gd.getCards().add(0, playerCards[i]);
            }
            else if(i == 1) {
                gd.getCards().add(5, playerCards[i]);
            }
            else if(i == 2 || i == 4) {
                gd.getCards().add(10, playerCards[i]);
            }
            else{
                gd.getCards().add(15, playerCards[i]);
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow31() throws InterruptedException {
        String[] playerCards = {"5H", "6H", "3H", "AH", "7H"};
        rigTestRow31("4H", playerCards);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("4H",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("5H"));
        playCard.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("5H",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 1){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard2 = drivers.get(1).findElement(By.id("6H"));
        playCard2.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("6H",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 3",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 2){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard3 = drivers.get(2).findElement(By.id("3H"));
        playCard3.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("3H",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 4",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 3){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard4 = drivers.get(3).findElement(By.id("AH"));
        playCard4.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("AH",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 3",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 2){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard3Reverse = drivers.get(2).findElement(By.id("7H"));
        playCard3Reverse.click();

        TimeUnit.SECONDS.sleep(2);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("7H",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 1){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
    }

    public void rigTestRow32(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String p1CardPlay = cardPlay[0];
        String p2CardPlay = cardPlay[1];
        String p3CardPlay = cardPlay[2];
        String p4CardPlay = cardPlay[3];

        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard) && !s.equals(p1CardPlay) &&
                    !s.equals(p2CardPlay) && !s.equals(p3CardPlay) &&
                    !s.equals(p4CardPlay)) {
                newDeck.add(convertStringToCard(s));
            }
        }

        Card top = convertStringToCard(topCard);
        Card[] playerCards = new Card[cardPlay.length];
        for(int i = 0; i < cardPlay.length; i++){
            playerCards[i] = convertStringToCard(cardPlay[i]);
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.length; i++){
            if(i == 0) {
                gd.getCards().add(0, playerCards[i]);
            }
            else if(i == 1) {
                gd.getCards().add(5, playerCards[i]);
            }
            else if(i == 2) {
                gd.getCards().add(10, playerCards[i]);
            }
            else{
                gd.getCards().add(15, playerCards[i]);
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow32() throws InterruptedException {
        String[] playerCards = {"5C", "6C", "7C", "QC"};
        rigTestRow32("4C", playerCards);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("4C",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("5C"));
        playCard.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("5C",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 1){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard2 = drivers.get(1).findElement(By.id("6C"));
        playCard2.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("6C",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 3",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 2){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard3 = drivers.get(2).findElement(By.id("7C"));
        playCard3.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("7C",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 4",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 3){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
        WebElement playCard4 = drivers.get(3).findElement(By.id("QC"));
        playCard4.click();

        TimeUnit.SECONDS.sleep(2);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("QC",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2",playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if(i == 1){
                assertTrue(drawButton.isEnabled());
            }
            else {
                assertFalse(drawButton.isEnabled());
            }
        }
    }

    public void rigTestRow35(String topCard, String cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard) && !s.equals(cardPlay)) {
                newDeck.add(convertStringToCard(s));
            }
        }
        Card top = convertStringToCard(topCard);
        Card p1Card = convertStringToCard(cardPlay);
        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        gd.getCards().add(0, p1Card);
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }
    @Test
    @DirtiesContext
    public void testRow35() throws InterruptedException {
        rigTestRow35("KC", "KH");
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("KC",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("KH"));
        playCard.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("KH", topCard);
            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2", playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if (i == 1) {
                assertTrue(drawButton.isEnabled());
            } else {
                assertFalse(drawButton.isEnabled());
            }
        }
    }

    public void rigTestRow36(String topCard, String cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard) && !s.equals(cardPlay)) {
                newDeck.add(convertStringToCard(s));
            }
        }
        Card top = convertStringToCard(topCard);
        Card p1Card = convertStringToCard(cardPlay);
        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        gd.getCards().add(0, p1Card);
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow36() throws InterruptedException {
        rigTestRow36("KC", "7C");
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("KC",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("7C"));
        playCard.click();

        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals("7C",topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2", playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if (i == 1) {
                assertTrue(drawButton.isEnabled());
            } else {
                assertFalse(drawButton.isEnabled());
            }
        }
    }

    public void rigTestRow37(String topCard, String cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard) && !s.equals(cardPlay)) {
                newDeck.add(convertStringToCard(s));
            }
        }
        Card top = convertStringToCard(topCard);
        Card p1Card = convertStringToCard(cardPlay);
        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        gd.getCards().add(0, p1Card);
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow37() throws InterruptedException {
        rigTestRow37("KC", "8H");
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("KC",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("8H"));
        playCard.click();

        TimeUnit.SECONDS.sleep(1);
        for (WebDriver driver : drivers) {
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("8H", topCard);

            String playerTurn = driver.findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 1", playerTurn);
        }

        String[] suits = {"spade","heart","club","diamond"};
        for(String suit: suits) {
            WebElement played8 = drivers.get(0).findElement(By.id(suit));
            assertTrue(played8.isDisplayed());
            assertTrue(played8.isEnabled());
        }

    }

    public void rigTestRow38(String topCard, String cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard) && !s.equals(cardPlay)) {
                newDeck.add(convertStringToCard(s));
            }
        }
        Card top = convertStringToCard(topCard);
        Card p1Card = convertStringToCard(cardPlay);
        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        gd.getCards().add(0, p1Card);
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow38() throws InterruptedException {
        rigTestRow38("KC", "5S");
        startGame();
        TimeUnit.SECONDS.sleep(1);

        for(WebDriver driver: drivers){
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("KC",topCard);
        }
        WebElement playCard = drivers.get(0).findElement(By.id("5S"));
        playCard.click();

        String dismissMessage = drivers.get(0).switchTo().alert().getText();
        assertEquals(dismissMessage, "Invalid Selection");

        TimeUnit.SECONDS.sleep(1);
        drivers.get(0).switchTo().alert().dismiss();

        TimeUnit.SECONDS.sleep(1);
        for (WebDriver driver : drivers) {
            String topCard = driver.findElement(By.className("topCard")).getAttribute("id");
            assertEquals("KC", topCard);

            String playerTurn = driver.findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 1", playerTurn);
        }
    }



    public void rigTestRow42(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){

                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    public void checkTopCard(String top, int turn) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        for(int i = 0; i < drivers.size(); i++) {
            String topCard = drivers.get(i).findElement(By.className("topCard")).getAttribute("id");
            assertEquals(top,topCard);

            String playerTurn = drivers.get(i).findElement((By.id("turnID"))).getText();
            assertEquals("Turn: "+(turn+1), playerTurn);

            WebElement drawButton = drivers.get(i).findElement(By.id("draw"));
            if (i == turn) {
                assertTrue(drawButton.isEnabled());
            } else {
                assertFalse(drawButton.isEnabled());
            }
        }
    }

    public void checkDraw(int turn, String card){
        List<WebElement> pHand = drivers.get(turn).findElement(By.id("hand")).findElements(By.className("card"));

        for(WebElement pCard: pHand) {
            if(pCard.getAttribute("id").equals(card)) {
                assertTrue(pCard.isEnabled());
            } else {
                assertFalse(pCard.isEnabled());
            }
        }
    }

    public void verifyDrawnCard(int turn, String card){
        List<WebElement> pHand = drivers.get(turn).findElement(By.id("hand")).findElements(By.className("card"));

        int count = 0;
        for(WebElement pCard: pHand) {
            if(pCard.getAttribute("id").equals(card)) {
                count += 1;
            }
        }
        assertEquals(1, count);
    }

    @Test
    @DirtiesContext
    public void testRow42() throws InterruptedException {
        String[] cardPlay = {"6H","9D","6S","3C","3H",
                "5H","4D","5S","4C","QC",
                "4H","5D","4S","5C","AH",
                "9H","6D","3S","7C","AC",
                "6C"};

        rigTestRow42("TH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("TH", 0);
        drivers.get(0).findElement(By.id("6H")).click();
        checkTopCard("6H", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("4H")).click();
        checkTopCard("4H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("6D")).click();

        checkTopCard("6D", 0);
        drivers.get(0).findElement(By.id("6S")).click();
        checkTopCard("6S", 1);
        drivers.get(1).findElement(By.id("5S")).click();
        checkTopCard("5S", 2);
        drivers.get(2).findElement(By.id("4S")).click();
        checkTopCard("4S", 3);
        drivers.get(3).findElement(By.id("3S")).click();

        checkTopCard("3S", 0);
        drivers.get(0).findElement(By.id("3C")).click();
        checkTopCard("3C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("5C")).click();
        checkTopCard("5C", 3);
        drivers.get(3).findElement(By.id("7C")).click();

        WebElement drawButton = drivers.get(0).findElement(By.id("draw"));
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        checkDraw(0, "6C");
        verifyDrawnCard(0, "6C");
        drivers.get(0).findElement(By.id("6C")).click();
        checkTopCard("6C", 1);
    }

    public void rigTestRow43(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){

                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }
    @Test
    @DirtiesContext
    public void testRow43() throws InterruptedException {
        String[] cardPlay = {"6H","9D","6S","3C","3H",
                "5H","4D","5S","4C","QC",
                "4H","5D","4S","5C","AH",
                "9H","6D","3S","7C","AC",
                "5C","6D"};

        rigTestRow43("TH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("TH", 0);
        drivers.get(0).findElement(By.id("6H")).click();
        checkTopCard("6H", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("4H")).click();
        checkTopCard("4H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("6D")).click();

        checkTopCard("6D", 0);
        drivers.get(0).findElement(By.id("6S")).click();
        checkTopCard("6S", 1);
        drivers.get(1).findElement(By.id("5S")).click();
        checkTopCard("5S", 2);
        drivers.get(2).findElement(By.id("4S")).click();
        checkTopCard("4S", 3);
        drivers.get(3).findElement(By.id("3S")).click();

        checkTopCard("3S", 0);
        drivers.get(0).findElement(By.id("3C")).click();
        checkTopCard("3C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("5C")).click();
        checkTopCard("5C", 3);
        drivers.get(3).findElement(By.id("7C")).click();

        WebElement drawButton = drivers.get(0).findElement(By.id("draw"));
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        verifyDrawnCard(0,"6D");
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        checkDraw(0, "5C");
        verifyDrawnCard(0, "5C");
        drivers.get(0).findElement(By.id("5C")).click();
        checkTopCard("5C", 1);
    }

    public void rigTestRow44(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){

                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }
    @Test
    @DirtiesContext
    public void testRow44() throws InterruptedException {
        String[] cardPlay = {"6H","9D","6S","3C","3H",
                "5H","4D","5S","4C","QC",
                "4H","5D","4S","5C","AH",
                "9H","6D","3S","7C","AC",
                "7H","5S","6D"};

        rigTestRow44("TH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("TH", 0);
        drivers.get(0).findElement(By.id("6H")).click();
        checkTopCard("6H", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("4H")).click();
        checkTopCard("4H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("6D")).click();

        checkTopCard("6D", 0);
        drivers.get(0).findElement(By.id("6S")).click();
        checkTopCard("6S", 1);
        drivers.get(1).findElement(By.id("5S")).click();
        checkTopCard("5S", 2);
        drivers.get(2).findElement(By.id("4S")).click();
        checkTopCard("4S", 3);
        drivers.get(3).findElement(By.id("3S")).click();

        checkTopCard("3S", 0);
        drivers.get(0).findElement(By.id("3C")).click();
        checkTopCard("3C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("5C")).click();
        checkTopCard("5C", 3);
        drivers.get(3).findElement(By.id("7C")).click();

        WebElement drawButton = drivers.get(0).findElement(By.id("draw"));
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        verifyDrawnCard(0,"6D");
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        verifyDrawnCard(0,"5S");
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        checkDraw(0, "7H");
        verifyDrawnCard(0, "7H");
        drivers.get(0).findElement(By.id("7H")).click();
        checkTopCard("7H", 1);
    }

    public void rigTestRow45(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){

                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }
    @Test
    @DirtiesContext
    public void testRow45() throws InterruptedException {
        String[] cardPlay = {"6H","9D","6S","3C","3H",
                "5H","4D","5S","4C","QC",
                "4H","5D","4S","5C","AH",
                "9H","6D","3S","7C","AC",
                "4H","5S","6D"};

        rigTestRow45("TH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("TH", 0);
        drivers.get(0).findElement(By.id("6H")).click();
        checkTopCard("6H", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("4H")).click();
        checkTopCard("4H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("6D")).click();

        checkTopCard("6D", 0);
        drivers.get(0).findElement(By.id("6S")).click();
        checkTopCard("6S", 1);
        drivers.get(1).findElement(By.id("5S")).click();
        checkTopCard("5S", 2);
        drivers.get(2).findElement(By.id("4S")).click();
        checkTopCard("4S", 3);
        drivers.get(3).findElement(By.id("3S")).click();

        checkTopCard("3S", 0);
        drivers.get(0).findElement(By.id("3C")).click();
        checkTopCard("3C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("5C")).click();
        checkTopCard("5C", 3);
        drivers.get(3).findElement(By.id("7C")).click();

        WebElement drawButton = drivers.get(0).findElement(By.id("draw"));
        drawButton.click();
        verifyDrawnCard(0,"6D");
        TimeUnit.SECONDS.sleep(1);
        drawButton.click();
        verifyDrawnCard(0,"5S");
        TimeUnit.SECONDS.sleep(1);
        drawButton.click();
        verifyDrawnCard(0,"4H");
        TimeUnit.SECONDS.sleep(1);
        checkTopCard("7C", 1);
    }

    public void rigTestRow46(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){

                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }
    @Test
    @DirtiesContext
    public void testRow46() throws InterruptedException {
        String[] cardPlay = {"6H","9D","6S","3C","3H",
                "5H","4D","5S","4C","QC",
                "4H","5D","4S","5C","AH",
                "9H","6D","3S","7C","AC",
                "8H","6D"};

        rigTestRow46("TH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("TH", 0);
        drivers.get(0).findElement(By.id("6H")).click();
        checkTopCard("6H", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("4H")).click();
        checkTopCard("4H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("6D")).click();

        checkTopCard("6D", 0);
        drivers.get(0).findElement(By.id("6S")).click();
        checkTopCard("6S", 1);
        drivers.get(1).findElement(By.id("5S")).click();
        checkTopCard("5S", 2);
        drivers.get(2).findElement(By.id("4S")).click();
        checkTopCard("4S", 3);
        drivers.get(3).findElement(By.id("3S")).click();

        checkTopCard("3S", 0);
        drivers.get(0).findElement(By.id("3C")).click();
        checkTopCard("3C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("5C")).click();
        checkTopCard("5C", 3);
        drivers.get(3).findElement(By.id("7C")).click();

        WebElement drawButton = drivers.get(0).findElement(By.id("draw"));
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        verifyDrawnCard(0,"6D");
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        checkDraw(0, "8H");
        verifyDrawnCard(0, "8H");
        drivers.get(0).findElement(By.id("8H")).click();
        TimeUnit.SECONDS.sleep(1);
        drivers.get(0).findElement(By.id("heart")).click();
        TimeUnit.SECONDS.sleep(1);
        checkTopCard("H", 1);
    }

    public void rigTestRow47(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){

                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }
    @Test
    @DirtiesContext
    public void testRow47() throws InterruptedException {
        String[] cardPlay = {"6H","9D","6C","KS","3C",
                "5H","4D","5S","4C","QC",
                "4H","5D","4S","5C","AH",
                "9H","6D","3S","7C","AC",
                "6C"};

        rigTestRow47("TH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("TH", 0);
        drivers.get(0).findElement(By.id("6H")).click();
        checkTopCard("6H", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("4H")).click();
        checkTopCard("4H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("6D")).click();

        checkTopCard("6D", 0);
        drivers.get(0).findElement(By.id("6C")).click();
        checkTopCard("6C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("5C")).click();
        checkTopCard("5C", 3);
        drivers.get(3).findElement(By.id("7C")).click();

        WebElement drawButton = drivers.get(0).findElement(By.id("draw"));
        assertTrue(drawButton.isEnabled());
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        checkDraw(0, "6C");
        verifyDrawnCard(0, "6C");
        drivers.get(0).findElement(By.id("6C")).click();
        checkTopCard("6C", 1);
    }

    public void checkAddedCardsFrom2(int turn, String[] addedCards){
        List<WebElement> pHand = drivers.get(turn).findElement(By.id("hand")).findElements(By.className("card"));
        int count = 0;
        for(WebElement pCard: pHand){
                for(String card: addedCards){
                    if(pCard.getAttribute("id").equals(card)){
                        assertEquals(pCard.getAttribute("id"), card);
                        count++;
                    }
                }
        }
        assertEquals(count, addedCards.length);
    }

    public void rigTestRow51(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){

                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow51() throws InterruptedException {
        String[] cardPlay = {"6H","9D","6S","3C","2C",
                "5H","4D","QS","4C","4H",
                "7H","5D","4S","TC","AH",
                "9H","QD","3S","KC","AC",
                "6C","9D"};

        rigTestRow51("KH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("KH", 0);
        drivers.get(0).findElement(By.id("6H")).click();
        checkTopCard("6H", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("7H")).click();
        checkTopCard("7H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("QD")).click();

        checkTopCard("QD", 1);
        drivers.get(1).findElement(By.id("QS")).click();
        checkTopCard("QS", 3);
        drivers.get(3).findElement(By.id("3S")).click();

        checkTopCard("3S", 0);
        drivers.get(0).findElement(By.id("3C")).click();
        checkTopCard("3C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("TC")).click();
        checkTopCard("TC", 3);
        drivers.get(3).findElement(By.id("KC")).click();

        checkTopCard("KC", 0);
        drivers.get(0).findElement(By.id("2C")).click();
        checkTopCard("2C", 1);

        String[] addedCards = {"6C","9D"};
        checkAddedCardsFrom2(1, addedCards);

        drivers.get(1).findElement(By.id("6C")).click();
        TimeUnit.SECONDS.sleep(1);
        checkTopCard("6C", 2);
    }

    public void rigTestRow52(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){

                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow52() throws InterruptedException {
        String[] cardPlay = {"6H","9D","6S","3C","2C",
                "5H","4D","QS","4C","4H",
                "7H","5D","4S","TC","AH",
                "9H","QD","3S","KC","AC",
                "6C","9H","9D","6S"};

        rigTestRow52("KH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("KH", 0);
        drivers.get(0).findElement(By.id("6H")).click();
        checkTopCard("6H", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("7H")).click();
        checkTopCard("7H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("QD")).click();

        checkTopCard("QD", 1);
        drivers.get(1).findElement(By.id("QS")).click();
        checkTopCard("QS", 3);
        drivers.get(3).findElement(By.id("3S")).click();

        checkTopCard("3S", 0);
        drivers.get(0).findElement(By.id("3C")).click();
        checkTopCard("3C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("TC")).click();
        checkTopCard("TC", 3);
        drivers.get(3).findElement(By.id("KC")).click();

        checkTopCard("KC", 0);
        drivers.get(0).findElement(By.id("2C")).click();
        checkTopCard("2C", 1);

        List<WebElement> pHand = drivers.get(1).findElement(By.id("hand")).findElements(By.className("card"));
        for(WebElement pCard: pHand){
            drivers.get(1).findElement(By.id(pCard.getAttribute("id"))).click();
            String dismissMessage = drivers.get(1).switchTo().alert().getText();
            assertEquals(dismissMessage, "Invalid Selection");

            TimeUnit.SECONDS.sleep(1);
            drivers.get(1).switchTo().alert().dismiss();
        }

        String[] addedCards = {"6S","9D"};
        checkAddedCardsFrom2(1, addedCards);

        WebElement drawButton = drivers.get(1).findElement(By.id("draw"));
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        checkDraw(1, "6C");

        drivers.get(1).findElement(By.id("6C")).click();
        TimeUnit.SECONDS.sleep(1);
        checkTopCard("6C", 2);
    }

    public void rigTestRow53(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){
                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow53() throws InterruptedException {
        String[] cardPlay = {"6H","9D","6S","3C","2C",
                "5H","4D","QS","4C","4H",
                "7H","5D","4S","TC","AH",
                "9H","QD","3S","KC","AC",
                "9H","7S","9H","9D","6S"};

        rigTestRow53("KH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("KH", 0);
        drivers.get(0).findElement(By.id("6H")).click();
        checkTopCard("6H", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("7H")).click();
        checkTopCard("7H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("QD")).click();

        checkTopCard("QD", 1);
        drivers.get(1).findElement(By.id("QS")).click();
        checkTopCard("QS", 3);
        drivers.get(3).findElement(By.id("3S")).click();

        checkTopCard("3S", 0);
        drivers.get(0).findElement(By.id("3C")).click();
        checkTopCard("3C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("TC")).click();
        checkTopCard("TC", 3);
        drivers.get(3).findElement(By.id("KC")).click();

        checkTopCard("KC", 0);
        drivers.get(0).findElement(By.id("2C")).click();
        checkTopCard("2C", 1);

        String[] addedCards = {"6S","9D"};
        checkAddedCardsFrom2(1, addedCards);

        WebElement drawButton = drivers.get(1).findElement(By.id("draw"));
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        drawButton.click();
        TimeUnit.SECONDS.sleep(1);
        checkDraw(1, "2C");

        checkTopCard("2C", 2);
    }

    public void rigTestRow55(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){
                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow55() throws InterruptedException {
        String[] cardPlay = {"TH","9D","6S","3C","2C",
                "5H","4D","JD","4C","4H",
                "7H","5D","JS","TC","7D",
                "9H","QD","3S","KC","AC",
                "7C","6H","6D","5S","9D","2H"};

        rigTestRow55("KH", cardPlay);
        startGame();
        TimeUnit.SECONDS.sleep(1);

        checkTopCard("KH", 0);
        drivers.get(0).findElement(By.id("TH")).click();
        checkTopCard("TH", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("7H")).click();
        checkTopCard("7H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("QD")).click();

        checkTopCard("QD", 1);
        drivers.get(1).findElement(By.id("JD")).click();
        checkTopCard("JD", 2);
        drivers.get(2).findElement(By.id("JS")).click();
        checkTopCard("JS", 3);
        drivers.get(3).findElement(By.id("3S")).click();

        checkTopCard("3S", 0);
        drivers.get(0).findElement(By.id("3C")).click();
        checkTopCard("3C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 2);
        drivers.get(2).findElement(By.id("TC")).click();
        checkTopCard("TC", 3);
        drivers.get(3).findElement(By.id("KC")).click();


        checkTopCard("KC", 0);
        drivers.get(0).findElement(By.id("2C")).click();
        checkTopCard("2C", 1);
        String[] addedCards = {"2H","9D"};
        checkAddedCardsFrom2(1, addedCards);

        drivers.get(1).findElement(By.id("2H")).click();
        checkTopCard("2H", 2);

        String[] addedCards2 = {"7C","6H","6D","5S"};
        checkAddedCardsFrom2(2, addedCards2);

        drivers.get(2).findElement(By.id("6H")).click();
    }

    public void rigTestRow56(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){
                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow56() throws InterruptedException {
        String[] cardPlay = {"TH", "9D", "2C", "6S", "3C",
                "5H", "4D", "4C", "6C", "9D",
                "7H", "5D", "JS", "TC", "7D",
                "9H", "5C", "3S", "KC", "AC"};

        rigTestRow56("KH", cardPlay);
        startGame();

        checkTopCard("KH", 0);
        drivers.get(0).findElement(By.id("TH")).click();
        checkTopCard("TH", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("7H")).click();
        checkTopCard("7H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("5C")).click();

        checkTopCard("5C", 0);
        drivers.get(0).findElement(By.id("2C")).click();
        checkTopCard("2C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 1);
        drivers.get(1).findElement(By.id("6C")).click();
        checkTopCard("6C", 2);
    }

    public void rigTestRow57(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){
                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow57() throws InterruptedException {
        String[] cardPlay = {"TH", "9D", "6S", "2C", "3C",
                "5H", "4D", "9S", "4S", "4C",
                "7H", "5D", "JS", "TC", "7D",
                "9H", "5S", "JC", "KC", "AC"};

        rigTestRow57("KH", cardPlay);
        startGame();

        checkTopCard("KH", 0);
        drivers.get(0).findElement(By.id("TH")).click();
        checkTopCard("TH", 1);
        drivers.get(1).findElement(By.id("5H")).click();
        checkTopCard("5H", 2);
        drivers.get(2).findElement(By.id("7H")).click();
        checkTopCard("7H", 3);
        drivers.get(3).findElement(By.id("9H")).click();

        checkTopCard("9H", 0);
        drivers.get(0).findElement(By.id("9D")).click();
        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("4D")).click();
        checkTopCard("4D", 2);
        drivers.get(2).findElement(By.id("5D")).click();
        checkTopCard("5D", 3);
        drivers.get(3).findElement(By.id("5S")).click();

        checkTopCard("5S", 0);
        drivers.get(0).findElement(By.id("6S")).click();
        checkTopCard("6S", 1);
        drivers.get(1).findElement(By.id("9S")).click();
        checkTopCard("9S", 2);
        drivers.get(2).findElement(By.id("JS")).click();
        checkTopCard("JS", 3);
        drivers.get(3).findElement(By.id("JC")).click();

        checkTopCard("JC", 0);
        drivers.get(0).findElement(By.id("2C")).click();
        checkTopCard("2C", 1);
        drivers.get(1).findElement(By.id("4C")).click();
        checkTopCard("4C", 1);
        drivers.get(1).findElement(By.id("4S")).click();

        for(WebDriver driver: drivers){
            String playerTurn = driver.findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2", playerTurn);
            String scoreP1 = driver.findElement((By.id("p1"))).getText();
            assertEquals("Player 1: 3", scoreP1);
            String scoreP2 = driver.findElement((By.id("p2"))).getText();
            assertEquals("Player 2: 0", scoreP2);
            String scoreP3 = driver.findElement((By.id("p3"))).getText();
            assertEquals("Player 3: 17", scoreP3);
            String scoreP4 = driver.findElement((By.id("p4"))).getText();
            assertEquals("Player 4: 11", scoreP4);
        }
        WebElement p2StartButton = drivers.get(1).findElement(By.id("startBtn"));
        assertTrue(p2StartButton.isEnabled());
    }

    public void checkFinalScoreCards(int turn, String[] playerHand){
        List<WebElement> pHand = drivers.get(turn).findElement(By.id("hand")).findElements(By.className("card"));
        for(int i = 0; i< pHand.size(); i++) {
            assertEquals(pHand.get(i).getAttribute("id"), playerHand[i]);
        }
    }

    public void rigTestRow62(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){
                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow62() throws InterruptedException {
        String[] cardPlay = {"3H", "AC", "2S", "JS", "AS",
                "AH", "2C", "3S", "5S", "KS",
                "6S", "JH", "6H", "KH", "KS",
                "8C", "8D", "2D", "4S", "TS",
                "8H", "4C"};

        rigTestRow62("5H", cardPlay);
        startGame();

        checkTopCard("5H", 0);
        drivers.get(0).findElement(By.id("3H")).click();
        checkTopCard("3H", 1);
        drivers.get(1).findElement(By.id("AH")).click();
        checkTopCard("AH", 0);
        drivers.get(0).findElement(By.id("AC")).click();
        checkTopCard("AC", 1);
        drivers.get(1).findElement(By.id("2C")).click();

        checkTopCard("2C", 2);
        drivers.get(2).findElement(By.id("4C")).click();
        checkTopCard("4C", 3);
        drivers.get(3).findElement(By.id("4S")).click();

        checkTopCard("4S", 0);
        drivers.get(0).findElement(By.id("2S")).click();
        checkTopCard("2S", 1);
        drivers.get(1).findElement(By.id("3S")).click();
        checkTopCard("3S", 1);
        drivers.get(1).findElement(By.id("5S")).click();

        checkTopCard("5S", 2);
        drivers.get(2).findElement(By.id("6S")).click();
        checkTopCard("6S", 3);
        drivers.get(3).findElement(By.id("TS")).click();

        checkTopCard("TS", 0);
        drivers.get(0).findElement(By.id("JS")).click();
        checkTopCard("JS", 1);
        String[] p1Hand = {"AS"};
        String[] p3Hand = {"KS","KH","6H","JH","8H"};
        String[] p4Hand = {"2D","8D","8C"};

        checkFinalScoreCards(0, p1Hand);
        checkFinalScoreCards(2, p3Hand);
        checkFinalScoreCards(3, p4Hand);

        drivers.get(1).findElement(By.id("KS")).click();

        for(WebDriver driver: drivers){
            String playerTurn = driver.findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2", playerTurn);
            String winMsg = driver.findElement((By.id("winMSG"))).getText();
            assertEquals("WINNER IS PLAYER 2", winMsg);
            String scoreP1 = driver.findElement((By.id("p1"))).getText();
            assertEquals("Player 1: 1", scoreP1);
            String scoreP2 = driver.findElement((By.id("p2"))).getText();
            assertEquals("Player 2: 0", scoreP2);
            String scoreP3 = driver.findElement((By.id("p3"))).getText();
            assertEquals("Player 3: 86", scoreP3);
            String scoreP4 = driver.findElement((By.id("p4"))).getText();
            assertEquals("Player 4: 102", scoreP4);
        }
    }

    public void checkCannotPlay(int turn) {
        List<WebElement> pHand = drivers.get(turn).findElement(By.id("hand")).findElements(By.className("card"));
        for(WebElement pCard: pHand){
            drivers.get(turn).findElement(By.id(pCard.getAttribute("id"))).click();
            String dismissMessage = drivers.get(turn).switchTo().alert().getText();
            assertEquals(dismissMessage, "Invalid Selection");

            drivers.get(turn).switchTo().alert().dismiss();
        }
    }

    public void rigTestRow64(String topCard, String[] cardPlay){
        String deck = "AH 2H 3H 4H 5H 6H 7H 8H 9H TH JH QH KH " +
                "AS 2S 3S 4S 5S 6S 7S 8S 9S TS JS QS KS " +
                "AD 2D 3D 4D 5D 6D 7D 8D 9D TD JD QD KD " +
                "AC 2C 3C 4C 5C 6C 7C 8C 9C TC JC QC KC";
        String[] deckSplit = deck.split("\\s+");
        ArrayList<Card> newDeck = new ArrayList<>();
        for (String s : deckSplit) {
            if (!s.equals(topCard)) {
                for(String card : cardPlay) {
                    if(!card.equals(s)) {
                        newDeck.add(convertStringToCard(s));
                    }
                }
            }
        }
        Card top = convertStringToCard(topCard);
        ArrayList<Card> playerCards = new ArrayList<>();
        for(String card : cardPlay){
            playerCards.add(convertStringToCard(card));
        }

        gd.setCards(newDeck);
        game.shuffleDeck(gd.getCards());

        for(int i = 0; i < playerCards.size(); i++){
            if (i < 5) {
                gd.getCards().add(0, playerCards.get(i));
            } else if(i < 10) {
                gd.getCards().add(5, playerCards.get(i));
            } else if(i < 15) {
                gd.getCards().add(10, playerCards.get(i));
            } else if(i < 20){
                gd.getCards().add(15, playerCards.get(i));
            } else{
                gd.getCards().add(20, playerCards.get(i));
            }
        }
        gd.getCards().add(0, top);

        gd.setTopCard(game.startSetTopCard(gd.getCards()));

        for(Player p : gd.getPlayers()){
            game.startDealCards(gd.getCards(), gd.getPlayers(),p.getID() - 1);
        }
    }

    @Test
    @DirtiesContext
    public void testRow64() throws InterruptedException {
        String[] cardPlay = {"4H", "7S", "5D", "6D", "9D",
                "4S", "6S", "KC", "8H", "TD",
                "9S", "6C", "9C", "JD", "3H",
                "7D", "JH", "QH", "KH", "5C",
                "7C","JC","TC","4C","3C", "2C"};

        rigTestRow64("4D", cardPlay);
        startGame();

        checkTopCard("4D", 0);
        drivers.get(0).findElement(By.id("4H")).click();
        checkTopCard("4H", 1);
        drivers.get(1).findElement(By.id("4S")).click();
        checkTopCard("4S", 2);
        drivers.get(2).findElement(By.id("9S")).click();
        checkTopCard("9S", 3);

        WebElement drawButton4 = drivers.get(3).findElement(By.id("draw"));
        drawButton4.click();
        checkCannotPlay(3);
        verifyDrawnCard(3,"2C");
        drawButton4.click();
        checkCannotPlay(3);
        verifyDrawnCard(3,"3C");
        drawButton4.click();
        verifyDrawnCard(3,"4C");

        checkTopCard("9S", 0);
        drivers.get(0).findElement(By.id("7S")).click();
        checkTopCard("7S", 1);
        drivers.get(1).findElement(By.id("6S")).click();
        checkTopCard("6S", 2);
        drivers.get(2).findElement(By.id("6C")).click();
        checkTopCard("6C", 3);
        drivers.get(3).findElement(By.id("2C")).click();

        String[] addedCards = {"TC", "JC"};
        checkAddedCardsFrom2(0, addedCards);

        checkTopCard("2C", 0);
        drivers.get(0).findElement(By.id("JC")).click();
        checkTopCard("JC", 1);
        drivers.get(1).findElement(By.id("KC")).click();
        checkTopCard("KC", 2);
        drivers.get(2).findElement(By.id("9C")).click();
        checkTopCard("9C", 3);
        drivers.get(3).findElement(By.id("3C")).click();

        checkTopCard("3C", 0);
        WebElement drawButton1 = drivers.get(0).findElement(By.id("draw"));
        assertTrue(drawButton1.isEnabled());
        drawButton1.click();
        verifyDrawnCard(0, "7C");
        checkDraw(0,"7C");
        drivers.get(0).findElement(By.id("7C")).click();
        checkTopCard("7C", 1);
        drivers.get(1).findElement(By.id("8H")).click();
        TimeUnit.SECONDS.sleep(1);
        drivers.get(1).findElement(By.id("diamond")).click();

        checkTopCard("D", 2);
        drivers.get(2).findElement(By.id("JD")).click();
        checkTopCard("JD", 3);
        drivers.get(3).findElement(By.id("7D")).click();

        checkTopCard("7D", 0);
        drivers.get(0).findElement(By.id("9D")).click();

        String[] p1HandR1 = {"6D","5D","TC"};
        String[] p3HandR1 = {"3H"};
        String[] p4HandR1 = {"5C","KH","QH","JH","4C"};

        checkFinalScoreCards(0, p1HandR1);
        checkFinalScoreCards(2, p3HandR1);
        checkFinalScoreCards(3, p4HandR1);

        checkTopCard("9D", 1);
        drivers.get(1).findElement(By.id("TD")).click();

        for(WebDriver driver: drivers){
            String playerTurn = driver.findElement((By.id("turnID"))).getText();
            assertEquals("Turn: 2", playerTurn);
            String scoreP1 = driver.findElement((By.id("p1"))).getText();
            assertEquals("Player 1: 21", scoreP1);
            String scoreP2 = driver.findElement((By.id("p2"))).getText();
            assertEquals("Player 2: 0", scoreP2);
            String scoreP3 = driver.findElement((By.id("p3"))).getText();
            assertEquals("Player 3: 3", scoreP3);
            String scoreP4 = driver.findElement((By.id("p4"))).getText();
            assertEquals("Player 4: 39", scoreP4);
        }
        WebElement p2StartButton = drivers.get(1).findElement(By.id("startBtn"));
        assertTrue(p2StartButton.isEnabled());

        TimeUnit.SECONDS.sleep(1);

        String[] cardPlay2 = {"7D", "4S", "7C", "4H", "5D",
                "9D", "3S", "9C", "3H", "JC",
                "3D", "9S", "3C", "9H", "5H",
                "4D", "7S", "4C", "5S", "8D",
                "TS","JS","6S","JD","QD","6D","KH","QS","KS"};

        rigTestRow64("TD", cardPlay2);

        p2StartButton.click();

        checkTopCard("TD", 1);
        drivers.get(1).findElement(By.id("9D")).click();
        checkTopCard("9D", 2);
        drivers.get(2).findElement(By.id("3D")).click();
        checkTopCard("3D", 3);
        drivers.get(3).findElement(By.id("4D")).click();
        checkTopCard("4D", 0);
        drivers.get(0).findElement(By.id("4S")).click();

        checkTopCard("4S", 1);
        drivers.get(1).findElement(By.id("3S")).click();
        checkTopCard("3S", 2);
        drivers.get(2).findElement(By.id("9S")).click();
        checkTopCard("9S", 3);
        drivers.get(3).findElement(By.id("7S")).click();
        checkTopCard("7S", 0);
        drivers.get(0).findElement(By.id("7C")).click();

        checkTopCard("7C", 1);
        drivers.get(1).findElement(By.id("9C")).click();
        checkTopCard("9C", 2);
        drivers.get(2).findElement(By.id("3C")).click();
        checkTopCard("3C", 3);
        drivers.get(3).findElement(By.id("4C")).click();
        checkTopCard("4C", 0);
        drivers.get(0).findElement(By.id("4H")).click();

        checkTopCard("4H", 1);
        drivers.get(1).findElement(By.id("3H")).click();
        checkTopCard("3H", 2);
        drivers.get(2).findElement(By.id("9H")).click();
        checkTopCard("9H", 3);

        assertTrue(drawButton4.isEnabled());
        drawButton4.click();
        verifyDrawnCard(3, "KS");
        assertTrue(drawButton4.isEnabled());
        drawButton4.click();
        verifyDrawnCard(3, "QS");
        assertTrue(drawButton4.isEnabled());
        drawButton4.click();
        verifyDrawnCard(3, "KH");
        checkDraw(3,"KH");
        drivers.get(3).findElement(By.id("KH")).click();
        checkTopCard("KH", 0);

        drawButton1.click();
        checkCannotPlay(0);
        verifyDrawnCard(0, "6D");
        drawButton1.click();
        checkCannotPlay(0);
        verifyDrawnCard(0, "QD");
        drawButton1.click();
        verifyDrawnCard(0, "JD");

        checkTopCard("KH", 1);
        WebElement drawButton2 = drivers.get(1).findElement(By.id("draw"));
        drawButton2.click();
        verifyDrawnCard(1, "6S");
        checkCannotPlay(1);
        drawButton2.click();
        checkCannotPlay(1);
        verifyDrawnCard(1, "JS");
        drawButton2.click();
        verifyDrawnCard(1, "TS");

        String[] p1HandR2 = {"5D","7D","6D","QD","JD"};
        String[] p2HandR2 = {"JC","6S","JS","TS"};
        String[] p4HandR2 = {"8D","5S","KS","QS"};

        checkFinalScoreCards(0, p1HandR2);
        checkFinalScoreCards(1, p2HandR2);
        checkFinalScoreCards(3, p4HandR2);

        checkTopCard("KH", 2);
        drivers.get(2).findElement(By.id("5H")).click();

        for(WebDriver driver: drivers){
            String winMsg = driver.findElement((By.id("winMSG"))).getText();
            assertEquals("WINNER IS PLAYER 3", winMsg);
            String scoreP1 = driver.findElement((By.id("p1"))).getText();
            assertEquals("Player 1: 59", scoreP1);
            String scoreP2 = driver.findElement((By.id("p2"))).getText();
            assertEquals("Player 2: 36", scoreP2);
            String scoreP3 = driver.findElement((By.id("p3"))).getText();
            assertEquals("Player 3: 3", scoreP3);
            String scoreP4 = driver.findElement((By.id("p4"))).getText();
            assertEquals("Player 4: 114", scoreP4);
        }
    }
}
