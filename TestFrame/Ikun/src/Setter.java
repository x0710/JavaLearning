import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.Timer;

public class Setter {
    public static final int READLINE = 75;
    private JTextArea jta;
    private static float frame = 25.6f;
    public final FlushFrame flushFrame = new Setter.FlushFrame();

    private Timer timer;
    private FReader reader;

    public Setter(JTextArea jta) {
        this.jta = jta;
		try {
			reader = new FReader();
		}
        catch(Exception e) {
			e.printStackTrace();
		}
    }
    private class FlushFrame extends TimerTask{
        @Override
        public void run() {
            String str = reader.getSetVal();
            if(str == null) {
                cancel();
                return;
            }
            jta.setText(str);
        }
    }
    public void start() {
        timer = new Timer();
        timer.schedule(flushFrame, 0, (int)(1000/frame));
    }

    public void setFrame(float newFrame) {
        if(newFrame > 1000) {
            frame = 1000;
        }
        else if(frame < 0) {}
        else {
            frame = newFrame;
        }
    }
    public double getNowFrame() {
        return frame;
    }

    /**
     * Release the instance.
     */
    public void gcT() {
        try {
            timer.cancel();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
class FReader {
    private LinkedList<String> frame;

    public FReader() throws IOException, ClassCastException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(getClass().getResourceAsStream("ikun"));
        frame = (LinkedList<String>) ois.readObject();
		ois.close();
    }
    public String getSetVal() {
        if(frame.isEmpty())
            return null;
        return frame.pop();
    }
}