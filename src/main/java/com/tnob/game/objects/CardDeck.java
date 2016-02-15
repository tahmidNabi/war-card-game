package com.tnob.game.objects;

import com.tnob.game.exception.CardExhaustedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a deck of 52 cards, 13 from each of the 4 suits.
 * @author tahmid
 * @since 2/13/16
 */
public class CardDeck {

    public static final int SUIT_SIZE = 13;

    private List<Card> cards;
    private int remainingCardCount;

    public CardDeck() {
        cards = new ArrayList<Card>(SUIT_SIZE * Suit.values().length);
        createCards();
    }

    private void createCards() {

        for (Suit suit : Suit.values()) {
            for (int rank = 1; rank <= SUIT_SIZE; rank++) {
                Card card = new Card(rank, suit);
                cards.add(card);
                ++remainingCardCount;
            }
        }
    }

    public int getSize() {
        return remainingCardCount;
    }

    /**
     * Shuffles the deck of cards. Used Collection framework's shuffle method, which provides an implementation of
     * the Fisher-Yates shuffle
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        remainingCardCount--;
        if (remainingCardCount < 0) throw new CardExhaustedException();
        return cards.get(remainingCardCount);
    }
}
