package com.jh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Scanner;

import javafx.application.Application;

public class Main {
	public static final File ERR_FILE = new File("./wrong.log");

	public static void main(String[] args) throws Exception {
		// Process p = Runtime.getRuntime().exec("cmd.exe");

		
		// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		// BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		// new Thread() {
		// 	public void run() {
		// 		try {
		// 			for(String val;(val=br.readLine())!= null;) {
		// 				System.out.println(val);
		// 			}
		// 			br.close();
		// 		} catch (IOException e) {
		// 			// TODO Auto-generated catch block
		// 			e.printStackTrace();
		// 		}
		// 	}
		// }.start();
		// Scanner sc = new Scanner(System.in);
		// for(String val;!"\\q".equals(val=sc.nextLine());) {
		// 	System.out.println(p.isAlive());
		// 	bw.write(val);
		// 	bw.flush();
		// 	bw.newLine();
		// }
		// bw.close();
		// System.out.println(p.isAlive());
		//br.close();
		System.setErr(new PrintStream(ERR_FILE));
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
	public static String exchangeConver(String exchange) {
		StringBuilder sb = new StringBuilder();
		for(String t : exchange.split("/")) {
			if(t.length() <= 1) continue;
			String another = t.replaceAll("^.:", "");
			switch(t.charAt(0)) {
				case 'p' :
					sb.append("过去式：");
					break;
				case 'd' :
					sb.append("过去分词：");
					break;
				case 'i' :
					sb.append("现在分词：");
					break;
				case '3' :
					sb.append("第三人称单数：");
					break;
				case 'r' :
					sb.append("形容词比较级：");
					break;
				case 't' :
					sb.append("形容词最高级：");
					break;
				case 's' :
					sb.append("名词复数形式：");
					break;
				case '0' :
					sb.append("Lemma: ");
					break;
				case '1' :
					sb.append("Lemma 变换形式：");
					break;
			}
			sb.append(another+System.lineSeparator());
		}
		return sb.toString();
	}
}
