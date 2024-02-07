import java.sql.*;
import java.util.*;

public class LoginSystem {
	public static void main(String[] args) throws Exception {
		//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Class.forName("com.mysql.jdbc.Driver");
		String 
			url = "jdbc:mysql://192.168.0.103:3306/learn", 
			user = "root", 
			password = "Gao1124";
		
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println("已连接到"+url);
		Scanner sc = new Scanner(System.in);
		System.out.println("欢迎使用登录系统");
		/*
		System.out.print("账号：");
		String enterAccount = sc.next();
		System.out.print("密码：");
		String enterPassword = sc.next();
		*/
		String enterAccount = "Guest", enterPassword = "3' or '1'='1";
		Statement sm = conn.createStatement();
		ResultSet result = sm.executeQuery("select no from user where account='"+
			enterAccount+"' and password='"+enterPassword+"'");
		
		if(result.next()) {
			System.out.println("登录成功");
		}
		else {
			System.out.println("登录失败");
		}
		ResultSet add = sm.executeQuery("select no,loginTimes from user where account='"
			+enterAccount+"'");
		if(add.next()) {
			String loginTryUser = add.getString("no");
			int updateTimes = add.getInt("loginTimes")+1;
			sm.executeUpdate("update user set loginTimes='"+updateTimes+
				"' where no='"+loginTryUser+"'");
		}
		conn.close();
	}
}