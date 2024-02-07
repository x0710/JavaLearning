package com.tool;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

public class SpeedFlusher implements Runnable{
    private SimpleIntegerProperty managed;
    private Label updated;
    private double count1, count2;

    private boolean count1Add;
    public SpeedFlusher(SimpleIntegerProperty controler, Label updated) {
        managed = controler;
        this.updated = updated;
        managed.addListener((obs, oldV, newV)->{
            if(count1Add) count1++;
            else count2++;
        });
    }
    @Override
    public void run() {
        TimeRecoder recoder = new TimeRecoder();
        while(!Thread.currentThread().isInterrupted()) {
            recoder.setNewPoint();
            try {
                Thread.sleep(2000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
                break;
            }
            double speed = (count1+count2)/(recoder.getSecondToRecord()*2);
            if(count1Add) count2=0;
            else count1=0;
            count1Add = !count1Add;
            Platform.runLater(()->{
                updated.setText(String.format("%.3f", speed));
            });
        }
            System.out.println("flush UI");
        
    }
    // public static final DecimalFormat SPEED = DecimalFormat.getCurrencyInstance()
    
}
