package com.jh;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MineGame extends Stage {
    private ObservableList<Cell> mines = FXCollections.observableArrayList();
    
    public MineGame() {
        Stage primaryStage = this;
        Label usedTime = new Label("已用时间");
        ImageView restart = new ImageView(new Image("./face.jpg"));
        restart.setFitWidth(32);
        restart.setFitHeight(32);
        FlowPane restartFlowPane = new FlowPane(restart);
        restartFlowPane.setAlignment(Pos.CENTER);
        Label restMines = new Label("剩余雷数");
        AnchorPane tipBox = new AnchorPane(usedTime, restartFlowPane, restMines);
        AnchorPane.setLeftAnchor(usedTime, 0d);
        AnchorPane.setRightAnchor(restMines, 0d);

        BorderPane root = new BorderPane();
        root.setTop(tipBox);

        GridPane minesArea = new GridPane();
        root.setCenter(minesArea);

        mines.addListener((ListChangeListener<Cell>) c -> {
            while(c.next()) {
                System.out.println("ADD Sth.");
                if(c.wasAdded()) {
                    for(Cell c2 : c.getAddedSubList()) {
                        minesArea.add(c2, c2.getX(), c2.getY());
                    }
                }
            }
            
        });
        restart.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler->{
            this.mines.clear();
            minesArea.getChildren().clear();
            GameSystem.getInstance().restart(30, 30, 25);
            // System.out.println(minesArea.getChildren());
        });

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public void setMines(Collection<Cell> mines) {
        this.mines.clear();
        this.mines.addAll(mines);
    }
    HashSet<Cell> getMines() {
        HashSet<Cell> hashSet = new HashSet<>();
        mines.forEach(hashSet::add);
        return hashSet;
    }
    public void openCell(Cell target) {
        
    }
}
