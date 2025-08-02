package org.example;

import java.io.PrintWriter;
import java.util.*;

public class Main {

    private static int MAX_PLAYERS = 0;

    public static List<Player> players;
    public static List<Card> currentCardsPlayed;
    public static String currSuit;
    public static int leader_count;
    static int MAX_CARDS_PER_PLAYER = 12;
    static int MAX_MELEE = 12;
    static final int MAX_MERLIN = 3;
    static final int MAX_ALCHEMY = 15;
    static final int MAX_APPRENTICE = 2;
    static final String[] suits = { "Swords", "Arrows", "Sorcery", "Deception" };

    public static int processNumPlayers(Scanner input, PrintWriter output) {
        System.out.println("How many players? (3-5): ");
        int num_players = input.nextInt();
        if (num_players >= 3 && num_players <= 5) {
            return num_players;
        } else {
            output.println("Invalid amount of players");
            return 0;
        }
    }

    public static String processPlayerName(int playerNum, Scanner input, PrintWriter output) {
        System.out.println("Player " + (playerNum + 1) + " Name: ");
        String name = input.nextLine();
        if (!name.isEmpty()) {
            output.println("Player " + (playerNum + 1) + ": "+name);
            return name;
        }
        output.println("Invalid Name");
        return "";
    }

    public static boolean processPlayerHealth(Scanner input, PrintWriter output){
        System.out.println("Set Players Health: ");
        int playerHealth = input.nextInt();
        if(playerHealth > 0){
            for (Player player : players) {
                player.setHealth(playerHealth);
            }
            output.println("Players Health set to: "+playerHealth);
            return true;
        } else {
            output.println("Invalid Health");
            return false;
        }
    }

    public static List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>();

        String[] ranks = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" };

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }

        for (int i = 0; i < MAX_MERLIN; i++) {
            deck.add(new Card("Merlin", "Any"));
        }

        for (int i = 0; i < MAX_APPRENTICE; i++) {
            deck.add(new Card("Apprentice", "Any"));
        }

        for (int i = 1; i <= MAX_ALCHEMY; i++) {
            deck.add(new Card("Alchemy", Integer.toString(i)));
        }

        return deck;
    }
    public static void shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
    }

    public static void dealCards(Player player, List<Card> deck, int id) {
        for (int i = 0; i < MAX_CARDS_PER_PLAYER; i++) {
            deck.get(0).setPlayer(id);
            player.getHand().add(deck.remove(0));
        }
    }

    public static void calculateInjuryDamage(List<Player> players) {
        for(Player player : players) {
            int injuryDamage = 0;
            for(Card injuryCard : player.getInjuryDeck()) {
                injuryDamage = injuryDamage + injuryCard.cardDamage(injuryCard.getSuit(),
                        Integer.toString(injuryCard.getValue()));
            }
            int newHealth = player.getHealth() - injuryDamage;
            player.setHealth(newHealth);
            System.out.println("Player: "+player.getName()+ " currently has "+player.getHealth()+" health left");
        }
    }

    public static boolean haveLoser(List<Player> players) {
        for (Player player : players) {
            if (player.getHealth() <= 0) {
                return true;
            }
        }
        return false;
    }

    public static String findWinner(List<Player> players) {
        String winner = "";
        int winnerHealth = 0;
        for (Player player : players) {
            if (player.getHealth() > winnerHealth && player.getHealth() > 0) {
                winner = player.getName();
                winnerHealth = player.getHealth();
            }
            else if (winnerHealth == player.getHealth() && player.getHealth() > 0) {
                winner += " and "+ player.getName();
            }
        }
        if (!winner.isEmpty()){
            winner += " is the winner of the game";
        }
        else {
            return "There are no winners in the game";
        }
        return winner;
    }

    public static int findStartingLeader(List<Player> players,int currRound){
        return (currRound-1) % players.size();
    }

    public static List<Card> findPlayableCards(Player player, List<Card> playerHand, boolean leader, String currSuit) {
        List<Card> playableCards = new ArrayList<>();
        if(leader) {
            int count = 0;
            for (int i = 0 ; i < playerHand.size(); i++) {
                playerHand.get(i).setIndexHand(i);
                if (!playerHand.get(i).getSuit().equals("Alchemy")) {
                    count++;
                    playableCards.add(playerHand.get(i));
                }
            }
            if(count == 0) {
                playableCards = playerHand;
            }
        }
        else {
            if (currSuit.equals("Alchemy")){
               return playerHand;
            }
            int numCard = 0;
            for (int i = 0 ; i < playerHand.size(); i++) {
                playerHand.get(i).setIndexHand(i);
                if (playerHand.get(i).getSuit().equals(currSuit) ||
                        playerHand.get(i).getSuit().equals("Merlin") ||
                        playerHand.get(i).getSuit().equals("Apprentice")) {
                    playableCards.add(playerHand.get(i));
                    numCard++;
                }
            }
            for (Card card : playerHand) {
                if (card.getSuit().equals("Alchemy") && numCard == 0) {
                    playableCards.add(card);
                }
            }

            if (playableCards.isEmpty()) {
                Scanner inputDiscard = new Scanner(System.in);
                PrintWriter outputDiscard = new PrintWriter(System.out);
                int discardNum;
                do {
                    discardNum = processDiscardInput(playerHand.size(), inputDiscard, outputDiscard);
                    outputDiscard.flush();
                } while (discardNum == -1);
                discardCard(player, playerHand, discardNum);
            }
        }
        return playableCards;
    }

    public static Player findCurrPlayer(List<Player> players, int leader_count, int nextPlayer) {
        if (leader_count + nextPlayer >= players.size()){
            return players.get((nextPlayer+leader_count) - players.size());
        } else {
            return players.get(leader_count + nextPlayer);
        }
    }

    public static int processPlayableCardInput(List<Card> playableCards, Scanner input, PrintWriter output) {
        System.out.println("These are your playable cards: " + playableCards);
        System.out.println("Choose a card for this melee from 0-" + (playableCards.size() - 1 + ": "));
        int card_num = input.nextInt();
        if (card_num < playableCards.size() && card_num >= 0) {
            return card_num;
        } else {
            output.println("Invalid Input. Enter a number from 0-"+(playableCards.size() - 1));
        }
            return -1;
    }

    public static String ProcessCurrentSuit(String currSuit, Scanner input, PrintWriter output){
        if (currSuit.equals("Merlin") || currSuit.equals("Apprentice") || currSuit.isEmpty()) {
            System.out.println("Choose suit for this melee from 0-3: " + Arrays.toString(suits));
            int suit = input.nextInt();
            if (suit >= 0 && suit <= 3 ) {
                return suits[suit];
            } else {
                output.println("Invalid Input. Choose suit from 0-3");
                return "";
            }
        }
        return currSuit;
    }

    public static int ProcessValue(String currSuit, Scanner input, PrintWriter output){
        System.out.println("Choose value for " + currSuit + " from 1-15: ");
        int newVal = input.nextInt();
        if (newVal >= 1 && newVal <= 15) {
            return newVal;
        }
        output.println("Invalid Input. Enter a number from 1-15.");
        return -1;
    }

    public static int processDiscardInput(int playerHandSize, Scanner input, PrintWriter output) {
        System.out.println("There are no playable cards. Need to discard a card.");
        System.out.println("Choose a card for this melee from 0-" + (playerHandSize - 1 + ": "));
        int discard_num = input.nextInt();
        if (discard_num < playerHandSize && discard_num >= 0) {
            return discard_num;
        } else {
            output.println("Invalid Input");
            return -1;
        }
    }

    public static void discardCard(Player player, List<Card> playerHand, int discardCard) {
        System.out.println("Discarded card: "+ playerHand.get(discardCard));
        player.getHand().remove(discardCard);
        shamedPlayer(player);
    }

    public static void shamedPlayer(Player player) {
        System.out.println(player.getName() + " is shamed and will take 5 injury points");
        player.setHealth(player.getHealth() - 5);
    }

    public static boolean isShamedPlayerDead(Player shamed, List<Player> players) {
        if (shamed.getHealth() <= 0) {
            String winner = findWinner(players);
            System.out.println(winner);
            return true;
        }
        return false;
    }

    public static void addCurrentCardsPlayed(List<Card> currentCardsPlayed, Card playedCard) {
        currentCardsPlayed.add(playedCard);
    }

    public static void playedCard(Player player, Card playedCard) {
        player.getHand().remove(playedCard);
    }

    public static void removeSameValueCards(List<Card> currentCardsPlayed) {
        int sameCardValue = 0;
        for(int k = 0; k < 2; k++) {
            for (int i = 0; i < currentCardsPlayed.size(); i++) {
                System.out.println(currentCardsPlayed);
                if (currentCardsPlayed.get(i).getValue() == sameCardValue) {
                    currentCardsPlayed.remove(i);
                }
                for (int j = 1; j < currentCardsPlayed.size(); j++) {
                    if (currentCardsPlayed.get(i).getValue() == currentCardsPlayed.get(j).getValue() &&
                            currentCardsPlayed.get(i).getPlayerId() != currentCardsPlayed.get(j).getPlayerId()) {
                        sameCardValue = currentCardsPlayed.get(i).getValue();
                        currentCardsPlayed.remove(i);
                        currentCardsPlayed.remove(j - 1);
                        i = 0;
                    }
                }
            }
        }
    }

    public static boolean currentCardsEmpty(List<Card> currentCardsPlayed){
        return currentCardsPlayed.isEmpty();
    }
    public static Card findLoserMelee(List<Card> currentCardsPlayed) {
        System.out.println("Current cards left: "+currentCardsPlayed);
        Card lowestCard = currentCardsPlayed.get(0);
        if (currentCardsPlayed.size() > 1) {
            for (int i = 1; i < currentCardsPlayed.size(); i++) {
                if (currentCardsPlayed.get(i).getValue() < lowestCard.getValue()) {
                    lowestCard = currentCardsPlayed.get(i);
                }
            }
        }
        return lowestCard;
    }

    public static void addPlayerInjuryDeck(List<Player> players, List<Card> injuryDeck, Card lowestCard) {
        for (Card injuryCard : injuryDeck) {
            players.get(lowestCard.getPlayerId()).addInjuryDeck(injuryCard);
        }
    }

    public static int getLoserPlayerId(Card lowestCard) {
        return lowestCard.getPlayerId();
    }

    public static void resetAllPlayersInjuryDeck(List<Player> players) {
        for (Player player : players) {
            player.resetInjuryDeck();
        }
    }

    public static void playMelee(List<Player> players, int currRound) {
        leader_count = findStartingLeader(players, currRound);
        for(int m = 1; m <= MAX_MELEE; m++) {
            currentCardsPlayed = new ArrayList<>();
            System.out.println("\nMelee: " + m);
            System.out.println("Current leader is " + players.get(leader_count).getName());
            System.out.println("This is your hand: " + players.get(leader_count).getHand());
            List<Card> leaderPlayableCards = findPlayableCards(players.get(leader_count),
                    players.get(leader_count).getHand(), true, "");
            Scanner inputLeader = new Scanner(System.in);
            PrintWriter outputLeader = new PrintWriter(System.out);
            int card_num;
            do {
                card_num = processPlayableCardInput(leaderPlayableCards, inputLeader, outputLeader);
                outputLeader.flush();
            } while (card_num == -1);

            currSuit = leaderPlayableCards.get(card_num).getSuit();
            do {
                currSuit = ProcessCurrentSuit(currSuit, inputLeader, outputLeader);
                outputLeader.flush();
            } while (currSuit.isEmpty());

            int newVal;
            if (leaderPlayableCards.get(card_num).getSuit().equals("Merlin") || leaderPlayableCards.get(card_num).getSuit().equals("Apprentice")) {
                do {
                    newVal = ProcessValue(currSuit, inputLeader, outputLeader);
                    outputLeader.flush();
                } while (newVal == -1);
                players.get(leader_count).getHand().get(leaderPlayableCards.get(card_num).getIndexHand()).setValue(Integer.toString(newVal));
            }

            System.out.println("Suit will be " + currSuit);
            addCurrentCardsPlayed(currentCardsPlayed, players.get(leader_count).getHand().get(leaderPlayableCards.get(card_num).getIndexHand()));
            playedCard(players.get(leader_count), players.get(leader_count).getHand().get(leaderPlayableCards.get(card_num).getIndexHand()));

            Player currPlayer;
            for(int i = 1; i < players.size(); i++) {
                currPlayer = findCurrPlayer(players, leader_count, i);
                System.out.println("It is your turn: " + currPlayer.getName());
                System.out.println("This is your hand: " + currPlayer.getHand());
                List<Card> playableCards = findPlayableCards(currPlayer, currPlayer.getHand(), false, currSuit);

                if (isShamedPlayerDead(currPlayer, players)) {
                    System.exit(0);
                }

                if (!playableCards.isEmpty()) {
                    Scanner inputCurrPlayer = new Scanner(System.in);
                    PrintWriter outputCurrPlayer = new PrintWriter(System.out);
                    int currPlayerCard;
                    do {
                        currPlayerCard = processPlayableCardInput(playableCards, inputCurrPlayer, outputCurrPlayer);
                        outputCurrPlayer.flush();
                    } while (currPlayerCard == -1);

                    int newVal2;
                    if (playableCards.get(currPlayerCard).getSuit().equals("Merlin") || playableCards.get(currPlayerCard).getSuit().equals("Apprentice")) {
                        do {
                            newVal2 = ProcessValue(currSuit, inputCurrPlayer, outputCurrPlayer);
                            outputCurrPlayer.flush();
                        } while (newVal2 == -1);
                        currPlayer.getHand().get(playableCards.get(currPlayerCard).getIndexHand()).setValue(Integer.toString(newVal2));
                    }
                    addCurrentCardsPlayed(currentCardsPlayed, currPlayer.getHand().get(playableCards.get(currPlayerCard).getIndexHand()));
                    playedCard(currPlayer, currPlayer.getHand().get(playableCards.get(currPlayerCard).getIndexHand()));
                }
            }
            List<Card> injuryDeck = new ArrayList<>(currentCardsPlayed);
            System.out.println("These are the current cards played: "+currentCardsPlayed);
            removeSameValueCards(currentCardsPlayed);

            if(currentCardsEmpty(currentCardsPlayed)){
                System.out.println("All values are the same, no losers");
                break;
            }
            Card loserCard = findLoserMelee(currentCardsPlayed);
            addPlayerInjuryDeck(players,injuryDeck,loserCard);
            leader_count = getLoserPlayerId(loserCard);
            System.out.println("Loser is "+players.get(leader_count).getName()+" for this melee");
        }
    }

    public static void main(String[] args) {
        Scanner playerInput = new Scanner(System.in);
        PrintWriter playerOutput = new PrintWriter(System.out);
        do {
            MAX_PLAYERS = processNumPlayers(playerInput, playerOutput);
            playerOutput.flush();
        } while(MAX_PLAYERS == 0);
        players = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);
        for (int i = 0; i < MAX_PLAYERS; i++) {
            String name;
            do {
                name = processPlayerName(i, input, output);
                output.flush();
            } while(name.isEmpty());
            Player player = new Player(name);
            players.add(player);
        }

        boolean validHealth;
        do {
            validHealth = processPlayerHealth(input, output);
            output.flush();
        } while(!validHealth);

        boolean game_over = false;
        int round_count = 0;
        while (!game_over) {
            round_count++;
            System.out.println("\nRound: " + round_count);
            List<Card> deck = generateDeck();
            shuffleDeck(deck);
            for (int i = 0; i < players.size(); i++) {
                dealCards(players.get(i), deck, i);
            }
            playMelee(players, round_count);

            calculateInjuryDamage(players);
            game_over = (haveLoser(players));
            resetAllPlayersInjuryDeck(players);
        }

        String winner = findWinner(players);
        System.out.println(winner);
    }
}