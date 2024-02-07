package com.ui;

import java.time.LocalDate;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

public class TypeTableCell<T, K> extends TableCell<T, K> {
    private final TableColumnType type;
    public TypeTableCell(TableColumnType type) {
        this.type = type;
    }
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        updateItem(getUserData().toString(), false);
    }

    @Override
    public void commitEdit(K newValue) {
        super.commitEdit(newValue);
        System.out.printf("提交%s", newValue);
        updateItem(getUserData().toString(), false);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        TextField tf;
        switch(type) {
            case INTEGER:case DOUBLE:
            tf = new TextField((String)getUserData());
            tf.requestFocus();
            tf.setOnAction(value->{
                int val;
                try {
                    val = Integer.parseInt(tf.getText());
                    setUserData(val);
                    commitEdit(tf.getText());
                }
                catch(NumberFormatException e) {
                    cancelEdit();
                }
            });
            setGraphic(tf);
            break;
            case TEXT:
            tf = new TextField((String)getUserData());
            tf.requestFocus();
            tf.setOnAction(value->{
                setUserData(tf.getText());
                commitEdit(tf.getText());
            });
            setGraphic(tf);
            break;
            case DATE:
            DatePicker picker = new DatePicker();
            if(getUserData() != null) picker.setValue(LocalDate.parse((String)getUserData()));
            picker.setOnAction(value->{
                setUserData(picker.getValue());
                commitEdit(picker.getValue().toString());
            });
            setGraphic(picker);
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(empty) return;
        Label contentLabel = new Label("(null)");
        if(item != null) contentLabel.setText(item);
        else contentLabel.setOpacity(0.45);
        try{
            if(type == TypeTableCell.TableColumnType.DATE) contentLabel.setText(LocalDate.parse(item).toString());
        }
        catch(Exception e) {
            contentLabel.setText("");
        }
        setGraphic(contentLabel);
        setUserData(item==null?"":item);
    }
    public static enum TableColumnType {
        INTEGER, TEXT, DOUBLE, DATE;
    }
}
