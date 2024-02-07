package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.*;

/**
 * 一个Notepad的实例
 * 用法new Main();
 * @author x0710
 */
public class Main extends Application {
    private Stage primaryStage;

    /**
     * @see Application
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        VBox root = new VBox();
        Scene scene = new Scene(root);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(initEditor());

        root.getChildren().addAll(initMenu(), borderPane);

        loadFile(new File("C:\\Users\\15940\\OneDrive\\Desktop\\learn\\MyObject\\Chat\\Send.java"));
        loadFile(new File("C:\\Users\\15940\\OneDrive\\Desktop\\learn\\MyObject\\Notepad\\src\\sample\\Main.java"));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Notepad by x0710");
        primaryStage.setWidth(1500);
        primaryStage.setHeight(900);
        primaryStage.show();
    }

    /**
     * 已经打开的文件(正在编辑的文件)
     */
    private TabPane openedFiles;

    /**
     * 对菜单下方的编辑处进行初始化
     * @return 指定布局的初始结果
     */
    private Node initEditor() {
        VBox editor = new VBox();
        openedFiles = new TabPane();
        VBox.setVgrow(openedFiles, Priority.ALWAYS);
        editor.getChildren().add(openedFiles);
        return editor;
    }

    /**
     * 记录新建tab的下标
     */
    private int tabIndex = 1;
    /**
     * 创建一个新窗口
     * @param file 要加载的文件
     *             如果file为null，则创建一个无内容窗口
     */
    void loadFile(File file) {
        Tab tab = new Tab("new " + tabIndex++);
        EditFile textArea = new EditFile(file);
        tab.setContent(textArea);
        textArea.setPrefHeight(900);

        if(file != null) {
            if(!file.exists()) {
                System.out.println("load " + file + "Exception: 文件不存在");
                createWarning("文件未找到", "警告");
                return;
            }
            tab.setText(file.getName());
            StringBuilder sb = new StringBuilder();
            char[] str = new char[1<<8];

            Reader br = null;
            try {
                br = new FileReader(file);
                for(int size;(size = br.read(str)) != -1;)
                    sb.append(str, 0, size);
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            finally {
                try {
                    if(br != null)
                        br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    createWarning("发生IO错误", "警告");
                }
            }
            textArea.setText(sb.toString());
        }

        openedFiles.getTabs().add(tab);
    }

    private Tab getUseEditTop() {
        openedFiles.getTabs().forEach(act->{
            if(act.isSelected())
                return act;
        });
    }

    /**
     * 将正在使用的文本域文本写入到硬盘
     * @param text 要写出的{@link EditFile}
     * @return 是否成功保存
     */
    boolean saveFile(TextInputControl text, File path) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(path));
            bw.write(text.getText());
            bw.flush();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            createWarning("发生IO异常，文件未成功保存", "警告");
            return false;
        }
        finally {
            try {
                if(bw != null)
                    bw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建一个错误窗口，并播放蜂鸣器
     * @param message 错误信息
     * @param title 错误程度
     */
    public void createWarning(String message, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initOwner(primaryStage);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setHeight(120);
        stage.setWidth(330);
        Label warn = new Label(message);
        warn.setFont(Font.font(20));
        BorderPane root = new BorderPane();
        root.setCenter(warn);
        stage.setScene(new Scene(root));
        Toolkit.getDefaultToolkit().beep();
        stage.show();
    }

    /**
     * 初始化菜单布局
     * @return 指定布局的初始结果
     */
    private Node initMenu() {
        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("文件"), editor = new Menu("编辑"), tools = new Menu("工具");
        MenuItem create = new MenuItem("新建"), open = new MenuItem("打开"), save = new MenuItem("保存");
        create.setAccelerator(KeyCombination.valueOf("ctrl+n"));
        create.setOnAction(act->loadFile(null));
        open.setOnAction(new EventHandler<ActionEvent>() {
            private final TextField path = new TextField();
            private final Stage stage = new Stage();

            /**
             * 加载一个文件
             */
            class Enter implements EventHandler<ActionEvent> {
                @Override
                public void handle(ActionEvent event) {
                    loadFile(new File(path.getText()));
                    stage.close();
                }
            }
            @Override
            public void handle(ActionEvent event) {
                Enter act = new Enter();
                path.setAccessibleText("文件路径");
                path.setOnAction(act);
                Button enter = new Button("确定");
                enter.setOnAction(act);
                HBox root = new HBox();
                root.getChildren().addAll(path, enter);
                stage.setScene(new Scene(root));
                stage.show();
            }
        });
        create.setAccelerator(KeyCombination.valueOf("ctrl+o"));
        save.setAccelerator(KeyCombination.valueOf("ctrl+s"));
//        save.setDisable(true);
        save.setOnAction(act-> openedFiles.getTabs().forEach(tab->{
            if(tab.isSelected()) {
                EditFile ta = (EditFile) tab.getContent();
                File path = ta.getPath();
                if(path == null) {
                    Stage stage = new Stage();
                    stage.initOwner(primaryStage);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("另存为");
                    HBox root = new HBox();
                    TextField savePath = new TextField();
                    savePath.setAccessibleText("保存路径");
                    Button enter = new Button("确定");

                    EventHandler<ActionEvent> saveAction = act1->{
                        File setPath = new File(savePath.getText());
                        boolean isSaved = saveFile(ta, setPath);
                        if(isSaved) // 如果路径存在，则设置文件路径
                            ta.setPath(setPath);
                        stage.close();
                    };
                    stage.show();
                    enter.setOnAction(saveAction);
                    savePath.setOnAction(saveAction);
                    root.getChildren().addAll(savePath, enter);
                    stage.setScene(new Scene(root));
                }
                else {
                    saveFile(ta, path);
                }
            }
        }));

        file.getItems().addAll(create, open, save);

        editor.setDisable(true);

        MenuItem find = new MenuItem("查找"), replace = new MenuItem("替换");
        find.setAccelerator(KeyCombination.valueOf("ctrl+f"));
        find.setOnAction(act->{
            Stage findStage = new Stage();
            TextField finavalue = new TextField();
            Button findButton = new Button("查找");

            EventHandler<ActionEvent> findAction = act1->{
                openedFiles.getTabs()
            };


        });find.setDisable(true);

        replace.setAccelerator(KeyCombination.valueOf("ctrl+r"));
        replace.setDisable(true);
        tools.getItems().addAll(find, replace);

        menuBar.getMenus().addAll(file, editor, tools);

        return menuBar;
    }
    public static void main(String[] args) {
        launch(args);
    }
}

