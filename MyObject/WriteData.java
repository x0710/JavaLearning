import java.io.*;

public class WriteData {
	static long n = Long.MIN_VALUE;
	public static void main(String[] args) throws IOException {
		Writer w = new FileWriter("H:\\test.TXT");
		Thread t = new Thread(()->{
			try {
				Thread.sleep(5*1000);
				System.out.println(n);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		});
		for(;n < Long.MAX_VALUE;n++) {
			w.write('淦');
			w.flush();
		}
		w.close();
	}
}