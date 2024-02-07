import java.util.*;
import java.sql.*;

public class Login {
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = null;
		
		System.out.print("URL: ");
		String url = sc.next();
		System.out.print("USER: ");
		String user = sc.next();
		System.out.print("PassWord: ");
		String password = sc.next();
		
		try {
			c = DriverManager.getConnection(url, user, password);
		}
		catch(SQLException e) {
			System.out.println("连接失败");
		}
	}
}