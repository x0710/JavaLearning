package com.jh;

import java.io.FileWriter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
	private Stage stage;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.stage = arg0;
		String path = "/cfa9f797c94f28ec60463cfab781650.jpg";
		Image image = new Image(path, 128, 1700, true, true,false);
		PixelReader prx = image.getPixelReader();
		Dealable d = new Dealer();
		FileWriter fw = null;
		fw = new FileWriter("C:\\Users\\15940\\OneDrive\\Desktop\\pic.txt");
		
		System.out.println("width"+image.getWidth());
		System.out.println("height"+image.getHeight());
		for(int i = 0;i < image.getHeight();i++) {
			for(int j = 0;j < image.getWidth();j++) {
				String str = d.dealChar(prx.getArgb(j, i));
				fw.write(str);
			}
			fw.write(System.lineSeparator());
		}
		fw.flush();
		fw.close();
		ImageView iv = new ImageView(image);
		arg0.setScene(new Scene(new HBox(iv)));
//		arg0.show();
	}
	public String getInstruction() {
		return "ImageToText";
	}
	public Stage getStage() {
		try {
			start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stage;
	}
}
