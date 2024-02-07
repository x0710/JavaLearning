package ReactionForceTest;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MainFrame extends Frame
{
	private long start, finish;
	private Label speed;
	private boolean isRunning;
	
	public MainFrame()
	{
		super("ReactionForce");
		Thread1 t = new Thread1(this);
		Thread t1 = new Thread(t);
		t1.start();
		setLayout(new BorderLayout());
		Panel p = new Panel(new FlowLayout());
		speed = new Label("null");
		p.add(speed);
		add(p, BorderLayout.SOUTH);
		setVisible(true);
		setBounds(470, 180, 930, 520);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(t.getLoading()) t.runAndStop();
				if(!isRunning) t1.suspend();
			}
		});
		setBackground(Color.YELLOW);
	}
	public Label getSpeed()
	{
		return speed;
	}
	public static double timeGet()
	{
		Random sc = new Random();
		double ret = 1.0;
		ret += (sc.nextInt(4)) / 1.0;
		ret += (sc.nextInt(10)) / 10.0;
		ret += (sc.nextInt(10)) / 100.0;
		ret += (sc.nextInt(10)) / 1000.0;
		return ret;
	}
	public void setStart()
	{
		start = System.currentTimeMillis();
	}
	public long getStart()
	{
		return start;
	}
	public void setFinish()
	{
		finish = System.currentTimeMillis();
	}
	public long getFinish()
	{
		return finish;
	}
	public void nextRunning()
	{
		isRunning = !isRunning;
	}
	public boolean getRunning()
	{
		return isRunning;
	}
}