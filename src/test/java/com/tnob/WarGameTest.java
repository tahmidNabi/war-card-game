package com.tnob;

import com.tnob.game.entities.Player;
import com.tnob.game.objects.Card;
import com.tnob.game.objects.CardDeck;
import com.tnob.game.objects.CardPile;
import com.tnob.game.objects.Suit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tahmid on 2/14/16.
 */
public class WarGameTest {

    Player p1, p2;
    CardDeck cardDeck;
    WarGame game;

    @Before
    public void setup() {
        p1 = new Player("P1");
        p2 = new Player("P2");
        cardDeck = new CardDeck();
        game = new WarGame(p1, p2, cardDeck);
    }

    @Test
    public void testDealCardsToPlayers() throws Exception {
        game.dealCardsToPlayers();

        Assert.assertEquals(0, cardDeck.getSize());
        Assert.assertEquals(26, p1.getCardCount());
        Assert.assertEquals(26, p1.getCardCount());

    }

    @Test
    public void testCollectWonCards() throws Exception {
        Card playerOnePlayedCard = new Card(1, Suit.Clubs);
        Card playerTwoPlayedCard = new Card(2, Suit.Spades);

        game.collectWonCards(playerOnePlayedCard, playerTwoPlayedCard);

        Assert.assertEquals(2, p1.getCardCount());
        Assert.assertEquals(0, p2.getCardCount());

        playerOnePlayedCard = new Card(10, Suit.Diamonds);
        playerTwoPlayedCard = new Card(13, Suit.Hearts);

        game.collectWonCards(playerOnePlayedCard, playerTwoPlayedCard);

        Assert.assertEquals(2, p1.getCardCount());
        Assert.assertEquals(2, p2.getCardCount());
    }

    @Test
    public void testExhaustDuringWar() throws Exception {
        Card c1 = new Card(1, Suit.Clubs);
        Card c2 = new Card(2, Suit.Clubs);
        p1.acceptCard(c1);
        p2.acceptCard(c2);
        CardPile warPile = new CardPile();

        boolean isExhausted = game.doWar(warPile);

        Assert.assertEquals(Boolean.TRUE, isExhausted);
    }

    @Test
    public void testWinInWar() throws Exception {
        Card c1 = new Card(6, Suit.Diamonds);
        Card c2 = new Card(1, Suit.Hearts);
        Card c3 = new Card(2, Suit.Clubs);
        Card c4 = new Card(5, Suit.Spades);

        p1.acceptCard(c1);
        p1.acceptCard(c2);

        p2.acceptCard(c3);
        p2.acceptCard(c4);

        CardPile warPile = new CardPile();

        game.doWar(warPile);

        Assert.assertEquals(4, p1.getCardCount());

    }

    @Test
    public void testInitializeWarPile() throws Exception {
        Card c1 = new Card(1, Suit.Clubs);
        Card c2 = new Card(2, Suit.Clubs);
        CardPile warPile = new CardPile();

        game.initializeWarPile(warPile, c1, c2);

        Assert.assertEquals(2, warPile.getSize());
        Assert.assertEquals(Boolean.TRUE, warPile.getNextCard().equals(c1));
        Assert.assertEquals(Boolean.TRUE, warPile.getNextCard().equals(c2));


    }

    @Test
    public void testPlayNormalTurn() throws Exception {
        Card c1 = new Card(1, Suit.Clubs);
        Card c2 = new Card(2, Suit.Clubs);

        p1.acceptCard(c1);
        p2.acceptCard(c2);

        Card [] playedCards = game.playNormalTurn(1);
        Assert.assertEquals(2, playedCards.length);
        Assert.assertEquals(Boolean.TRUE, playedCards[0].equals(c1));
        Assert.assertEquals(Boolean.TRUE, playedCards[1].equals(c2));

    }

    @Test
    public void testDeclareWinner() throws Exception {

        Card c1 = new Card(1, Suit.Clubs);
        Card c2 = new Card(2, Suit.Clubs);
        Card c3 = new Card(3, Suit.Diamonds);

        p1.collectWonCard(c1);
        p1.collectWonCard(c2);
        p2.collectWonCard(c3);

        Player winner = game.declareWinner();

        Assert.assertEquals(Boolean.TRUE, winner.equals(p1));

    }

    @Test
    public void testHasEnoughCards() throws Exception {

        Card c1 = new Card(1, Suit.Clubs);
        Card c2 = new Card(2, Suit.Clubs);
        Card c3 = new Card(3, Suit.Diamonds);

        p1.acceptCard(c1);
        p1.acceptCard(c2);
        p2.acceptCard(c3);

        Assert.assertEquals(Boolean.TRUE, game.hasEnoughCards(1));
        Assert.assertEquals(Boolean.FALSE, game.hasEnoughCards(2));


    }
}