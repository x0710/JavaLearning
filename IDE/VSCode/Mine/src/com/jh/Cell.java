package com.jh;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class Cell extends Pane {
    private State state;
    private int x, y;
   
    private boolean isMine;

    private Label show;
    public Cell(int x, int y, State state, boolean mine) {
        this.x = x;
        this.y = y;
        isMine = mine;
        show = new Label();
        setState(state);
        
        // this.state = state;
        getChildren().add(show);

        show.setOnMouseClicked(value->{
            if(MouseButton.SECONDARY.equals(value.getButton())) { // 鼠标右键
                setState(state.getNext());

            }
            else if(MouseButton.PRIMARY.equals(value.getButton())) {
                setState(State.KNOW);
            }
        });
    }
    public void setState(State state) {
        this.state = state;
        if(state == null) {
            return;
        }
        if(state != State.KNOW) {
            Node imgN = show.getGraphic();
            if(imgN == null) {
                ImageView icon = new ImageView(state.getIcon());
                icon.setX(0);
                icon.setY(0);
                icon.setFitHeight(16);
                icon.setFitWidth(16);
                show.setPrefHeight(icon.getFitHeight());
                show.setPrefWidth(icon.getFitWidth());
                show.setGraphic(icon);
            }
            ((ImageView)show.getGraphic()).setImage(state.getIcon());
        }
        else { // 状态为KNOW
            int size = GameSystem.getInstance().getRoundCells(this).size();
            System.out.println(size);
            show.setText(String.valueOf(size));
        }
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += x;
        hash *= 17;
        hash += y;
        hash *= 17;
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return true;
        if(obj instanceof Cell) {
            Cell c = (Cell)obj;
            return c.x==x&&c.y==y;
        }
        return false;
    } 
    @Override
    public String toString() {
        return state+" ("+x+", "+y+")";
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean isMine() {
        return isMine;
    }
}
