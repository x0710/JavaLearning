package com.core;
import java.sql.Connection;
import java.sql.DriverManager;

import com.core.database.SQLConnection;
import com.core.database.Table;
import com.ui.Columns;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class UI extends Application {
    private static SQLConnection sqlConnection;
    private TextArea sqlArea = new TextArea();
    
    @Override
    public void init() throws Exception {
        super.init();
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        sqlConnection = new SQLConnection("jdbc:mysql://localhost:3306/life?user=root&password=Gao1124", sqlArea);
        sqlArea.setEditable(false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        SQLTableView tableView = new SQLTableView(sqlConnection.execute("SELECT * FROM connectways"));
        FlowPane root = new FlowPane(tableView);
        root.setOrientation(Orientation.VERTICAL);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Connectways表管理工具");
        primaryStage.show();
        

    }
    public static SQLConnection getSQLConnection() {
        return sqlConnection;
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        sqlConnection.close();
    }
    
}
