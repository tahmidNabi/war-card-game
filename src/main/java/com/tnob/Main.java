package com.tnob;

import com.tnob.game.entities.Player;
import com.tnob.game.objects.CardDeck;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        Player playerOne, playerTwo;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to a 2 player text-based version of the War Card game");
        System.out.println("Enter name of playerOne");
        playerOne = new Player(scanner.nextLine());

        System.out.println("Enter name of playerTwo");
        playerTwo = new Player(scanner.nextLine());

        System.out.println("The game will run for a maximum of 10,000 turns.\n If one player fails to win all " +
                "by this turn, the player with the higher number of cards\n at the end of " + WarGame.MAX_TURNS +" turns" +
                " will be declared the winner");

        Thread.sleep(2000);

        CardDeck cardDeck = new CardDeck();

        WarGame game = new WarGame(playerOne, playerTwo, cardDeck);
        game.dealCardsToPlayers();
        game.playGame();
        Player winner = game.declareWinner();
        if (winner != null) {
            System.out.println(winner.getName() + " won the game!");
        } else {
            System.out.println("The players battled valiantly to a tie!");
        }

    }


}
