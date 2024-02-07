import java.awt.*;

public class Thread1 implements Runnable
{
	MainFrame mf;
	
	boolean exit = true;
	boolean loading = false;
	
	public Thread1(MainFrame f)
	{
		this.mf = f;
	}
	public void run()
	{
		while(exit)
		{
			try
			{
				Thread.currentThread().resume();
				mf.setBackground(Color.BLUE);
				loading = false;
				Thread.sleep((int)(MainFrame.timeGet() * 1000.0));
				loading = true;
				mf.setBackground(Color.RED);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			
			runAndStop();
		}
		
	}
	public void runAndStop()
	{
		if(mf.getRunning())
		{
			mf.setFinish();
			mf.getSpeed().setText("speed: " + (mf.getFinish() - mf.getStart()));
			
		}
		else
		{
			//开始
			mf.setStart();
		}
		mf.nextRunning();
	}
	public boolean getLoading()
	{
		return loading;
	}
}