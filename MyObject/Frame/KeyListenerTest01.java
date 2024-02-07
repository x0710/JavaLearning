import java.awt.*;
import java.awt.event.*;

public class KeyListenerTest01
{
	public static void main(String[] args)
	{
		Frame fi = new Frame("Mouse And Key Test-one Input");
		Frame fo = new Frame("Mouse And Key Test-one Output");
		TextArea ta = new TextArea();
		ta.setEditable(false);
		fo.add(ta);
		fi.setBounds(500, 200, 550, 250);
		fo.setBounds(100, 200, 400, 600);
		fi.addKeyListener(new MyKeyAdapter(ta));
		
		fo.setVisible(true);
		fi.setVisible(true);
		fi.addWindowListener(new Close());
		fo.addWindowListener(new Close());
	}
}
class MyKeyAdapter extends KeyAdapter
{
	TextArea ta;
	
	public MyKeyAdapter(TextArea ta)
	{
		this.ta = ta;
	}
	
	public void keyPressed(KeyEvent e)
	{
		ta.append("\n" + (e.getKeyCode() + "---------->" + (char)e.getKeyCode()));
	}
}