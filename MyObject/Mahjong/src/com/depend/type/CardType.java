package com.depend.type;

import com.depend.Card;

import java.util.*;

public abstract class CardType {
    protected TreeSet<Card> cards;
    protected CardType(TreeSet<Card> cards) {
        this.cards = cards;
    }
    public abstract boolean isGreater(TreeSet<Card> cards);
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardType cardType = (CardType) o;
        return Objects.equals(cards, cardType.cards);
    }
    public static CardType forType(TreeSet<Card> cards) {
        try {
            if(cards.size() == 1)
                return new Single(cards);
            if(cards.size() == 2)
                return new Pare(cards);
        }
        catch (TypeException e) {}
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }
}
