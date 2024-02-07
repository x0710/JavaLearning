package com.code;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 维护JavaFx Application的维一类
 * {@link OAM}是一个有main入口的方法，这也意味着不需要实例化
 * 当main()被执行时，会自动寻找src目录(与.jar包同级)/com/aaui/下所有的.jar文件
 * 每一个.jar为一个单元加入功能，调用该包下com.jh.Main#getInstruction
 * 按钮点击时调用com.jh.Main#getStage且{@link Stage#show（）}
 * @author 15940
 *
 */
public class OAM extends Application{
	private static Set<Startable> startables = new HashSet<>();
	private static Map<File, Exception> exceptions = new HashMap<>();
	
	public OAM() {}
	
	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			runStart(primaryStage);
		}
		catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR, "发生异常");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			throw e;
		}
	}
	
	private void runStart(Stage primaryStage) throws Exception {
		ObservableList<Startable> data = FXCollections.observableArrayList();
		data.addAll(startables);
		TableView<Startable> tv = new TableView<>(data);
		TableColumn<Startable, String> ins = new TableColumn<>("支持的内容");
		ins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Startable,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Startable, String> param) {
				return new SimpleStringProperty(param.getValue().instruction());
			}
		});
		TableColumn<Startable, Button> btn = new TableColumn<>("开始");
		btn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Startable,Button>, ObservableValue<Button>>() {

			@Override
			public ObservableValue<Button> call(CellDataFeatures<Startable, Button> param) {
				Button b = new Button("SHOW");
				b.setOnAction(action->{
					EditClass ec = (EditClass)param.getValue();
					ec.init().show();
					if(ec.getException() != null) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setContentText(ec.getException().getMessage());
						alert.showAndWait();
					}
					primaryStage.setIconified(true);
				});
				return new SimpleObjectProperty<Button>(b);
			}
		});
		tv.getColumns().addAll(ins, btn);
		VBox root = new VBox(tv);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}

	public static void main(String[] args) {
		File dir = new File("src/com/aaui");
		if(!(dir.exists() && dir.isDirectory())) {
			throw new RuntimeException(dir+" 不存在");
		}
		File[] jarsFiles = dir.listFiles();
		ArrayList<Startable> list = new ArrayList<>();
//		URL[] urls = new URL[jarsFiles.length];
		for(int i = 0;i < jarsFiles.length;i++) {
			try {
				URLClassLoader cl = new URLClassLoader(new URL[]{jarsFiles[i].toURI().toURL()});
				Class<?> r = cl.loadClass("com.jh.Main");
				Object o = r.newInstance();
				Method getInstructionMethod = r.getMethod("getInstruction", null),
						getStageMethod = r.getMethod("getStage", null);
				EditClass s = new EditClass() {
					@Override
					public String instruction() {
						try {
							return (String)getInstructionMethod.invoke(o, null);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//							exceptions.put(jarsFiles[i], e);
							setException(e);
							startables.remove(this);
						}
						return null;
					}
					@Override
					public Stage init() {
						try {
							return (Stage)getStageMethod.invoke(o, null);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//							exceptions.put(jarsFiles[i], e);
							setException(e);
							startables.remove(this);
						}
						return null;
					}
				};
				list.add(s);
			} catch (Exception e) {
				exceptions.put(jarsFiles[i], e);
			}
		}
		
		list.forEach(OAM::addApp);
		launch(args);
	}
	
	/**
	 * 手动添加该应用的一个功能
	 * @param app 添加的功能
	 * @exception NullPointerException 如果app为null
	 */
	public static void addApp(Startable app) {
		startables.add(app);
	}
}
