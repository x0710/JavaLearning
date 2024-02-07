package com.jh.ui;

import com.jh.connect.Field;
import com.jh.connect.TableManager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableUI extends Stage {
    public static final int SHOW_LINES = 35;

    private final TableManager table;
    private final SelectTable creator;

    public TableUI(TableManager table, SelectTable creator) {
        this.table = table;
        this.creator = creator;
        InstructionBar instructionBar = new InstructionBar(creator.getProgramSystem());
        instructionBar.flushUI();

        Button back = new Button("选择数据库");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
                creator.show();
            }
        });
        
        ResultSet rs = table.getAllData();
        ArrayList<Field> fields = table.getFields();
        ObservableList<ArrayList<String>> obs = FXCollections.observableArrayList();
        try {
			while(rs.next()) { // 加载数据
				ArrayList<String> d = new ArrayList<>(fields.size());
				for(Field field : fields) {
					d.add(rs.getString(field.getField()));
				}
				obs.add(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        TableView<ArrayList<String>> data = new TableView<>(obs);
        
        for(Field f : fields) { // 标题
        	TableColumn<ArrayList<String>, String> tc = new TableColumn<>(f.getField());
        	tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ArrayList<String>,String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<ArrayList<String>, String> param) { //  放置数据
					int index = obs.indexOf(param.getValue());
					return new SimpleStringProperty (obs.get(index).get(fields.indexOf(f)));
				}
			});
        	data.getColumns().add(tc);
        }
        
//        GridPane dataField = new GridPane();
//        dataField.setHgap(20);
//        dataField.setVgap(8);
//        ArrayList<GridPane> pages = new ArrayList<>();
//        // 将表的fields显示
//        int i1 = 0;
//        for(Field field : fields) {
//            dataField.add(new Text(field.getField()), i1++, 0);
//        }
//        // 加载数据
//        try {
//            int pageInt = 0;
//            GridPane data = new GridPane();
//            data.setHgap(20);
//            data.setVgap(8);
//
//            for(int col = 1;rs.next();col++) { // 以行加载
//                int i2 = 0; // 以列加载
//                for(Field field : fields) {
//                    data.add(new Text(rs.getString(field.getField())), i2++, col); //
////                    System.out.println(field.getField());
//                }
//
//                if(pageInt++ == SHOW_LINES) {
//                    pages.add(data);
//                    pageInt = 0;
//                    data = new GridPane();
//                    data.setHgap(20);
//                    data.setVgap(8);
//                    col = 1; // 添加到布局的下标归零
//                }
//            }
//            if(pageInt != 0) {
//                pages.add(data);
//            }
//        }
//        catch (SQLException e) {
//            new ErrorWindow("未知错误", e).show();
//            e.printStackTrace();
//        }
//        System.out.println("数据加载完成");
//        System.out.println(pages.size());
//
//        Pagination pagination = new Pagination(pages.size(), 0); // 分页系统
//        pagination.setPageFactory(new Callback<Integer, Node>() {
//            @Override
//            public Node call(Integer integer) {
//                int i = 0;
//                for(GridPane gridPane : pages) {
//                    if(i++ == integer) {
//                        return gridPane;
//                    }
//                }
//                return null;
//            }
//        });

        VBox root = new VBox(instructionBar, back, data);
        setScene(new Scene(root));
        creator.close();
        setTitle("访问的表："+table.getName());

        setMaxHeight(Screen.getPrimary().getBounds().getMaxY());
        setMaxWidth(Screen.getPrimary().getBounds().getMaxX());
        show();
    }

}
