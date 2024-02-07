package com.jh;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		Image image = new Image("file:C:\\Users\\15940\\OneDrive\\Desktop\\9dd9e9de13d5fef55064d7fc16db1c0.jpg");
		WritableImage wi = new WritableImage(image.getPixelReader(), (int)image.getWidth(), (int)image.getHeight());
		PixelWriter pw = wi.getPixelWriter();
		PixelReader pr = wi.getPixelReader();
		
		for(int i = 0;i < image.getHeight();i++) {
			for(int j = 0;j < image.getWidth();j++) {
				int argb = pr.getArgb(i, j);
				int alpha =(argb>>24)&255;
				int red = (argb>>16)&255;
				int green = (argb>>8)&255;
				int blue = argb&255;
				
				pw.setColor(i, j, Color.valueOf("#"+red+green+blue));
				
			}
		}
		ImageView iv = new ImageView(wi);
		Slider slider = new Slider(0, 255, 128);
		
		HBox hBox = new HBox(slider, iv);
		
		arg0.setScene(new Scene(hBox));
		arg0.show();
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
