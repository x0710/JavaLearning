import java.net.*;
import java.io.*;

public class Send {
	public static void main(String[] args) throws IOException {
		//Socket s = new Socket("127.0.0.1", 6666);
		ServerSocket ss = new ServerSocket(6666);
		Socket ts = ss.accept();
		System.out.println("connected,ip:"+ts.getInetAddress());
		
		OutputStream r = ts.getOutputStream();
		FileInputStream fos = new FileInputStream("./file.txt");
		
		byte[] buffer = new byte[16];
		System.out.println("wait to write");
		for(int size;(size = fos.read(buffer)) != -1;) {
			System.out.println("write: "+new String(buffer, 0, size));
			r.write(buffer, 0, size);
			r.flush();
		}
		
		r.close();
		fos.close();
		System.out.println("over");
}
}