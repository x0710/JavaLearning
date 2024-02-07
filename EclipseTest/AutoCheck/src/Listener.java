import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Listener implements Runnable {
	private Socket user;
	
	public Listener(Socket user) {
		this.user = user;
	}

	@Override
	public void run() {
		while(!Checker.ss.isClosed()) {
			try {
				System.out.println("ThreadStart: "+Thread.currentThread().getName());
				System.setOut(new PrintStream(user.getOutputStream()));
				InputStream isStream = user.getInputStream();
				BufferedReader is = new BufferedReader(new InputStreamReader(isStream));
				for(String size;(size=is.readLine())!= null;) {
					System.out.println("send: "+size);
					Checker.runCommand(size);
				}
				is.close();
				System.out.println("disconnect");
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
