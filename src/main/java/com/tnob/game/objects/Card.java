package com.tnob.game.objects;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a Playing Card object. Each Card has a rank and a suit associated with.
 * 4 of the ranks have special values. A card with the rank of 1 is an "ACE", and is at a higher
 * value compared to other ranks.
 * Created by tahmid on 2/13/16.
 */
public class Card implements Comparable<Card> {

    private static final int ACE = 1;
    private static final int JACK = 11;
    private static final int QUEEN = 12;
    private static final int KING = 13;
    private static final int SPECIAL_ACE_RANK = 14;

    private static final Map<Integer, String> SPECIAL_CARDS;

    static {
        SPECIAL_CARDS = new HashMap<Integer, String>();
        SPECIAL_CARDS.put(ACE, "Ace");
        SPECIAL_CARDS.put(JACK, "Jack");
        SPECIAL_CARDS.put(QUEEN, "Queen");
        SPECIAL_CARDS.put(KING, "King");
    }

    private int rank;
    private Suit suit;

    public Card(int rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card otherCard = (Card) obj;
            return getRank() == otherCard.getRank();
        } else
            return false;
    }

    public int compareTo(Card otherCard) {
        int thisRank = (this.getRank() == ACE) ? SPECIAL_ACE_RANK : this.getRank();
        int otherRank = (otherCard.getRank() == ACE) ? SPECIAL_ACE_RANK : otherCard.getRank();
        return (thisRank - otherRank);
    }

    @Override
    public String toString() {
        String cardVal;

        if (SPECIAL_CARDS.containsKey(rank)) {
            cardVal = SPECIAL_CARDS.get(rank);
        } else {
            cardVal = String.valueOf(rank);
        }

        return cardVal + " of " + suit;
    }
}
