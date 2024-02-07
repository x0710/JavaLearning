package com.core;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.*;
import java.awt.image.BufferedImage;

public class Intercept extends Core {
    @Override
    protected void check(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT); // 设置透明
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Rectangle2D rectangle2D = Screen.getPrimary().getBounds();

        BufferedImage image = screenShot(0, 0, (int)rectangle2D.getWidth(), (int)rectangle2D.getHeight());
        stage.setAlwaysOnTop(true);
        stage.setFullScreen(true);

        ImageView imageView = new ImageView();
        root.getChildren().add(imageView);
        Image image1 = SwingFXUtils.toFXImage(image, null);
        imageView.setImage(image1);

        HBox hBox = new HBox();

    }
}