package l2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private class MyEventHandler implements EventHandler {
        int num;

        @Override
        public void handle(Event event) {
            System.out.println("check-"+(++num));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setOpacity(0.65d);//透明度
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        Group root = new Group();
        Button bt1 = new Button();
        bt1.setPrefWidth(200);
        bt1.setPrefHeight(200);
        MyEventHandler meh = new MyEventHandler();
        bt1.setOnAction(meh);
        BackgroundFill bf = new BackgroundFill(Paint.valueOf("#33C5E6"),
                new CornerRadii(30d), //弧度的大小
                new Insets(5.0d));
        BorderStroke bs = new BorderStroke(Paint.valueOf("#3a7C9F"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(14d),
                BorderWidths.DEFAULT);
        bt1.setBackground(new Background(new BackgroundImage(new Image("zenshopbutton.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        bt1.setBorder(new Border(bs));
        root.getChildren().addAll(bt1);
        Scene scene = new Scene(root);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN), ()->bt1.fire());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
