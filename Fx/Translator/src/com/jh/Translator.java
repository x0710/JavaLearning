package com.jh;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Translator {
	private static String url;
	private static Properties sign = new Properties();
	static {
		Properties properties = new Properties();
		try {
	//		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary?user=root&password=Gao1124");
			File propertyFile = new File("./property.properties");
			if(!(propertyFile.exists() && propertyFile.isFile())) {
				propertyFile.createNewFile();
			}
			properties.load(new FileReader(propertyFile));
			sign.load(new InputStreamReader(Translator.class.getResourceAsStream("/resources/sign"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			Class.forName(properties.getProperty("driver", "com.mysql.jdbc.Driver"));
		}
		catch(ClassNotFoundException e2) {
			System.err.println("找不到可用驱动！");
		}
		url = properties.getProperty("url");
	}
	
	static Connection createConnection() throws DriverNotFoundException, SQLException {
		if(!DriverManager.getDrivers().hasMoreElements()) 
			throw new DriverNotFoundException("找不到驱动文件！");
		
		return DriverManager.getConnection(url);
	}

	public static String tagConver(String tag) {
		StringBuilder sb = new StringBuilder();
		for(String t : tag.split("\\s")) {
			sb.append(sign.getProperty(t, t)+" ");
		}
		return sb.toString();
	}
}
