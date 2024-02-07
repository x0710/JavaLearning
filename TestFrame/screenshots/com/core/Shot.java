import java.io.FileOutputStream;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Shot extends Application {
    private static final int HOT_KEY = 1;
    private Stage primary;
    @Override
    public void start(Stage primaryStage) {
        this.primary = primaryStage;
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
		primaryStage.setWidth(50.0d);

        Button start = new Button("SCREENSHOT");
		
        start.setOnAction(actionEvent->{
            primaryStage.setIconified(true);
            check();
        });
		
        start.setLayoutX(5d);
        start.setLayoutY(10d);
		
        root.getChildren().add(start);
        primaryStage.show();
		
    }
    private double startX, startY, endX, endY, width, height;
    private void check() {
        Stage tmp = new Stage();
        tmp.initStyle(StageStyle.TRANSPARENT);
        HBox hBox = new HBox();
        Group group = new Group(hBox);

        hBox.setBorder(new Border(
                new BorderStroke(Paint.valueOf("#69541b"), BorderStrokeStyle.SOLID, null, new BorderWidths(2.5d))));
//        hBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#000000"), null, null)));
        hBox.setStyle("-fx-background-color:#00000000");

        Scene scene = new Scene(group);
        scene.setFill(Paint.valueOf("#BEBEBE20"));
        tmp.setScene(scene);

        tmp.setFullScreenExitHint("");
        tmp.setFullScreen(true);
		tmp.setTitle("ScreenShots");
		tmp.setHeight(50);
		tmp.setWidth(75);


        tmp.setAlwaysOnTop(true);
        //注册监听
        scene.setOnKeyPressed(h->{
            if(KeyCode.ESCAPE.equals(h.getCode()))
                tmp.close();
            primary.setIconified(false);
        });
        scene.setOnMousePressed(h->{
            if(MouseButton.SECONDARY.name().equals(h.getButton().name())) {
                tmp.close();
                primary.setIconified(false);
            }
            else if(MouseButton.PRIMARY.name().equals(h.getButton().name())) {
                hBox.setMinWidth(0d);
                hBox.setMinHeight(0d);
                startX = h.getScreenX();
                startY = h.getScreenY();
				width = 0;
				height = 0;
//                System.out.println("start: "+startX+", "+startY);
            }
//            System.out.println(h.getButton().name());
        });
        scene.setOnMouseDragged(t->{
			endX = t.getScreenX();
			endY = t.getScreenY();
			if(endX >= startX) {
				width= endX - startX;
                hBox.setLayoutX(startX);
				hBox.setMinWidth(width);
				hBox.setPrefWidth(width);
			}
			else {
				width= startX - endX;
				hBox.setLayoutX(endX);
				hBox.setMinWidth(width);
				hBox.setPrefWidth(width);
			}
			if(endY <= startY) {
				height= startY - endY;
				hBox.setLayoutY(endY);
				hBox.setMinHeight(height);
				hBox.setPrefHeight(height);
			}
			else {
				height= endY - startY;
				hBox.setLayoutY(startY);
				hBox.setMinHeight(height);
				hBox.setPrefHeight(height);
			}
			
//            System.out.println("Mouse("+h.getSceneX()+", "+h.getSceneY()+")");
        });
        scene.setOnMouseReleased(h->{
            primary.setIconified(false);
            screenShot(
			(int)(startX < endX ? endX : startX),
			(int)(startY < endY ? endY : startY),
			(int)width,
			(int)height,
			"C:\\Users\\15940\\OneDrive\\Desktop\\ScreenShot\\"+System.currentTimeMillis()+".png");
			/*
			hBox.setLayoutX((startX > endX ? endX : startX));
			hBox.setLayoutY((startY > endY ? endY : startY));
			hBox.setMinWidth(width);
			hBox.setMinHeight(height);
			*/
            //tmp.close();
        });
        tmp.show();
    }
    public static void screenShot(int x, int y, int width, int height, String path) {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        Robot robot;
        try {
            robot = new Robot();
            BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
            ImageIO.write(bufferedImage, "png", new FileOutputStream(path));
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {
		Shot.launch(args);
	}

}
