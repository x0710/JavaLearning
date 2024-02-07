package main.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ColumnType extends HBox{
    private boolean PK;
    private TextField typeOther;
    private String columnName;

    public ColumnType(String column) {
        this(column, "");
    }
    public ColumnType(String column, String other) {
        this.columnName = column;
        TextField columnName = new TextField(column);
        CheckBox pkBox = new CheckBox(column);
        pkBox.pressedProperty().addListener((obs, oldV, newV)->{
            PK = newV;
        });
        pkBox.onActionProperty().addListener((obs, oldV, newV)->{
            System.out.println("Action: "+newV);
        });
        columnName.setPromptText("列名");

        typeOther = new TextField();
        getChildren().addAll(pkBox, columnName, typeOther);
    }
    public boolean isPK() {
        return PK;
    }
    public String getTypeOther() {
        return typeOther.getText();
    }
    public void setTypeOther(String other) {
        typeOther.setText(other);
    }
    public String getColumnName() {
        return columnName;
    }
}

