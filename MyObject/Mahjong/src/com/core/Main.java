package com.core;

import com.depend.Card;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*ArrayList<Card> al = Licensing.getShuffledCards();
        Iterator it = al.iterator();
        while(it.hasNext()) {
            System.out.println((Card)it.next());
        }*/

        Set<Product> tmp = new TreeSet<>();
        tmp.add(new Product(1.5, "Watermelon"));
        tmp.add(new Product(2.5, "Banana"));
        tmp.add(new Product(1.7, "Apple"));
        tmp.add(new Product(1.5, "Know1.5"));
        tmp.add(new Product(5, "Pear"));
        tmp.add(new Product(3,"Hot"));
        tmp.add(new Product(1, "Peach"));
        System.out.println(tmp);
    }
}
class Product implements Comparable {
    private double price;
    private String name;

    public Product(double price, String name) {
        this.price = price;
        this.name = name;
    }
    void tmp() {
        Product p = new Product(141, "adaf");
        double ada = p.price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " " + price + "/kg";
    }

    @Override
    public int compareTo(Object o) {
        return (int)(price*10-((Product)o).getPrice()*10);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
