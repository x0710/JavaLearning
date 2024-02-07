import java.awt.*;
import java.awt.event.*;

public class Close extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
		//((Frame)e.getWindow()).setVisible(false);
	}
}