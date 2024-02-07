package com.jh;

import java.io.PrintStream;

import javafx.application.Application;

public class Main {

	public static void main(String[] args) throws Exception {
		// System.setErr(new PrintStream("src/wrong.log"));
		Application.launch(TranslatorUI.class, args);
//		Connection c = Translator.createConnection();
//		PreparedStatement p = c.prepareStatement("select * from life.personal_information where name like ?");
//		p.setString(1, "%?%");
//		ResultSet rs = p.executeQuery();
//		while(rs.next()) {
//			System.out.println(rs.getString(1));
//		}
//		c.close();
	}

}
