package com.tnob;

import com.tnob.game.entities.Player;
import com.tnob.game.objects.Card;
import com.tnob.game.objects.CardDeck;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        /*Card card = new Card(12, Suit.Hearts);

        System.out.println(card);*/

        Player playerOne = new Player("Bob");
        Player playerTwo = new Player("Tim");
        CardDeck cardDeck = new CardDeck();

        WarGame game = new WarGame(playerOne, playerTwo, cardDeck);
        game.dealCardsToPlayers();
        game.playGame();
        Player winner = game.declareWinner();
        System.out.println(winner.getName() + " won the game!");

    }


}
