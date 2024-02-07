import java.net.*;
import java.io.*;

public class Receive {
	public static void main(String[] args) throws IOException {
		Socket s = new Socket("127.0.0.1", 6666);
		ServerSocket ss = new ServerSocket(6666);
		while(true) {
			Socket ts = ss.accept();
			
			Reader r = new InputStreamReader(ts.getInputStream());
			
			char[] buffer = new char[128];
			
			StringBuilder sb = new StringBuilder(1024);
			
			for(int size;(size = r.read(buffer)) != -1;) {
				sb.append(new String(buffer, 0, size));
			}
			System.out.println(sb);
			r.close();
		}
	}
}