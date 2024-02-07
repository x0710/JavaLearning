package com.depend;

import java.util.Collection;
import java.util.TreeSet;

public interface Play {
    /**
     * 某一方打牌时，调用此方法
     * @param lastPlayer 上家出的牌
     * @return 所打出的牌
     */
    TreeSet<Card> play(Collection<Card> lastPlayer);
}
