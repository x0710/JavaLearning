package com.core;

import com.melloware.jintellitype.JIntellitype;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class Sketch extends Core {
    private double startX, startY, endX, endY, width, height;
    @Override
    protected void check(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);
        AnchorPane hBox = new AnchorPane();
        Group group = new Group(hBox);

        Color background = Color.valueOf("#D2E9FF10"),
                board = Color.valueOf("#408080");
        if(property != null) {
            try {
                background = Color.valueOf(property.getProperty("fillColor"));
            }
            catch (IllegalArgumentException | NullPointerException e) {
                e.printStackTrace();
            }
            try {
                board = Color.valueOf(property.getProperty("boardColor"));
            }
            catch (IllegalArgumentException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        hBox.setBorder(new Border(
                new BorderStroke(board, BorderStrokeStyle.SOLID, null, new BorderWidths(1d))));
        hBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#00000000"), null, null)));
//        hBox.setStyle("-fx-background-color:#00000000");

        Scene scene = new Scene(group);
        scene.setFill(background);
        stage.setScene(scene);

        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.setAlwaysOnTop(true);
        // 注册监听
        // 当键盘按下时
        scene.setOnKeyPressed(h->{
            if(KeyCode.ESCAPE.equals(h.getCode()))
                stage.close();
            if(!isLastIcon) {
                primary.setIconified(false);
                isLastIcon = false;
            }
        });
        // 当鼠标点击时
        scene.setOnMouseClicked(h->{
            if(MouseButton.SECONDARY.name().equals(h.getButton().name())) {
                stage.close();
                if(!isLastIcon) {
                    primary.setIconified(false);
                    isLastIcon = false;
                }
            }
        });
        // 当鼠标按下时
        scene.setOnMousePressed(h->{
            if(MouseButton.PRIMARY.name().equals(h.getButton().name())) {
                AnchorPane.setTopAnchor(hBox, 0d);
                AnchorPane.setLeftAnchor(hBox, 0d);
                width = 0;
                height = 0;
                startX = h.getScreenX();
                startY = h.getScreenY();
//                System.out.println("start: "+startX+", "+startY);
            }
//            System.out.println(h.getButton().name());
        });
        // 拖动时
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                endX = t.getScreenX();
                endY = t.getScreenY();
                if(endX >= startX) {
                    width= endX - startX;
                    hBox.setLayoutX(startX);
                }
                else {
                    width= startX - endX;
                    hBox.setLayoutX(endX);
                }
                if(endY <= startY) {
                    height= startY - endY;
                    hBox.setLayoutY(endY);
                }
                else {
                    height= endY - startY;
                    hBox.setLayoutY(startY);
                }
                hBox.setMinWidth(width);
                hBox.setPrefWidth(width);
                hBox.setMinHeight(height);
                hBox.setPrefHeight(height);
            }
        });
        // 鼠标释放时
        scene.setOnMouseReleased(h->{
            if(!MouseButton.PRIMARY.name().equals(h.getButton().name()))
                return; // 如果不是主键释放，则跳过截图，避免其它键

            stage.close(); // 先关窗，再截图
            if(width > 0 && height > 0) {
                BufferedImage image = (screenShot(
                        (int) (Math.min(startX, endX)),
                        (int) (Math.min(startY, endY)),
                        (int) width,
                        (int) height));
                if(image != null) {
                    imageView.setImage(SwingFXUtils.toFXImage(image, null));
                    try {
                        ImageIO.write(image, "png", new File(System.currentTimeMillis() + ".png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                        changePath(path);
                    }
                }

                if(!isLastIcon)
                    primary.setIconified(false);
                else
                    isLastIcon = false;
            }
            else// 如果参数错误就再次截图
                start.fire();
        });

        stage.show();
    }
}
