package com.tnob.game.entities;

import com.tnob.game.objects.Card;
import com.tnob.game.objects.CardPile;
import com.tnob.game.objects.Suit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tahmid
 * @since 2/13/16
 */
public class PlayerTest {
    Player player;
    Card c1, c2;

    @Before
    public void setup() {
        player = new Player("Tahmid");
        c1 = new Card(1, Suit.Clubs);
        c2 = new Card(12, Suit.Hearts);
    }

    @Test
    public void testAcceptCard() throws Exception {
        player.acceptCard(c1);
        Assert.assertEquals(1, player.getCardCount());
        player.acceptCard(c2);
        Assert.assertEquals(2, player.getCardCount());

    }

    @Test
    public void testCollectWonCard() throws Exception {
        player.collectWonCard(c1);
        Card playedCard = player.playCard();
        Assert.assertEquals(Boolean.TRUE, playedCard.equals(c1));
    }

    @Test
    public void testCollectWonPileFromWar() throws Exception {
        CardPile wonCardPile = new CardPile();
        wonCardPile.addCard(c1);
        wonCardPile.addCard(c2);

        int pileSize = wonCardPile.getSize();

        int oldCount = player.getCardCount();

        player.collectWonPileFromWar(wonCardPile);

        int newCount = player.getCardCount();

        Assert.assertEquals(pileSize, newCount - oldCount);
        Assert.assertEquals(Boolean.TRUE, player.playCard().equals(c1));
        Assert.assertEquals(Boolean.TRUE, player.playCard().equals(c2));


    }

    @Test
    public void testPlayCard() throws Exception {
        player.acceptCard(c1);
        int oldCount = player.getCardCount();
        Card playedCard = player.playCard();
        int newCount = player.getCardCount();
        Assert.assertNotNull(playedCard);
        Assert.assertEquals(1, oldCount - newCount);
    }
}