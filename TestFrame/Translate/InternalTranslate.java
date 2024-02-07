import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.awt.*;
import javax.swing.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.util.TimerTask;

public class InternalTranslate {
	public static String youdaoTranslate(String arg) throws IOException {
		URL u = new URL("http://dict.youdao.com/w/" + URLEncoder.encode(arg, "UTF-8") + "/#keyfrom=dict2.top");
		StringBuilder sb = new StringBuilder(1024*8);
		BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream(), "utf-8"));
		
		for(String str;(str = br.readLine()) != null;) {
			sb.append(str + "\n\t");
		}
		
		return sb.toString();
	}
	public static String translate(String val) {
		String str = "";
		try {
			str = youdaoTranslate(val);
		}
		catch(Exception e) {
			System.out.println("Exception---" + e);
		}
		//匹配中文翻译为中文的 <span class="contentTitle"><a class="search-js" href=".*">(.*)</a></span>\s*([\W\w]*?)\s*</p>
		StringBuilder sb = new StringBuilder(512);
		
		Matcher reg = Pattern.compile("<li>(.*?)</li>", Pattern.MULTILINE).matcher(str);
		reg = Pattern.compile("<span style=\"font-weight: bold; color: #959595; margin-right: .5em; width : 36px; display: inline-block;\">(.*)</span>|<span class=\"contentTitle\"><a class=\"search-js\" href=\".*\">(.*)</a>|<li>(.*?)</li>").matcher(str);
		//reg = Pattern.compile("<span class=\"contentTitle\"><a class=\"search-js\" href=\".*\">(.*)</a></span>\\s*([\\W\\w]*?)\\s*</p>").matcher(str);
		for(int i = 1;reg.find();) {
			String temp = null;
			/*
			System.out.println(reg.group(1));
			System.out.println(reg.group(2));
			System.out.println(reg.group(3));
			*/
			for(int j = 1;j <= 3;j++) {
				if((temp = reg.group(j)) != null) {
					System.out.println(temp);
					sb.append(temp + "\n\r");
				}
				//System.out.println();
			}
		}
		//System.out.println(reg.find());
		return sb.toString();
		
		
		
	}
	public static void main(String[] args) {
		new SetFrame();
		//System.out.println(SetFrame.getClipboardString());
	}
}
class SetFrame extends JFrame {
	private JTextArea ta = new JTextArea(50, 20);
	private java.util.Timer t = new java.util.Timer("Flush");
	public SetFrame() {
		super("Translate-youDaoTranslate");
		setBounds(1200, 200, 550, 450);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		init();
		setVisible(true);
		t.schedule(new TimerTask() {
			public void run() {
				start();
			}
		}, 0, 700);
	}
	private void start() {
		String str = getClipboardString();
		if(str == null || str.equals(ta.getText())) 
			return;
		ta.setText(InternalTranslate.translate(str));
	}
	private void init() {
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				start();
			}
		});
		
		//ta.setLineWrap(true); 
		add(new JScrollPane(ta));
	}
	
	public static String getClipboardString() {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // 获取剪贴板中的内容
        Transferable trans = clipboard.getContents(null);

        if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    return text;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}