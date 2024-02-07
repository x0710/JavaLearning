import java.awt.*;
import java.awt.event.*;

public class Test02
{
	
	public static void main(String[] args)
	{
		TextField input1 = new TextField(5);
		TextField input2 = new TextField(5);
		TextField output = new TextField(10);
		Frame f = new Frame("Test-Two");
		f.setVisible(true);
		f.addWindowListener(new Close());
		f.setBounds(50, 50, 350, 100);
		Button toCalculate = new Button("计算");
		output.setEditable(false);
		toCalculate.addActionListener(new MyActionListener(input1, input2, output));
		f.setLayout(new FlowLayout(FlowLayout.LEFT));
		f.add(input1);
		f.add(new Label("+"));
		f.add(input2);
		f.add(toCalculate);
		f.add(output);
	}
}

class MyActionListener implements ActionListener
{
	TextField tf1;
	TextField tf2;
	TextField tf3;
	
	public MyActionListener(TextField tf1, TextField tf2, TextField tf3)
	{
		this.tf1 = tf1;
		this.tf2 = tf2;
		this.tf3 = tf3;
	}	
	public void actionPerformed(ActionEvent e)
	{
		double f = Integer.parseInt(tf1.getText());
		double s = Integer.parseInt(tf2.getText());
		
		tf1.setText("");
		tf2.setText("");
		
		tf3.setText((f + s) + "");
	}
}