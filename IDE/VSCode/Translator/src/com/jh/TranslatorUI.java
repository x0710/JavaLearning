package com.jh;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.melloware.jintellitype.JIntellitype;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TranslatorUI extends Application {
	private final static Alert ERROR = new Alert(AlertType.ERROR);
	private Connection connection;
	
	private double x, y; // Change the location.The last location.
	private Label pointingLabel = new Label(); // Title
	
	@Override
	public void start(Stage primaryStage) {
		try {
			runStart(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
			ERROR.setContentText(e.getMessage());
			ERROR.showAndWait();
		}
	}
	
	@Override
	public void init() throws IOException {
		if(!JIntellitype.isJIntellitypeSupported()) {
			System.err.println("不支持快捷键！");
		}
		else {
			JIntellitype.getInstance().registerHotKey(SHOW, "ALT+SPACE");
		}
		
	}
	
	public static final int SHOW = 0x30547;

	@Override
	public void stop() throws Exception {
		JIntellitype.getInstance().cleanUp();
		System.out.println("UI进程结束");
	}

	private void runStart(Stage primaryStage) {
		Platform.setImplicitExit(false);
		
		TextField inpuTextField = new TextField();
		inpuTextField.setPromptText("要翻译的单词");
		inpuTextField.setPrefHeight(40);
		inpuTextField.setPrefWidth(400);
		inpuTextField.setFont(new Font("宋体", 25));
		Button translateButton = new Button("翻译");
		translateButton.setFont(new Font("宋体", 25));
		translateButton.setPrefHeight(45);
		translateButton.setPrefWidth(150);
		translateButton.setStyle("-fx-color:#53868B");
		HBox hBox = new HBox(inpuTextField, translateButton);
		HBox.setMargin(inpuTextField, new Insets(0, 5, 5, 0));
		HBox.setMargin(translateButton, new Insets(3, 5, 5, 45));
		TextArea resulTextArea = new TextArea();
		resulTextArea.setFont(new Font("宋体", 30));
		resulTextArea.setPrefWidth(600);
		resulTextArea.setPrefRowCount(4);
		resulTextArea.setPromptText("您翻译的结果将会显示在这里");
		resulTextArea.setStyle("-fx-fill-color:#A2CD5A");
		inpuTextField.setOnAction(action->translateButton.fire());
		translateButton.setOnAction(new EventHandler<ActionEvent>() {
			private String lastTrans = "";
			@Override
			public void handle(ActionEvent event) {
				String resultNewString = "无匹配结果";
				String input = inpuTextField.getText().trim();
				if(lastTrans.equals(input)) {
					primaryStage.hide();
					return;
				}
				lastTrans = input;
				ResultSet rs;
				StringBuilder sb = new StringBuilder();
				if(input.matches("^[a-zA-Z0-9-]*$")) { // 输入的是英文
					PreparedStatement ps;
					try {
						ps = connection.prepareStatement("SELECT word, translation FROM enwords WHERE word = ?");
						ps.setString(1, input);
						rs = ps.executeQuery();
						
						StringBuilder translation = new StringBuilder();
						if(rs.next()) {
							input = rs.getString("word");
							sb.append(input).append(System.lineSeparator());
							translation.append(rs.getString("translation"));
	//						Pattern pattern = Pattern.compile("\\w?(\\w+\\..*)\\w?");
						}
						sb.append(translation);
					} catch (SQLException e) {
						e.printStackTrace();
						resultNewString = e.getMessage();
					}
					resulTextArea.setWrapText(true);
				}
				else { // 汉译英
					/* s1
					if(input.matches("^.*['_%].*$")) { // 防止SQL注入问题
						inpuTextField.selectAll();
						return;
					}
					*/
					String[] limit = input.split("\\s+");
					if(limit.length == 0) {
						inpuTextField.selectAll();
						return;
					}
					StringBuilder sql2 = new StringBuilder("SELECT word, translation FROM enwords "
							+ "WHERE translation like ?");
					for(int i = 1;i < limit.length;i++) {
						sql2.append(" AND translation LIKE ?");
					}
					/*在早期版本使用这种算法(s1)
					StringBuilder sqlStringBuilder = 
							new StringBuilder("SELECT word, translation FROM enwords WHERE translation LIKE '%"+limit[0]+"%'");
					for(int i = 1;i < limit.length;i++) {
						sqlStringBuilder.append(" AND translation LIKE '%"+limit[i]+"%'");
					}
					*/
					try {
//						rs = connection.createStatement().executeQuery(sqlStringBuilder.toString()); // s1
						PreparedStatement ps = connection.prepareStatement(sql2.toString());
						for(int i = 0;i < limit.length;i++) {
							ps.setString(i+1, "%"+limit[i]+"%");
						}
						rs = ps.executeQuery();
						while (rs.next()) {
							String resultWord = rs.getString("word"),
									translation = rs.getString("translation");
	//						result.put(resultWord, translation);
							
							sb.append(resultWord).append(": ").append(translation).append(System.lineSeparator());
						}
					} catch (SQLException e) {
						e.printStackTrace();
						resultNewString = e.getMessage();
					}
//					Map<String, String> result = new HashMap<>();
					resulTextArea.setWrapText(false);
				}
				if(sb.length() == 0) {
					resulTextArea.setText(resultNewString);
				} else
					resulTextArea.setText(sb.toString());
				inpuTextField.selectAll();
				
			}
		});
		
		JIntellitype.getInstance().addHotKeyListener(listener->{
			if(listener == SHOW) {
				Platform.runLater(()->{
					if(primaryStage.isShowing()) {
						primaryStage.hide();
					}
					else {
						primaryStage.show();
						inpuTextField.selectAll();
					}
				});
			}
		});
		
		Button close = new Button("Close");
		close.setOnAction(action->{
			primaryStage.close();
			Platform.setImplicitExit(true);
		});
		close.setBackground(new Background(new BackgroundFill(null, null, null)));
		AnchorPane pointingBar = new AnchorPane(pointingLabel, close);
		AnchorPane.setTopAnchor(pointingBar, 0d);
		AnchorPane.setTopAnchor(close, 0d);
		AnchorPane.setLeftAnchor(pointingLabel, 15d);
		AnchorPane.setRightAnchor(close, 0d);
		
		FlowPane flowPane = new FlowPane(pointingBar, hBox, resulTextArea);
	//	FlowPane.setMargin(flowPane, new Insets(0d));
		flowPane.setAlignment(Pos.CENTER);
		flowPane.setOrientation(Orientation.VERTICAL);
	//	flowPane.setStyle("-fx-color:black");
		Scene scene = new Scene(flowPane);
		scene.setOnKeyReleased(action->{
			if(KeyCode.ESCAPE.equals(action.getCode())) primaryStage.hide();
		});
		scene.setOnMousePressed(val->{
			x = val.getSceneX();
			y = val.getSceneY();
//			System.out.println("pressed "+x+", "+y);
		});
		scene.setOnMouseDragged(val->{
			primaryStage.setX(val.getScreenX()-x);
			primaryStage.setY(val.getScreenY()-y);
		});
		primaryStage.setScene(scene);
		primaryStage.focusedProperty().addListener((o, a, b)->{
			if(!b) { // 失去焦点
				primaryStage.hide();
			}
	//		System.out.println(b);
		});
		pointingLabel.textProperty().addListener((o, a, b)->{
			primaryStage.setTitle(b);
		});
		pointingLabel.setText("单词翻译");
		primaryStage.setOnHiding(act->{
			pointingLabel.setText("翻译-正在断开连接...");
	//		System.out.println("HIDING");
			try {
				if(connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pointingLabel.setText("翻译-隐藏窗口中");
		});
		
		primaryStage.setOnShown(act->{
			pointingLabel.setText("翻译-正在连接至数据库...");
			translateButton.setDisable(true);
			Thread t = new Thread() {
				public void run() {
					try {
						connection = Translator.createConnection();
//						System.out.println(c.hashCode());
						Platform.runLater(()->pointingLabel.setText("翻译-准备就绪"));
						translateButton.setDisable(false);
					}
					catch(DriverNotFoundException e) {
						Platform.runLater(()->pointingLabel.setText("翻译-驱动找不到，无法正常运行！"));
					}
					catch (SQLException e) {
						Platform.runLater(()->pointingLabel.setText("翻译-错误，请检查日志！"));
					}
				}
			};
			t.setName("TranslationThread");
			t.start();
		});
		primaryStage.setWidth(650);
		primaryStage.setHeight(350);
		primaryStage.setResizable(false);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setOpacity(0.9D);
	//	primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image("./title.png"));
	//	primaryStage.centerOnScreen();
//		Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_HIDING));
	//	primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("title.png")));
	//	primaryStage.show();
		System.out.println("UI准备就绪");
	}
}
