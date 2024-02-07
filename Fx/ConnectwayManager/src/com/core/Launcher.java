package com.core;

import java.util.Scanner;

import javafx.application.Application;

public class Launcher {
    public static final double VAL = Math.log(11.5);
    public static void main(String[] args) {
        Application.launch(UI.class, args);
    }
    
    public static double scoreSummNo1(double val) {
        return Math.log(val/365)/VAL+2;
    }
}
