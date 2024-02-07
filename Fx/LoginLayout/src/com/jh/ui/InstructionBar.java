package com.jh.ui;

import com.jh.connect.DatabasesManager;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InstructionBar extends VBox {
    private final Text connectingIP, user, usingDatabase;
    private final ProgramSystem programSystem;

    /**
     *
     * @param programSystem 将内容刷新为此{@link ProgramSystem}的信息
     */
    public InstructionBar(ProgramSystem programSystem) {
        this.programSystem = programSystem;
        // 初始化信息栏
        connectingIP = new Text();
        user = new Text();
        usingDatabase = new Text();

        Label connectIPLabel = new Label("连入MySQL服务器IP：");
        HBox connectHBox = new HBox(connectIPLabel, connectingIP);

        Label userLabel = new Label("当前用户：");
        HBox userHBox = new HBox(userLabel, user);

        Label usingDatabaseLabel = new Label("正在使用的数据库：");
        HBox databaseHBox = new HBox(usingDatabaseLabel, usingDatabase);

        getChildren().addAll(connectHBox, userHBox, databaseHBox);
    }
    /**
     * 刷新UI组件
     */
    public void flushUI() {
        DatabasesManager databasesManager = programSystem.getConnectionHolder();
        user.setText(databasesManager.user);
        connectingIP.setText(databasesManager.ip+":"+databasesManager.port);
        usingDatabase.setText(databasesManager.getUsingDatabase());
    }

}
