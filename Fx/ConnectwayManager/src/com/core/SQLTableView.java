package com.core;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import com.core.database.TableLine;
import com.ui.TypeTableCell;
import com.ui.TypeTableCell.TableColumnType;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class SQLTableView extends TableView<TableLine> {
    private LinkedHashSet<String> tags = new LinkedHashSet<>();
    public SQLTableView(ResultSet table) {
        try {
            ResultSetMetaData setData = table.getMetaData();
            final int columns = setData.getColumnCount();
            for(int i = 1;i <= columns;i++) {
                final String name = setData.getColumnName(i);
                final Class<?> typeClass = Class.forName(setData.getColumnClassName(i));
                final TypeTableCell.TableColumnType type;
                if(typeClass.isAssignableFrom(Integer.class)) type = TableColumnType.INTEGER;
                else if(typeClass.isAssignableFrom(Double.class)) type = TableColumnType.DOUBLE;
                else if(typeClass.isAssignableFrom(Date.class)) type = TableColumnType.DATE;
                else if(typeClass.isAssignableFrom(String.class)) type = TableColumnType.TEXT;
                else type = null;
                TableColumn<TableLine, String> col = new TableColumn<>(name);
                col.setCellValueFactory(param->new SimpleStringProperty(param.getValue().getDataByName(name)==null?null:param.getValue().getDataByName(name).toString()));
                col.setCellFactory(param->{
                    return new TypeTableCell<TableLine>(type);
                });
                getColumns().add(col);
            }
            ObservableList<TableLine> array = FXCollections.observableArrayList();
            while(table.next()) {
                TableLine data = new TableLine();
                for(int i = 1;i <= columns;i++) {
                    data.putData(setData.getColumnName(i), table.getString(columns));
                }
                array.add(data);
            }
            setEditable(true);
            getItems().addAll(array);
            setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
