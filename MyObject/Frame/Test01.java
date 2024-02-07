import java.awt.*;
import java.awt.event.*;

public class Test01
{
	public static void main(String[] args)
	{
		MyActionListener mal = new MyActionListener();
		TextField tf = new TextField(30);
		tf.addActionListener(mal);
		Button b = new Button("exit");
		b.setSize(80, 10);
		Frame f = new Frame("Test");
		f.setLayout(new FlowLayout());
		f.add(tf);
		f.add(b);
		f.pack();
		f.setSize(500, 500);
		f.setResizable(!false);
		f.setVisible(true);
		b.addActionListener(mal);
	}
}
class MyActionListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() instanceof Button)
		{
			System.exit(0);
		}
		else if(e.getSource() instanceof TextField)
		{
			TextField tf = (TextField)e.getSource();
			System.out.println(tf.getText());
			tf.setText("OK");
		}
	}
}