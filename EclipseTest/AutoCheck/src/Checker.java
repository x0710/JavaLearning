import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Checker {
	static ServerSocket ss;
	private static PrintStream defaultPrintStream = System.out;

	public static void main(String[] args) throws AWTException, IOException {
		ss = new ServerSocket(9877);
		SystemTray st = SystemTray.getSystemTray();
		PopupMenu pm = new PopupMenu("菜单");
		MenuItem mi1 = new MenuItem("EXIT");
		mi1.addActionListener(al->{
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		});
		MenuItem mi2 = new MenuItem("RECOVER");
		mi2.addActionListener(al->{
			System.setOut(defaultPrintStream);
		});
		pm.add(mi1);
		pm.add(mi2);
		st.add(new TrayIcon(Toolkit.getDefaultToolkit().createImage("https://i.52112.com/icon/jpg/256/20190424/37588/1788141.jpg"), "远程管理工具", pm));
		
		new Thread(()->{
			while(!ss.isClosed()) {
				try {
					Socket connect = ss.accept();
					System.out.println("connected: "+connect.getInetAddress());
					Thread t = new Thread(new Listener(connect));
					t.setName(connect.getInetAddress()+"-thread");
					t.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	public static void runCommand(String command) {
		if("exit".equals(command)) {
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
			
		}
		try {
			Robot robot = new Robot();
			try {
				int mode = Integer.parseInt(command);
				int btn;
				switch(mode) {
					case 1 : 
						btn = InputEvent.BUTTON1_DOWN_MASK;
						break;
					case 2 : 
						btn = InputEvent.BUTTON2_DOWN_MASK;
						break;
					case 3 : 
						btn = InputEvent.BUTTON3_DOWN_MASK;
						break;
					default : return;
				}
				robot.mousePress(btn);
				robot.mouseRelease(btn);
				return;
			}
			catch(NumberFormatException e) {
//				System.out.println("Wrong input: "+command);
			}
			if(command.matches("^4.*$")) {
				for(char c : command.replaceAll("^\\d", "").toCharArray()) {
					int key = KeyEvent.getExtendedKeyCodeForChar(c);
					robot.keyPress(key);
					robot.keyRelease(key);
				}
			}
			
			String[] split = command.split("\\D");
			if(split.length == 2) {
				int x = Integer.parseInt(split[0]), y = Integer.parseInt(split[1]);
				robot.mouseMove(x, y);
			}
			else {
				if("shutdown".equalsIgnoreCase(command)) Runtime.getRuntime().exec("shutdown -s -t 30");
//				Process p = Runtime.getRuntime().exec(command);
//				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//				for(String val;(val=br.readLine()) != null;) {
//					System.out.println("Console: "+val);
//				}
			}
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
