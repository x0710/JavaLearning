package com.jh.ui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ErrorWindow extends Alert {
    public ErrorWindow(String message, Exception exception) {
        super(AlertType.ERROR, exception.getMessage());
        setTitle(message);
        /*DialogPane dp = new DialogPane();
        dp.setContentText(message);
        if(exception != null) {
            dp.setHeaderText(message);
            dp.setContentText(exception.toString());
            Text msg = new Text(exception.getMessage());
            dp.setExpandableContent(msg);
            dp.setExpanded(false);
        }
        dp.getButtonTypes().add(ButtonType.OK);
        dp.lookupButton(ButtonType.OK).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                close();
            }
        });*/
        /*initStyle(StageStyle.UTILITY);
        initModality(Modality.WINDOW_MODAL);
        setWidth(550);
        setHeight(140);*/
//        setTitle("发生异常");
//        Alert alert = new Alert(Alert.AlertType.ERROR, message);

    }
}
