package com.jh.ui;

import com.jh.connect.DatabasesManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SelectTable extends Stage {
    private final ProgramSystem programSystem;

    public SelectTable(ProgramSystem programSystem) {
        this.programSystem = programSystem;
        setTitle("选择数据库");

        DatabasesManager databasesManager = programSystem.getConnectionHolder();
        InstructionBar instructionBar = new InstructionBar(programSystem);
        instructionBar.flushUI();

        HBox content = new HBox();

        for(String database : databasesManager.getDatabases()) {
            TitledPane databasePane = new TitledPane();
//            databasePane.setExpanded(true);

            VBox vBox = new VBox(); // TitledPane中的布局

            for(String tables : databasesManager.getTables(database)) { //
//                TableManager tableManager = databasesManager.createTableManager(tables);
                Button tb = new Button(tables);
                tb.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("打开表"+tables);
                        databasesManager.setUseDatabase(database);
                        instructionBar.flushUI();
                        openTable(tables);
                    }
                });
                vBox.getChildren().add(tb);
            }
            databasePane.setContent(vBox);
            databasePane.setText(database);

            content.getChildren().add(databasePane);
        }

        VBox root = new VBox(instructionBar, content);
        setScene(new Scene(root));
    }
    public ProgramSystem getProgramSystem() {
        return programSystem;
    }
    public TableUI openTable(String table) {
        return new TableUI(programSystem.getConnectionHolder().createTableManager(table),this);
    }
}
