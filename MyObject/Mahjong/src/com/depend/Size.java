package com.depend;

public class Size {
    public static final Size RED_JOKER = new Size(15);
    public static final Size BLACK_JOKER = new Size(14);

    public final int SIZE;

    private Size(int size) {
        this.SIZE = size;
    }
    public static Size forSize(int size) {
        if(size <= 13 && size >= 1)
            return new Size(size);
        return null;
    }
    public boolean isGreater(final int VAL) {
        return VAL > SIZE;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj.getClass() == getClass()) {
            Size s = (Size)obj;
            return s.SIZE == SIZE;
        }
        return false;
    }

    @Override
    public String toString() {
        return SIZE + "";
    }
}
