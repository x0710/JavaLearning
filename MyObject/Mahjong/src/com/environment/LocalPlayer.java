package com.environment;

import com.core.Player;
import com.depend.Card;
import java.util.Collection;
import java.util.TreeSet;

public class LocalPlayer extends Player {
    @Override
    public int score() {
        return 0;
    }

    @Override
    public TreeSet<Card> play(Collection<Card> lastPlayer) {
        return null;
    }
}
