package com;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UI extends Application {

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label l = new Label("THIS IS A LABEL");
		TextField field = new TextField();
		field.setPromptText("inputSomething");
		
		l.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				Dragboard db = l.startDragAndDrop(TransferMode.COPY_OR_MOVE);
				ClipboardContent cc = new ClipboardContent();
				cc.putString(l.getText());
				db.setContent(cc);
			}
		});
		l.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		field.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
				
			}
		});
		field.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
//				event.acceptTransferModes(TransferMode.ANY);
				field.appendText((String)event.getDragboard().getContent(DataFormat.PLAIN_TEXT));
			}
		});
		HBox hBox = new HBox(l, field);
		Button dragableButton = new Button("DragMe");
		dragableButton.setOnAction(act->{
			SystemTray st = SystemTray.getSystemTray();
			Image bi = Toolkit.getDefaultToolkit().createImage("E:\\Pictures\\Saved Pictures\\QQ202106271029_OCR\\pic.jpg");
			TrayIcon ti = new TrayIcon(bi,"javaFxLearn");
			ti.addActionListener(action->Platform.runLater(()->primaryStage.show()));
			PopupMenu tip = new PopupMenu("label");
			MenuItem mi1 = new MenuItem("退出");
			MenuItem mi2 = new MenuItem("关机");
			mi1.addActionListener(action->{
				Platform.exit();
			});
			mi2.addActionListener(action->{
				try {
					Process p = Runtime.getRuntime().exec("shutdown -s -t 0");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			tip.add(mi1);
			tip.add(mi2);
			ti.setPopupMenu(tip);
			try {
				st.add(ti);
			}
			catch (AWTException e) {
				e.printStackTrace();
			}
			primaryStage.hide();
		});
		TextArea dragTextArea = new TextArea();
		dragTextArea.setPromptText("拖一个文件试试！");
		dragTextArea.setEditable(false);
		dragTextArea.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
//				event.acceptTransferModes(TransferMode.);
			}
			
		});
		dragTextArea.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if(db.hasString()) {
					dragTextArea.setText(db.getString());
				}
				else if(db.hasFiles()) {
					List<File> files = db.getFiles();
					FileReader fr = null;
					try {
						fr = new FileReader(files.get(0), Charset.forName("GBK"));
						StringBuilder sb = new StringBuilder();
						char[] buffer = new char[128];
						for(int size;(size=fr.read(buffer))!= -1;) {
							sb.append(buffer, 0, size);
						}
						dragTextArea.setText(sb.toString());
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					finally {
						if(fr != null) {
							try {
								fr.close();
							}
							catch(IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
		AnchorPane a = new AnchorPane(dragTextArea, hBox, dragableButton);
		AnchorPane.setTopAnchor(dragTextArea, 60d);
		
		primaryStage.setScene(new Scene(a));
		primaryStage.setWidth(800);
		primaryStage.setHeight(400);
		primaryStage.show();
		dragableButton.setOnMouseDragged(new EventHandler<MouseEvent>() {
			double dX = dragableButton.getWidth(), dY = dragableButton.getHeight();
			@Override
			public void handle(MouseEvent event) {
				dragableButton.setLayoutX(event.getSceneX()-dX/2);
				dragableButton.setLayoutY(event.getSceneY()-dY/2);
			}
		});
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}
	
}
