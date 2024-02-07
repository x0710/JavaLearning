package com.core;

import com.depend.*;
import java.util.*;
import static com.depend.Color.*;

public class Licensing {
    private Licensing() {}
    public static ArrayList<Card> getShuffledCards() {
        ArrayList<Card> ret = getASetCards();
        shuffle(ret);
        return ret;
    }
    public static void shuffle(List<Card> cards) {
        Collections.shuffle(cards);
    }
    public static ArrayList<Card> getASetCards() {
        ArrayList<Card> arrayList = new ArrayList<>(54);
        Color[] colors = {HEART, CLUB, DIAMOND, SPADE};
        for (Color color : colors) {
            for (int j = 1; j <= 13; j++) {
                arrayList.add(new Card(color, Size.forSize(j)));
            }
        }
        arrayList.add(new Card(JOKER, Size.BLACK_JOKER));
        arrayList.add(new Card(JOKER, Size.RED_JOKER));
        return arrayList;
    }
}
