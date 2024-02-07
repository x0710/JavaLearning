package com.jh;

import com.jh.runtime.GameUI;
import com.jh.runtime.OpenBox;
import com.jh.runtime.Type;
import javafx.application.Application;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Application.launch(GameUI.class, args);
//        HashSet h = new HashSet();
//        h.add(new OpenBox(1, 2, Type.RED));
//        System.out.println(h.remove(new OpenBox(1,2,Type.BLUE)));
//        System.out.println();
    }
}
