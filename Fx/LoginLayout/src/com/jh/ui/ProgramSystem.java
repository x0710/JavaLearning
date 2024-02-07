package com.jh.ui;

import com.jh.connect.DatabasesManager;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class ProgramSystem extends Application {
    private DatabasesManager connection;

    @Override
    public void start(Stage stage) throws Exception {
//        test();
//        Platform.setImplicitExit(false);
        // 窗口初始化
        stage.setWidth(350);
        stage.setHeight(600);
        stage.setTitle("欢迎使用MySql登录系统");
//        stage.setResizable(false);

        // 窗口布局
        BorderPane ap = new BorderPane();
        Scene root = new Scene(ap);
        stage.setScene(root);

        // 菜单栏
        MenuItem about = new MenuItem("进入作者网页");
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HostServices hs = ProgramSystem.this.getHostServices();
                hs.showDocument("https://x0710.github.io/website/welcome.html");
            }
        });
        Menu help = new Menu("帮助");
        help.getItems().add(about);
        MenuBar menuBar = new MenuBar(help);
        Text hello = new Text("登录到MySQL");
        hello.setStyle("-fx-font-size: 20px;-fx-fill: red;");

        TextField ipField = new TextField("localhost");
        Label ipTip = new Label("ip：");
        ipField.setPromptText("localhost");
        HBox ip = new HBox(ipTip, ipField);

        TextField accountField = new TextField();
        Label ac = new Label("账号：");
        accountField.setPromptText("您的账号");
        HBox account = new HBox(ac, accountField);

        PasswordField passwordField = new PasswordField();
        Label pw = new Label("密码：");
        passwordField.setPromptText("您的密码");
        HBox password = new HBox(pw, passwordField);

        Button login = new Button("登录");
        TextField portField = new TextField("3306");
        Label portL = new Label("端口：");
        portField.setPrefWidth(80);
        portField.setPromptText("端口");
        HBox port = new HBox(portL, portField);
        portField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                System.out.println("s: "+ s);
//                System.out.println("t1: "+t1);
                char[] tic = t1.toCharArray();
                if(tic.length >= 6) {
                    portField.setText(s);
                }
                for(char c : tic) {
                    if('9' < c || '0' > c) {
                        portField.setText(s);
                    }
                }

            }
        });
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(KeyCode.ENTER.equals(keyEvent.getCode())) {
                    login.fire();
                }
            }
        });
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    stage.setTitle("正在连接");
                    String ip = ipField.getText(),
                            user = accountField.getText(),
                            pwd = passwordField.getText();
                    connection = new DatabasesManager(ip, user, pwd, portField.getText());
                    stage.setTitle("连接成功");

                    stage.close();
                    openWorkUI();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    stage.setTitle("连接失败");
                    ErrorWindow error = new ErrorWindow("无法连接", e);
                    error.showAndWait();
                }
            }
        });
        BorderPane loginPane = new BorderPane(login);
        loginPane.setLeft(port);
//        loginPane.setCenter();

        FlowPane vBox = new FlowPane(hello, ip, account, password, loginPane);
        vBox.setVgap(20);
        vBox.setOrientation(Orientation.VERTICAL);
        vBox.setAlignment(Pos.CENTER);
        ap.setTop(menuBar);
        ap.setCenter(vBox);

        stage.show();
    }

    /**
     * 打开工作页面
     */
    protected void openWorkUI() {
        SelectTable work = new SelectTable(this);
        work.show();
    }
    @Override
    public void init() throws Exception {
        super.init();
        Class.forName("com.jh.connect.DatabasesManager");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if(connection != null) {
            connection.close();
            System.out.println("连接已正常关闭");
        }
    }
    protected DatabasesManager getConnectionHolder() {
        return connection;
    }
}
