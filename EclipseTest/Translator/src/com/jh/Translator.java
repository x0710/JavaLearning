package com.jh;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Translator {
	private static String ip, port, database, user, password;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e1) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			}
			catch(ClassNotFoundException e2) {
				System.err.println("找不到可用驱动！");
				
			}
		}
		Properties properties = new Properties();
		try {
	//		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary?user=root&password=Gao1124");
			File propertyFile = new File("src/property.properties");
			if(!(propertyFile.exists() && propertyFile.isFile())) {
				propertyFile.createNewFile();
			}
			properties.load(new FileReader(propertyFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ip = properties.getProperty("ip", "localhost");
			port = properties.getProperty("port", "3306");
			database = properties.getProperty("database", "");
			user = properties.getProperty("user", "root");
			password = properties.getProperty("password", "");
	}
	
	static Connection createConnection() throws DriverNotFoundException, SQLException {
		if(!DriverManager.getDrivers().hasMoreElements()) 
			throw new DriverNotFoundException("找不到驱动文件！");
		String url = "jdbc:mysql://"+ip.concat(":".concat(port).concat("/".concat(database)))
			.concat("?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
		return DriverManager.getConnection(url, 
				user, password);
	}

}
