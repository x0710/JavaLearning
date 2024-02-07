package com.jh;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class TestApplication extends Application {
    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private Image image;
    @Override
    public void start(Stage stage) throws Exception {
        Button selectFileButton = new Button("选择文件"),
                cancel = new Button("取消");
        TextField filePath = new TextField();
        filePath.setEditable(false);
        


        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(300);
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);

        Slider size = new Slider(0.01, 1,1);
        size.setPrefWidth(200);


        selectFileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, action->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("E:\\Pictures\\"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("图片类型","*.png", "*.jpg", "*.jepg"),
                    new FileChooser.ExtensionFilter("作死类型","*.*"));
            Stage openStage = new Stage();
            File file = fileChooser.showOpenDialog(openStage);

            if(file != null) {
                filePath.setText(file.getAbsolutePath());
                image = new Image("file:".concat(file.getAbsolutePath()), true);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                image.exceptionProperty().addListener(new ChangeListener<Exception>() {
                    @Override
                    public void changed(ObservableValue<? extends Exception> observableValue, Exception exception, Exception t1) {
                        alert.setContentText(t1.getMessage());
                        alert.showAndWait();
                    }
                });/*
                image.errorProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                        alert.showAndWait();
                        System.out.println(t1);
                    }
                });*/
                image.progressProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        progressBar.setProgress(t1.doubleValue());
                        if (t1.doubleValue() >= 1.0) {
                            progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                        }
                    }
                });
                imageView.setImage(image);
            }

        });
        cancel.setOnAction(action->{
            image.cancel();
        });
        HBox fileControl = new HBox(selectFileButton, filePath, cancel, progressBar, size);
        VBox root = new VBox(fileControl, imageView);
        stage.setScene(new Scene(root));
        stage.setHeight(400);


        stage.show();
        size.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                imageView.setFitWidth(image.getWidth()*t1.doubleValue());
                imageView.setFitHeight(image.getHeight()*t1.doubleValue());
//                System.out.println(image.getHeight()*t1.doubleValue());
            }
        });
    }
}