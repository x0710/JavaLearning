import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Flush {
	static final Properties p = new Properties();
	static Connection c;
	static{
		try{
			p.load(new FileReader("ConnectedData.properties"));
		}
		catch(IOException ioe) {
			System.out.println(ioe);
		}
	}
	static{
		String url = p.getProperty("url");
		String user = p.getProperty("user");
		String password = p.getProperty("password");
		
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			c = DriverManager.getConnection(url, user, password);
		}
		catch(SQLException se) {
			se.printStackTrace();
			c = null;
		}
		
	}
	public static void main(String[] args) throws Exception {
		//System.setOut(new PrintStream(new File("test.txt")));
		selectData();
		//flushData();
	}
	public static void flushData() throws SQLException{
		
		Statement s = c.createStatement();
		//s.executeUpdate("create table test(no int primary key auto_crement,)");
		Random random = new Random();
		for(int i = 0;i < 50000000;i++) {
			int chinese = random.nextInt(100)+1;
			int maths = random.nextInt(100)+1;
			int english = random.nextInt(100)+1;
			int science = random.nextInt(50)+1;
			int total_points = chinese+maths+english+science;
			String name = getRandomChar()+""+getRandomChar();
			if((random.nextInt()&1)==0) 
				name = name+getRandomChar();
			System.out.println(name);
			s.executeUpdate("insert into test(name,chinese,maths,english,science,total_points) values('"+name+"',"+chinese+","+maths+","+english+","+science+","+total_points+")");
		}
		
	}
	private static char getRandomChar() {
		String str = "";
		int hightPos; 
		int lowPos;
		Random random = new Random();
		hightPos = (176 + Math.abs(random.nextInt(39)));
		lowPos = (161 + Math.abs(random.nextInt(93)));
		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(hightPos)).byteValue();
		b[1] = (Integer.valueOf(lowPos)).byteValue();
		try {
			str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str.charAt(0);
  }
	
	public static void selectData() {
		Statement s = null;
		ResultSet rs = null;	
		try{
			s = c.createStatement();
			String sentence = p.getProperty("sentence");
			rs = s.executeQuery(sentence);
			String match = null;
			Matcher m = Pattern.compile("(?<=select\\s).+?(?=\\s)").matcher(sentence);
			if(m.find()) {
				match = m.group();
			}
			if("*".equals(match) || (match == null || match.length()==0)) {
				while(rs.next()) {
					try{
						for(int i = 1;;i++) {
							String data = rs.getString(i);
							if(data == null) 
								data = "null";
						System.out.print(data+((data.length()<=7)?"\t":""));
						}
					}
					catch(SQLException sqlE) {
						System.out.println();
					}
				}
			}
			else{
				String[] part = match.split(",");
				while(rs.next()) {
					for(String pa : part) {
						System.out.print(rs.getString(pa)+"\t");
					}
					System.out.println();
				}
			}
		}
		catch(SQLException se) {
			se.printStackTrace();
		}	
			
		if(s != null) {
			try{
				s.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs != null) {
			try{
				rs.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(c != null) {
			try{
				c.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}