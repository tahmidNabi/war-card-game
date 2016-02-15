package com.tnob.game.entities;

import com.tnob.game.objects.Card;
import com.tnob.game.objects.CardPile;

/**
 * Class representing a Player playing the War card game
 * Has two piles of cards, one is playing pile, and one is pile of cards won from the opponent
 * Once the playing pile is exhausted, player makes the won pile the playing pile.
 * @author tahmid
 * @since 2/13/16
 */
public class Player {
    private String name;
    private CardPile playingPile;
    private CardPile wonPile;

    public Player(String name) {
        this.name = name;
        playingPile = new CardPile();
        wonPile = new CardPile();
    }

    public String getName() {
        return name;
    }

    public void acceptCard(Card card) {
        playingPile.addCard(card);
    }

    public void collectWonCard(Card wonCard) {
        wonPile.addCard(wonCard);
    }

    public void collectWonPileFromWar(CardPile warPile) {
        wonPile.addCardsFromPile(warPile);
    }

    public void useWonCardPile() {
        playingPile.clear();
        playingPile.addCardsFromPile(wonPile);
        wonPile.clear();
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
        return playingPile.getSize() + wonPile.getSize();
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
