package com.jh.runtime;

public class Node {
    private int x, y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        if(node.hashCode() == 0 || hashCode() == 0) return true;
        return x == node.x && y == node.y;
    }

    private int hashcode = 13;
    @Override
    public int hashCode() {
        if(hashcode != 13) {
            return hashcode;
        }
        if(x < 0 || y < 0) {
            return hashcode = 0;
        }
        int hashcode = 13;
        hashcode += x;
        hashcode *= 17;
        hashcode += y;
        return this.hashcode=hashcode;
    }
}
