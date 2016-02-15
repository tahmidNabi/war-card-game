package com.tnob.game.objects;

import com.tnob.game.exception.CardExhaustedException;
import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 * @author tahmid
 * @since 2/13/16
 */
public class CardPileTest {

    static Card c1, c2, c3, c4, c5, c6;
    static CardPile cardPile;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {

        c1 = new Card(1, Suit.Clubs);
        c2 = new Card(1, Suit.Hearts);
        c3 = new Card(2, Suit.Clubs);
        c4 = new Card(5, Suit.Spades);
        c5 = new Card(1, Suit.Spades);
        c6 = new Card(11, Suit.Diamonds);

        cardPile = new CardPile();
    }

    @Test
    public void testClear() throws Exception {
        cardPile.clear();
        Assert.assertEquals(0, cardPile.getSize());
    }

    @Test
    public void testAddCard() throws Exception {
        cardPile.addCard(c1);
        Assert.assertEquals(1, cardPile.getSize());
        cardPile.addCard(c2);
        Assert.assertEquals(2, cardPile.getSize());
        cardPile.addCard(c3);
        Assert.assertEquals(3, cardPile.getSize());
        cardPile.addCard(c4);
        Assert.assertEquals(4, cardPile.getSize());
        cardPile.addCard(c5);
        Assert.assertEquals(5, cardPile.getSize());
        cardPile.addCard(c6);
        Assert.assertEquals(6, cardPile.getSize());
    }

    @Test
    public void testGetNextCard() throws Exception {
        cardPile.clear();
        expectedException.expect(CardExhaustedException.class);
        cardPile.getNextCard();

        cardPile.addCard(c1);
        Card nextCard = cardPile.getNextCard();

        Assert.assertEquals(Boolean.TRUE, c1.equals(nextCard));

        cardPile.addCard(c2);
        cardPile.addCard(c3);

        nextCard = cardPile.getNextCard();

        Assert.assertEquals(Boolean.TRUE, c3.equals(nextCard));

    }

    @Test
    public void testAddCardsFromPile() throws Exception {
        CardPile newPile = new CardPile();

        cardPile.addCard(c1);
        cardPile.addCard(c2);
        cardPile.addCard(c3);

        newPile.addCardsFromPile(cardPile);

        Assert.assertEquals(0, cardPile.getSize());
        Assert.assertEquals(3, newPile.getSize());

        Card nextCard = newPile.getNextCard();

        Assert.assertEquals(Boolean.TRUE, c1.equals(nextCard));

    }
}