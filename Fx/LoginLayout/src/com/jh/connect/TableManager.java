package com.jh.connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class TableManager {
    private final DatabasesManager manager;
    private final Connection connection;
    
    private String tableName;
    private ArrayList<Field> fields;

    /**
     * 一个表的封装类
     * 构造方法没有进行安全检查，table应在DatabaseManager.getTables()存在
     * @param manager 调用者
     * @param tableName 打开的表
     */
    TableManager(DatabasesManager manager, String tableName) {
        this.manager = manager;
        this.tableName = tableName;
        fields = new ArrayList<>(6);
        connection = manager.getConnection();
        
        try {
			ResultSet resultSet = connection.createStatement().executeQuery("DESCRIBE "+tableName);
			while(resultSet.next()) {
				String field = resultSet.getString("Field"),
						type = resultSet.getString("Type"),
						pk = resultSet.getString("Key"),
						defaultString = resultSet.getString("Default"),
						extra = resultSet.getString("Extra");
				boolean beNull = resultSet.getBoolean("Null");
				fields.add(new Field(type, field, beNull, pk, defaultString, extra));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    /**
     * 得到表中所有数据
     * @return 数据结果
     */
    public ResultSet getAllData() {
        try {
            return connection.createStatement().executeQuery("SELECT * FROM "+ tableName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 得到该表的字段名
     * @return 该表的字段名
     */
    public ArrayList<Field> getFields() {
		/*
		 * ArrayList<String> arrayList = new ArrayList<>(); try { ResultSet rs =
		 * connection.createStatement().executeQuery("DESC "+ tableName); for(int i =
		 * 0;rs.next();i++) { arrayList.add(rs.getString("Field")); } } catch
		 * (SQLException throwables) { throwables.printStackTrace(); } return arrayList;
		 */
    	return fields;
    }

    /**
     * 执行更新语句
     * limit中的键值对应为field,>=value，如WHERE a > 60可以写为a,>60
     * @param limit 语句限制条件
     * @param field 更新的属性
     * @param toValue 更新的值
     * @return 是否更新成功
     */
    public boolean updata(Map<String, String> limit, String field, String toValue) {
        boolean ret = false;
        StringBuilder sql = new StringBuilder("UPDATA " + tableName + " SET " + field+" = '"+toValue+"'");
        if(limit.size() > 0) {
            sql.append(" WHERE");
            for(Field f : fields) {
                String l = limit.get(f);
                sql.append(" ").append(f);
                sql.append(" ").append(l);
            }
        }
        System.out.println(sql.toString());
        /*try {
            ret = connection.createStatement().execute(sql.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        return ret;
    }

    /**
     * 获取该{@link TableManager}所可访问的表名字
     * @return 该表的String表示形式
     */
    public String getName() {
        return tableName;
    }
    DatabasesManager getDatabasesManager() {
    	return manager;
    }
}

