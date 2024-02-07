package com.depend;

public enum Color {
    JOKER("joker"), HEART("heart"), SPADE("spade"), DIAMOND("diamond"), CLUB("club");
    public final String logo;
    Color(String logo) {
        this.logo = logo.toLowerCase();
    }
    public static String cover(String obj) {
        return null;
    }
}
