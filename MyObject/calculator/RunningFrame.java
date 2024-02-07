package main;
import java.math.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* This class 
*/
public class RunningFrame extends JFrame {
	private JTextField displayWindow = new JTextField("", 12);
	private RunningBackground rb = new RunningBackground();
	
	public RunningFrame() {
		//窗口初始操纵
		super("MyCalculato");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 500, 500);
		setResizable(false);
		
		//组件初始化
		initW();
		initC();
		setVisible(true);
	}
	
	//组件初始化
	private void initC() {
		//displayWindow.setEditable(false);
		//数字框回车监听器
		displayWindow.setBounds(20, 10, 450, 100);
		displayWindow.setFont(new Font("", Font.BOLD, 50));
		displayWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object o = e.getSource(); //获取对象
				if(!(o instanceof JTextField)) 
					return;
				
			}
		});
		
		displayWindow.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				//System.out.println(e.getKeyChar());
				if(e.getKeyChar() == '.' && displayWindow.getText().indexOf(".") == -1) ;
				else if(e.getKeyChar() == '0') {
					System.out.println(displayWindow.getText().matches("0+\\\\.0*"));
					if(displayWindow.getText().matches("[0]*\\\\.[0]*")) {
						e.consume();
					}
				}
				else if(!(e.getKeyChar() >= '1' && e.getKeyChar() <= '9'))
					e.consume();
			}
		});
		
		
	}
	
	//窗口初始化
	private void initW() {
		add(displayWindow);
		
		JButton j1 = new JButton("???");
		j1.setBounds(20, 120, 50, 30);
		//添加监听器
		j1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(j1);
		
		displayWindow.setText(rb.toString());
	}
	
	public static void main(String[] args) {
		new RunningFrame();
	}
}