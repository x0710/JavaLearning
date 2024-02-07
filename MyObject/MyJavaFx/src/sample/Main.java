package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("文件"), editor = new Menu("编辑"), radio = new Menu("单选测试"), check = new Menu("多选测试");

        MenuItem settings = new MenuItem("设置"), save = new MenuItem("保存"), exit = new MenuItem("退出");
        exit.setOnAction(act-> Platform.exit());
        SeparatorMenuItem smi = new SeparatorMenuItem();
        file.getItems().addAll(save, settings, smi, exit);

        MenuItem copyPath = new MenuItem("复制路径");
        Menu findE = new Menu("查找");
        MenuItem find = new MenuItem("查找"), replace = new MenuItem("替换");
        findE.getItems().addAll(find, replace);
        editor.getItems().addAll(copyPath, findE);

        ToggleGroup sex = new ToggleGroup();
        RadioMenuItem man = new RadioMenuItem("男"), woman = new RadioMenuItem("女"), known = new RadioMenuItem("中性");
        man.setToggleGroup(sex);
        woman.setToggleGroup(sex);
        known.setToggleGroup(sex);
        known.setSelected(true);
        radio.getItems().addAll(man, woman, known);

        CheckMenuItem read = new CheckMenuItem("读书"), write = new CheckMenuItem("写作"), television = new CheckMenuItem("电视");
        check.getItems().addAll(read, write, television);

        menuBar.getMenus().addAll(file, editor, radio, check);

        root.setTop(menuBar);
        primaryStage.setTitle("Add");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
