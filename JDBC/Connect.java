import java.sql.*;

public class Connect {
	static Connection c = null;
	public static void main(String[] args) throws Exception {
		
		String user = "root";
		String password = "Gao1022";
		password = "Gao";
		String url = "jdbc:mysql://localhost:3306/calture";
		
		for(int i = 1200;i<=1229;i++) {
			
			try {
				c = DriverManager.getConnection(url, user, password+i);
				c.close();
				System.out.println(i);
			}
			catch(SQLException e) {}
		}
		if(1>0) return;
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			c = DriverManager.getConnection(url, user, password);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		Statement s = null;
		try{
			s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM enwords WHERE word='this'");
			while(rs.next()) {
				String word = rs.getString("word");
				String translation = new String(rs.getString("translation").getBytes("utf8"), "gbk");
				System.out.println("word: "+word);
				System.out.println("translation: "+translation);
				for(char ch : translation.toCharArray()) {
					System.out.print(ch+""+(int)ch+"  ");
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally{
			s.close();
		}
	}
	
	public static void changeDate() throws SQLException {
		Statement s = null;
		s = c.createStatement();
		//s.executeUpdate("update salgrade set hisal=4400 where grade=5");
		//s.executeUpdate("insert into salgrade values(6,4401,6200)");
		ResultSet rs = s.executeQuery("SELECT * FROM enwords WHERE word='this'");
		System.out.println(rs.getString("word"));
		System.out.println(rs.getString("translation"));
		
	}
}