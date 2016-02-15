package com.tnob;

import com.tnob.game.entities.Player;
import com.tnob.game.objects.Card;
import com.tnob.game.objects.CardDeck;
import com.tnob.game.objects.CardPile;

/**
 * Class for playing out a game of "War" between two players.
 * Created by tahmid on 2/13/16.
 */
public class WarGame {
    private Player playerOne;
    private Player playerTwo;
    private CardDeck cardDeck;

    public WarGame(Player playerOne, Player playerTwo, CardDeck cardDeck) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.cardDeck = cardDeck;
    }

    /**
     * Shuffle card deck and deal the cards to players
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
     * the players engage in a "war". Game stops once one player no longer has enough cards to continue playing.
     *
     */
    public void playGame() {
        CardPile warPile = new CardPile();
        int turn = 1;
        while (true) {
            if (!hasEnoughCards(1)) {
                break;
            }
            boolean cardsExhausted = false;
            Card[] playedCards = playNormalTurn(turn);

            Card c1 = playedCards[0];
            Card c2 = playedCards[1];

            if (c1.equals(c2)) {//begin war
                initializeWarPile(warPile, c1, c2);
                cardsExhausted = doWar(warPile);
            } else {
                collectWonCards(c1, c2);
            }

            System.out.println();
            if (cardsExhausted) {
                System.out.println("Someone won all!");
                break;
            }
            ++turn;
        }
    }

    /**
     * Player who played the card with the higher rank collects both cards
     * @param c1 card played by player 1
     * @param c2 card played by player 2
     */
    public void collectWonCards(Card c1, Card c2) {
        if (c1.compareTo(c2) > 0) {
            System.out.println("\t" + playerOne.getName() + " turned higher and collected " + c1 + " and " + c2 + "!");
            playerOne.collectWonCard(c1);
            playerOne.collectWonCard(c2);
        } else if (c1.compareTo(c2) < 0) {
            System.out.println("\t" + playerTwo.getName() + " turned higher and collected " + c1 + " and " + c2 + "!");
            playerTwo.collectWonCard(c1);
            playerTwo.collectWonCard(c2);
        }
    }

    /**
     * Plays out a "war" between two players. Each player plays two cards, the first one being "face down" (the rank and suit
     * remain unknown) and second one being "face up". The second card played are compared and a player playing the card
     * with higher rank wins all cards in the warpile. If the rank is the same, the "war" continues. War is suspended
     * if any one player does not have enough cards to continue playing
     * @param warPile Pile collecting the cards played during the "war"
     * @return True if at least one player does not have enough
     * cards to continue playing, false otherwise
     */
    public boolean doWar(CardPile warPile) {
        Card c1;
        Card c2;
        boolean warResolved = false;
        boolean cardsExhausted = false;

        do {
            if (!hasEnoughCards(2)) {
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
            c1 = playerOne.playCard();
            c2 = playerTwo.playCard();
            warPile.addCard(c1);
            warPile.addCard(c2);

            System.out.println("\t" + playerOne.getName() + " played " + c1);
            System.out.println("\t" + playerTwo.getName() + " played " + c2);

            if (c1.compareTo(c2) > 0) {
                playerOne.collectWonPileFromWar(warPile);
                System.out.println("\t" + playerOne.getName() + " turned higher and collected all cards in the warpile!");
                warResolved = true;
            } else if (c1.compareTo(c2) < 0) {
                playerTwo.collectWonPileFromWar(warPile);
                System.out.println("\t" + playerTwo.getName() + " turned higher and collected all cards in the warpile!");
                warResolved = true;
            }


        } while (!warResolved);
        return cardsExhausted;
    }

    /**
     * Initialize the warpile for the "war"
     * @param warPile Pile collecting the cards played during the "war"
     * @param c1 Card played by playerOne
     * @param c2 Card played by playerTwo
     */
    public void initializeWarPile(CardPile warPile, Card c1, Card c2) {
        warPile.clear();
        warPile.addCard(c1);
        warPile.addCard(c2);
    }

    /**
     * A normal turn where the players each play the top card in their playing pile
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
     * Declare the player having the greater number of cards at the end of the game as the winner
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
     * Checkss whether both players each have enough cards for the game to continue
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

