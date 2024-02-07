package com.depend.type;

import com.depend.Card;
import com.depend.Size;

import java.util.TreeSet;

public class Single extends CardType {
    private Card card;
    Single(TreeSet<Card> cards) {
        super(cards);
        if(cards.size() != 1)
            throw new TypeException();
        card = cards.first();
    }
    public Card getCard() {
        return card;
    }
    public boolean isGreater(TreeSet<Card> cards) {
        try {
            Single single = new Single(cards);
            return single.getCard().getSize().isGreater(card.getSize().SIZE);
        }
        catch (TypeException e) {
            return false;
        }
    }
}
