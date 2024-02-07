package com.depend.type;

import com.depend.Card;

import java.util.Iterator;
import java.util.TreeSet;

public class Pare extends CardType {
    private int logo;
    protected Pare(TreeSet<Card> cards) {
        super(cards);
        if(cards.size() != 2)
            throw new TypeException("牌数量错误");
        Iterator<Card> it = cards.iterator();
        Card c1 = it.next(), c2 = it.next();
        if(c1.getSize().SIZE != c2.getSize().SIZE)
            throw new TypeException("双牌不相同");
        logo = c1.getSize().SIZE;
    }
    public int getLogo() {
        return logo;
    }

    @Override
    public boolean isGreater(TreeSet<Card> cards) {
        Pare pare;
        try {
            pare = new Pare(cards);
            return pare.getLogo() > logo;
        }
        catch (TypeException e) {
            return false;
        }

    }

}
