import java.io.File;
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {
	private File path;
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button chooseDir = new Button("选择路径");
		TextField directoryPath = new TextField();
		Button startButton = new Button("开始");
		HBox hBox = new HBox(chooseDir, directoryPath);
		ObservableList<File> files = FXCollections.observableArrayList();
		ListView<File> from = new ListView<>(files);
		VBox root = new VBox(hBox, from);
		
		Parent n = (new FXMLLoader().load(new FileInputStream(new File("C:\\Users\\15940\\OneDrive\\Desktop\\work\\Fx\\javaFxLearn\\src\\fxml\\fxml1.fxml"))));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		from.setEditable(true);
		
//		from.setCellFactory(TextFieldListCell.forListView(new StringConverter<File>() {
//
//			@Override
//			public String toString(File object) {
//				return object.getAbsolutePath();
//			}
//
//			@Override
//			public File fromString(String string) {
//				
//				return new File(string);
//			}
//		}));
		from.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
			ListCell<File> ret;

			@Override
			public ListCell<File> call(ListView<File> param) {
				ret = new ListCell<File>() {
					private Node root;
					@Override
					protected void updateItem(File item, boolean empty) {
						super.updateItem(item, empty);
						if(empty) return;
						if(root != null) {
							setGraphic(root);
							return;
						}
//						ImageView iv = null;
//						if(item.isDirectory()) {
//							iv = new ImageView("https://bpic.588ku.com/element_origin_min_pic/19/04/24/5a6e6b2f95e18b7d820c7f31a814d39d.jpg");
//						}
//						else {
//							iv = new ImageView("https://tse1-mm.cn.bing.net/th/id/OIP-C.vF8FIiL3aLM0XXXlaCmj_wAAAA?w=179&h=180&c=7&r=0&o=5&pid=1.7");
//						}
//						iv.setPreserveRatio(true);
//						iv.setFitWidth(20d);
						Label name = new Label(item.getAbsolutePath());
						HBox h = new HBox(/* iv, */name);
						h.setAlignment(Pos.CENTER_LEFT);
						root = h;
						setGraphic(root);
					}
					
					@Override
					public void startEdit() {
						super.startEdit();
						System.out.println("startEdit");
						TextField newV = new TextField();
						setGraphic(newV);
						newV.setOnKeyPressed(action->{
							if(KeyCode.ENTER.equals(action.getCode())) {
								File nV = new File(newV.getText());
								files.set(param.getEditingIndex(), nV);
								ret.commitEdit(nV);
							}
						});
					}

					@Override
					public void commitEdit(File newValue) {
						super.commitEdit(newValue);
						System.out.println("commitEdit");
						param.refresh();
//						setGraphic(root);
					}

					@Override
					public void cancelEdit() {
						super.cancelEdit();
						System.out.println("cancelEdit");
						setGraphic(root);
					}
					
				};
				return ret;
			}
		});
		
		directoryPath.setPromptText("读取路径");
		chooseDir.setOnAction(action->{
			DirectoryChooser dc = new DirectoryChooser();
			Stage stage = new Stage();
			stage.setTitle("选择路径");
//			File dir = dc.showDialog(stage);
			File dir = new File("F:");
			path = dir;
			if(dir != null) {
				directoryPath.setText(dir.getAbsolutePath());
				File[] filesT = dir.listFiles();
				files.clear();
				for(File f : filesT) {
					files.add(f);
				}
			}
		});
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
