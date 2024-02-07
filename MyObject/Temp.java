import java.util.*;
import javax.sound.sampled.*;
import java.io.*;
import java.applet.*;

public class Temp extends Thread 
{
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception
	{
		Thread t = new Thread2();
		t.start();
		t.join();
		
	}
}
class Thread1 extends Thread
{
	public void run()
	{
		try{
			String s = "F:\\Minecraft 启动器\\数据\\assets\\virtual\\legacy\\music\\hal2.ogg";
			s = "D:\\Hlddz\\Computer\\Desktop\\MyObject\\Pinao\\7 B.wav";
		java.io.File file = new java.io.File(s);
        FileInputStream fis = new FileInputStream(file);
		
		AudioInputStream ais = AudioSystem.getAudioInputStream(file);
		AudioFormat af = ais.getFormat();
		
		Clip c = AudioSystem.getClip();
		c.open(ais);
		c.start();
		c.flush();
		while(c.isActive())
			System.out.println(c.getLongFramePosition() );
		}catch(Exception e) {System.exit(1);}
		/*
		BufferedInputStream stream = new BufferedInputStream(fis);
		Player player = new Player(stream);
		player.play();
		player.close();
		*/
	}
}class Thread2 extends Thread
{
	public void run()
	{
		try {
			AudioClip ac = Applet.newAudioClip(new java.io.File("D:\\Hlddz\\Computer\\Desktop\\MyObject\\Pinao\\7 B.wav").toURI().toURL());
			//System.out.println(new java.io.File("D:\\Hlddz\\Computer\\Desktop\\piano3.wav").toURI().toURL());
			Thread.sleep(1000);
			ac.play();
			
			
		}catch(Exception e) {
			System.exit(1);
		}
		//while(true);
		//System.out.println("over");
	}
}