import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import javafx.application.Application;
import javafx.util.Builder;
import main.ui.UI;


public class Main {
    public static void main(String[] args) throws Exception {
        System.setErr(new PrintStream("./err.log"));
        Application.launch(UI.class, args);
        // DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        // Connection c = DriverManager.getConnection("jdbc:mysql://localhost/life?user=root&password=Gao1219");
        // int b = c.createStatement().executeUpdate("INSERT INTO tmp VALUES (225610,'历政地',24,4655225610,'程子琦',0,0,0,0,0,0,0,0,0,0,1982,0,0)");
        // c.close();

        // System.out.println(b);
        // PreparedStatement ps = c.prepareStatement("INSERT INTO tmp VALUES (225610,?,24,4655225610,?,0,0,0,0,0,0,0,0,0,0,1982,0,0)");
        // ps.setBytes(1, "历政地".getBytes("UTF-8"));
        // ps.setBytes(2, "历政地".getBytes("UTF-8"));
        // ps.execute();
        // Math.max((new HashMap<Integer, Integer>().get(0)), 21);
        // System.out.println(Integer.parseInt("16387486701897196"));
        // CSVParser s = new CSVParser(new FileReader("C:\\Users\\15940\\OneDrive\\Desktop\\tmp.csv"), CSVFormat.DEFAULT);
        // System.out.println(s.iterator().next());
        // // s.forEach(System.out::print);
        // System.out.println(s.getHeaderNames());
        // for(Iterator<CSVRecord> i = s.iterator();i.hasNext();) {
        //     CSVRecord c = i.next();
        //     c.forEach(System.out::println);
        //     // System.out.println(i.next());
        // }
    }
}
