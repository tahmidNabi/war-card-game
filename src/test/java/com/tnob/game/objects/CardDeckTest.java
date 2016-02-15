package com.tnob.game.objects;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by tahmid on 2/13/16.
 */
public class CardDeckTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetSize() throws Exception {
        CardDeck cardDeck = new CardDeck();
        Assert.assertEquals(52, cardDeck.getSize());
        cardDeck.shuffle();
        Assert.assertEquals(52, cardDeck.getSize());
        for (int i = 1; i <=10; i++) {
            cardDeck.deal();
        }
        Assert.assertEquals(42, cardDeck.getSize());
    }

    @Test
    public void testDeal() throws Exception {
        CardDeck cardDeck = new CardDeck();
        int size = cardDeck.getSize();

        for (int i = 0; i < size; i++) {
            Card card = cardDeck.deal();
            Assert.assertNotNull(card);
        }
        expectedException.expect(CardExhaustedException.class);
        cardDeck.deal();
    }
}