package com;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListViewTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ListView<String> listView = new ListView<>();
		ObservableList<String> observableList = FXCollections.observableArrayList();
		observableList.add("0309");
		
		
		listView.getItems().addAll(observableList);
		VBox vBox = new VBox(listView);
		primaryStage.setScene(new Scene(vBox));
		primaryStage.show();
	}

}
