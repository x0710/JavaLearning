package com.jh.connect;

import java.sql.*;
import java.util.ArrayList;

public class DatabasesManager {
    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private final Connection connection;
    public final String user, ip, port;

    public DatabasesManager(String user, String password) throws SQLException {
        this("localhost", user, password);
    }
    public DatabasesManager(String ip, String user, String password) throws SQLException {
        this(ip, user, password, "3306");
    }
    public DatabasesManager(String ip, String user, String password, String port) throws SQLException {
//        String url = "jdbc:mysql://"+ip+":"+port+"?user="+user+"&password="+password;
        String url = "jdbc:mysql://localhost:3306?user=root&password=Gao1124";
        this.user = user;
        this.ip = ip;
        this.port = port;

        connection = DriverManager.getConnection(url);
//            connection.createStatement().execute();
            /*PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM divideClass WHERE no > ?");
            preparedStatement.setInt(1,600);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }*/
    }

    /**
     * 返回有的数据库
     * 返回使用的数据库中所有的数据库，并以字符串返回
     * @return 数据库字符串表示形式
     */
    public ArrayList<String> getDatabases() {
        ArrayList<String> ret = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SHOW DATABASES");
            while (resultSet.next()) {
                ret.add(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * 返回现在所使用的数据库名称
     * 如果没有正使用的数据库则返回一个空的字符串
     * @return 现在所使用的数据库
     */
    public String getUsingDatabase() {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT DATABASE()");
            if (rs.next()) {
                String r = rs.getString(1);
                return rs.getString(1) == null ? "" : r;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    /**
     * 更改所使用的数据库
     * @param databaseName 要更改为的数据库的名字
     * @return 是否成功更改
     */
    public boolean setUseDatabase(String databaseName) {
        boolean ret = false;
        try {
            ret = connection.createStatement().execute("USE "+databaseName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * 关闭数据库连接，释放资源
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 返回正在使用的数据库中的表
     * @see #getTables(String)
     * @return 数据库的表的名字
     */
    public ArrayList<String> getTables() {
        return getTables(getUsingDatabase());
    }
    /**
     * 返回数据库中的表
     * @param database 要查找的数据库
     * @return 数据库的表的名字
     */
    public ArrayList<String> getTables(String database) {
        ArrayList<String> ret = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SHOW TABLES FROM "+database);
            while (resultSet.next()) {
                ret.add(resultSet.getString(1));
            }
            return ret;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public TableManager createTableManager(String tbname) {
        return new TableManager(this,tbname);
    }

    Connection getConnection() {
        return connection;
    }
}
