package com.tnob.game.entities;

import com.tnob.game.objects.Card;
import com.tnob.game.objects.CardPile;

/**
 * Class representing a Player playing the War card game
 * Has two piles of cards, one is playing pile, and one is pile of cards won from the opponent
 * Once the playing pile is exhausted, player makes the won pile the playing pile
 * Created by tahmid on 2/13/16.
 */
public class Player {
    private String name;
    private CardPile playingPile;
    private CardPile wonCardPile;

    public Player(String name) {
        this.name = name;
        playingPile = new CardPile();
        wonCardPile = new CardPile();
    }

    public String getName() {
        return name;
    }

    public void acceptCard(Card card) {
        playingPile.addCard(card);
    }

    public void collectWonCard(Card wonCard) {
        wonCardPile.addCard(wonCard);
    }

    public void collectWonPileFromWar(CardPile warPile) {
        wonCardPile.addCardsFromPile(warPile);
    }

    public void useWonCardPile() {
        playingPile.clear();
        playingPile.addCardsFromPile(wonCardPile);
        wonCardPile.clear();
    }

    public Card playCard() {
        if (playingPile.getSize() == 0) {
            useWonCardPile();
        }
        if (playingPile.getSize() > 0) {
            return playingPile.getNextCard();
        }
        return null;
    }

    public int getCardCount() {
        return playingPile.getSize() + wonCardPile.getSize();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player otherPlayer = (Player) obj;
            return name.equals(otherPlayer.getName());
        } else
            return false;
    }
}
