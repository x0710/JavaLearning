package com.depend;

public class Card implements Comparable {
    private Color color;
    private Size size;
    public Card(Color color, Size size) {
        this.color = color;
        this.size = size;
    }
    public Color getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    @Override
    public String toString() {
        return color.toString() + size.toString();
    }

    @Override
    public int compareTo(Object o) {
        Card card = (Card)o;
        int result = card.size.getSize() - size.getSize();
        if(result < 0)
            return 1;
        else if(result == 0) {
            int r = color.ordinal() - card.color.ordinal();
            if(r < 0) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    public int hashCode() {
        int ret = 13;
        ret = ret * 7 + color.ordinal();
        ret = ret * 7 + size.getSize();
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj instanceof Card) {
            Card card = (Card)obj;
            if(card.color == this.color && card.size.getSize() == this.size.getSize())
                return true;
        }
        return false;
    }
}
