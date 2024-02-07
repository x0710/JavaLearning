package com.jh.runtime;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class GameUI extends Application {
    private Text time;
    private int scoreInt = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox menu = new HBox(GameSystem.getInstance().getScore());
        menu.setAlignment(Pos.CENTER);
        GridPane gameBoard = GameSystem.getInstance().getGridPane();
//        ArrayList<OpenBox> blocks = new ArrayList<>();

        VBox bBox = new VBox(menu, gameBoard);
        primaryStage.setScene(new Scene(bBox));
        primaryStage.setWidth(300);
        primaryStage.setHeight(200);
        primaryStage.show();
//        gameBoard.setStyle("-fx-background-color: #44CC11");
    }
}
