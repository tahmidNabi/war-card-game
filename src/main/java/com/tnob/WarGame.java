package com.tnob;

import com.tnob.game.entities.Player;
import com.tnob.game.objects.Card;
import com.tnob.game.objects.CardDeck;
import com.tnob.game.objects.CardPile;

/**
 * Class for playing out a game of "War" between two players.
 * @author tahmid
 * @since 2/13/16
 */
public class WarGame {
    public static final int TURN_PAUSE_INTERVAL = 1000;
    private Player playerOne;
    private Player playerTwo;
    private CardDeck cardDeck;

    public static final int MAX_TURNS = 100;
    public static final int MIN_CARDS_FOR_ONE_TURN = 1;
    public static final int MIN_CARDS_FOR_ONE_WAR_ROUND = 2;

    public WarGame(Player playerOne, Player playerTwo, CardDeck cardDeck) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.cardDeck = cardDeck;
    }

    /**
     * Shuffle card deck and deal the cards to players.
     */
    public void dealCardsToPlayers() {
        cardDeck.shuffle();

        while (cardDeck.getSize() >= 2) {
            playerOne.acceptCard(cardDeck.deal());
            playerTwo.acceptCard(cardDeck.deal());

        }
    }

    /**
     * Method playing out the entire game. Each turn, the players play one card each. Player who played the
     * higher ranking card wins the turn and collects both cards. If the rank of cards played was the same,
     * the players engage in a "war". Game stops once one player no longer has enough cards to continue playing or max
     * number of turns is reached.
     */
    public void playGame() throws Exception{
        CardPile warPile = new CardPile();

        for (int turn = 1; turn <= MAX_TURNS; turn++) {
            if (!hasEnoughCards(MIN_CARDS_FOR_ONE_TURN)) {
                break;
            }
            boolean cardsExhausted = false;
            Card[] playedCards = playNormalTurn(turn);

            Card card1 = playedCards[0];
            Card card2 = playedCards[1];

            if (card1.equals(card2)) {//begin war
                initializeWarPile(warPile, card1, card2);
                cardsExhausted = doWar(warPile);
            } else {
                collectWonCards(card1, card2);
            }

            System.out.println("\t" + playerOne.getName() + " has " + playerOne.getCardCount() + " cards");
            System.out.println("\t" + playerTwo.getName() + " has " + playerTwo.getCardCount() + " cards");

            Thread.sleep(TURN_PAUSE_INTERVAL);
            if (cardsExhausted) {
                break;
            }
        }
    }

    /**
     * Player who played the card with the higher rank collects both cards
     * @param card1 card played by player 1
     * @param card2 card played by player 2
     */
    public void collectWonCards(Card card1, Card card2) {
        if (card1.compareTo(card2) > 0) {
            System.out.println("\t" + playerOne.getName() + " turned higher and collected " + card1 + " and " + card2 + "!");
            playerOne.collectWonCard(card1);
            playerOne.collectWonCard(card2);
        } else if (card1.compareTo(card2) < 0) {
            System.out.println("\t" + playerTwo.getName() + " turned higher and collected " + card1 + " and " + card2 + "!");
            playerTwo.collectWonCard(card1);
            playerTwo.collectWonCard(card2);
        }
    }

    /**
     * Plays out a "war" between two players. Each player plays two cards, the first one being "face down" (the rank and
     * suit remain unknown) and second one being "face up". The second card played are compared and a player playing
     * the card with higher rank wins all cards in the warpile. If the rank is the same, the "war" continues.
     * War is suspended if any one player does not have enough cards to continue playing.
     * @param warPile Pile collecting the cards played during the "war"
     * @return True if at least one player does not have enough cards to continue playing, false otherwise
     */
    public boolean doWar(CardPile warPile) {
        Card card1;
        Card card2;
        boolean warResolved = false;
        boolean cardsExhausted = false;

        do {
            if (!hasEnoughCards(MIN_CARDS_FOR_ONE_WAR_ROUND)) {
                cardsExhausted = true;
                break;
            }

            System.out.println("\tWar!");

            /**
             * playing the "face down" cards
             */
            warPile.addCard(playerOne.playCard());
            warPile.addCard(playerTwo.playCard());

            /**
             * playing the "face up" cards. These will be compared to check anyone has won the "war"
             */
            card1 = playerOne.playCard();
            card2 = playerTwo.playCard();
            warPile.addCard(card1);
            warPile.addCard(card2);

            System.out.println("\t" + playerOne.getName() + " played " + card1);
            System.out.println("\t" + playerTwo.getName() + " played " + card2);

            if (card1.compareTo(card2) > 0) {
                playerOne.collectWonPileFromWar(warPile);
                System.out.println("\t" + playerOne.getName() + " turned higher and collected all cards in the warpile!");
                warResolved = true;
            } else if (card1.compareTo(card2) < 0) {
                playerTwo.collectWonPileFromWar(warPile);
                System.out.println("\t" + playerTwo.getName() + " turned higher and collected all cards in the warpile!");
                warResolved = true;
            }


        } while (!warResolved);
        return cardsExhausted;
    }

    /**
     * Initialize the warpile for the "war".
     * @param warPile Pile collecting the cards played during the "war"
     * @param card1 Card played by playerOne
     * @param card2 Card played by playerTwo
     */
    public void initializeWarPile(CardPile warPile, Card card1, Card card2) {
        warPile.clear();
        warPile.addCard(card1);
        warPile.addCard(card2);
    }

    /**
     * A normal turn where the players each play the top card in their playing pile.
     * @param turn numerical value of the turn about to be played
     * @return Array containing cards played
     */

    public Card[] playNormalTurn(int turn) {
        Card [] playedCards = new Card[2];
        playedCards[0] = playerOne.playCard();
        playedCards[1] = playerTwo.playCard();


        System.out.println("Turn " + turn + ":");
        System.out.println("\t" + playerOne.getName() + " played " + playedCards[0]);
        System.out.println("\t" + playerTwo.getName() + " played " + playedCards[1]);
        return playedCards;
    }

    /**
     * Declare the player having the greater number of cards at the end of the game as the winner.
     */
    public Player declareWinner() {
        if (playerOne.getCardCount() > playerTwo.getCardCount()) {
            return playerOne;
        } else if (playerTwo.getCardCount() > playerOne.getCardCount()) {
            return playerTwo;
        }
        return null;
    }

    /**
     * Checks whether both players each have enough cards for the game to continue.
     * @param requiredCardNum the minimum number of cards each player must have for the game to continue
     * @return True if both players have enough cards for game to continue, False otherwise
     */
    public boolean hasEnoughCards(int requiredCardNum) {
        if (playerOne.getCardCount() < requiredCardNum || playerTwo.getCardCount() < requiredCardNum) {
            return false;
        }
        return true;
    }
}

