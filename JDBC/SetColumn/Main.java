import java.sql.*;
import java.util.Scanner;

// 多长时间没有用Notpad写代码了
public class Main {
	public static void main(String[] args) throws SQLException {
		Connection c = null;
		
		String url = "jdbc:mysql://localhost:3306/life";
		String user = "root";
		String password = "Gao1124";
		
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			c = DriverManager.getConnection(url, user, password);
			Scanner sc = new Scanner(System.in);
			
			Statement s = c.createStatement();
			String sql = "SELECT name,feeling FROM connectways WHERE feeling IS NOT NULL";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				/*if(rs.getString("feeling") != null) {
					System.out.println(rs.getDouble("feeling"));
					continue;
				}*/
				String name = rs.getString("name");
				System.out.print(name + " 的feeling=");
				double feelingValue = sc.nextDouble();
				PreparedStatement ps = c.prepareStatement("UPDATE connectways SET feeling=? WHERE name=?");
				ps.setDouble(1, feelingValue);
				ps.setString(2,name);
				ps.execute();
				boolean successful = ps.getUpdateCount()==1;
				System.out.println(successful?"成功":"失败");
				//System.out.println(ps.getUpdateCount());
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			
		}
		finally {
			c.close();
		}
	}
}