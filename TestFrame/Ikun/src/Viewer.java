import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Viewer extends JFrame {
	private JTextArea jta;
	private Setter setter;

	public Viewer() {
		super("Ikun");
		setBounds(200, 0, 1600, 1020);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		BufferedImage bi = null;
		try {
			bi = ImageIO.read(this.getClass().getResourceAsStream("title"));
		}
		catch (Exception ioe) {
			ioe.printStackTrace();
		}
		setIconImage(bi);
		setter = new Setter(jta);

		init();

		setVisible(true);
//		pack();
	}
	private boolean first = true;
	private void init() {
		jta = new JTextArea();
		jta.setFont(new Font("黑体", Font.PLAIN, 10));
		jta.setEditable(false);

		JPanel jp1 = new JPanel(new GridLayout(10, 1));
		JTextField frame = new JTextField();
		JButton jb1 = new JButton("START!!!");
		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(first) {
					setter.start();
					first = false;
				}
				setter.gcT();
				setter = new Setter(jta);
				setter.start();
				frame.setText(setter.getNowFrame()+"");
			}
		});
		JButton jb2 = new JButton("exit");
		jb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JPanel jp2 = new JPanel(new GridLayout(2, 1));
		JButton jb3 = new JButton("set");
		jb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setter.setFrame(Float.parseFloat(frame.getText()));
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
				frame.setText(setter.getNowFrame()+"");
			}
		});
//
//		JPanel jp4 = new JPanel(new GridLayout(2, 1)); // start and stop
//		JButton jp4_start = new JButton("restore");
//		jp4_start.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				setter.notifyTimer();
//			}
//		});
//		JButton jp4_stop = new JButton("restore");
//		jp4_start.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				setter.waitTimer();
//			}
//		});
		//监视框
//		jp4.add(jp4_start);
//		jp4.add(jp4_stop);

		//信息框
		jp2.add(frame); // 帧率
		jp2.add(jb3); // 设置帧率

		//状态栏

		jp1.add(jb1); // start
		jp1.add(jb2); // exit

		jp1.add(jp2); // 信息框
//		jp1.add(jp4); // 监视框

		//父容器
		add(jp1, BorderLayout.EAST);
		add(jta, BorderLayout.CENTER);

	}

}