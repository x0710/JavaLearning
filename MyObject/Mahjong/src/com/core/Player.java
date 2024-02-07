package com.core;

import com.depend.Card;
import com.depend.Play;

import java.util.*;

public abstract class Player implements Play {
    protected TreeSet<Card> hasCards;

    public Player() {
        hasCards = new TreeSet<>();
    }
    public void draw(Card card) {
        hasCards.add(card);
    }
    public abstract int score();
}
