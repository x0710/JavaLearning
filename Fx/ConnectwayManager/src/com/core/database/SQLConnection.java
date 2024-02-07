package com.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.TextArea;

public class SQLConnection {
    private Connection connection;
    private TextArea managedOutputNode;
    public SQLConnection(String url, TextArea managedOutputNode) throws SQLException {
        this.managedOutputNode = managedOutputNode;
        connection = DriverManager.getConnection(url);
    }
    public ResultSet execute(String sql) throws SQLException {
        managedOutputNode.appendText(sql+System.lineSeparator());
        return connection.createStatement().executeQuery(sql);
    }
    public PreparedStatement preparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
    public void close() throws SQLException {
        connection.close();
    }
}
