package com.tnob.game.objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author tahmid
 * @since 2/13/16
 */


public class CardTest {

    static Card c1, c2, c3, c4, c5, c6;

    @BeforeClass
    public static void setup() {

        c1 = new Card(1, Suit.Clubs);
        c2 = new Card(1, Suit.Hearts);
        c3 = new Card(2, Suit.Clubs);
        c4 = new Card(5, Suit.Spades);
        c5 = new Card(1, Suit.Spades);
        c6 = new Card(11, Suit.Diamonds);
    }

    @Test
    public void testEquals() throws Exception {

        Assert.assertEquals(Boolean.TRUE, c1.equals(c2));
        Assert.assertEquals(Boolean.TRUE, c1.equals(c5));
        Assert.assertEquals(Boolean.FALSE, c1.equals(c3));
        Assert.assertEquals(Boolean.FALSE, c1.equals(c4));
    }

    @Test
    public void testCompareTo() throws Exception {

        Assert.assertEquals(0, c1.compareTo(c2));
        Assert.assertEquals(0, c1.compareTo(c5));
        Assert.assertEquals(3, c4.compareTo(c3));
        Assert.assertEquals(-6, c4.compareTo(c6));
        Assert.assertEquals(3, c1.compareTo(c6));
        Assert.assertEquals(-12, c3.compareTo(c1));

    }
}