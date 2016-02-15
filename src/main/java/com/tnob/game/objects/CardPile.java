package com.tnob.game.objects;

import com.tnob.game.exception.CardExhaustedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a pile of playing cards. New cards are added to the end of the pile.
 * Cards are retrieved from top of the pile.
 * @author tahmid
 * @since 2/13/16
 */
public class CardPile {
    private List<Card> pile;
    private int topOfPile;
    private int endOfPile;

    public CardPile() {
        pile = new ArrayList<Card>(CardDeck.SUIT_SIZE * Suit.values().length);
        topOfPile = 0;
        endOfPile = 0;
    }

    public int getSize() {
        if (topOfPile > endOfPile) {
            throw new CardExhaustedException();
        }
        return (endOfPile - topOfPile);
    }

    public void clear() {
        pile.clear();
        topOfPile = 0;
        endOfPile = 0;
    }

    public void addCard(Card card) {
        pile.add(endOfPile, card);
        endOfPile++;
    }

    public Card getNextCard() {
        if (topOfPile >= endOfPile) {
            throw new CardExhaustedException();
        }
        Card nextCard = pile.get(topOfPile);
        topOfPile++;
        return nextCard;
    }

    public void addCardsFromPile(CardPile cardPile) {
        while (cardPile.getSize() > 0) {
            addCard(cardPile.getNextCard());
        }
    }
}
