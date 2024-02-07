package com.jh.runtime;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.HashSet;
import java.util.Objects;

public class OpenBox extends BorderPane {
    private Node node;
    private Type type;

    public Node getNode() {
        return node;
    }

    public Type getType() {
        return type;
    }

    public OpenBox(int x, int y, Type type) {
        node = new Node(x, y);
        this.type = type;
        if(type != null) {
            ImageView show = new ImageView(type.getImage());
            setCenter(show);
        }


//        System.out.println(type.toString());
//        setStyle("-fx-background-color: "+type.toString());
        setWidth(10);setHeight(10);
        setOnMouseClicked(value->{
            GameSystem.getInstance().clickNode(this);
            boolean over = GameSystem.getInstance().isGameOver();
            if(over) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "游戏结束");
                alert.setContentText("要再来一局吗？");
                alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                ButtonType t = alert.showAndWait().get();
                if(ButtonType.YES.equals(t)) {
                    GameSystem.getInstance().restart();
                }
                else if(ButtonType.NO.equals(t)) {}
                else {
                    Platform.exit();
                }
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenBox openBox = (OpenBox) o;
        if(!Objects.equals(node, openBox.node)) return false;
        if(this.type == null || openBox.type == null) return true;
        return type.equals(openBox.getType());
    }

    @Override
    public int hashCode() {
        int hashcode = node.getX();
        hashcode *= 17;
        hashcode += node.getY();
        return hashcode*17;
    }

    @Override
    public String toString() {
        return type+"("+node.getX()+", "+node.getY()+")";
    }
}
