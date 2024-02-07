import java.util.*;
import java.text.*;
import java.io.*;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		String e = new Viewer().toString();
		System.out.println(e);
		/*
		PrintWriter pw = null;
		try{
			StringBuilder sb = new StringBuilder(1024);
			pw = new PrintWriter(new FileOutputStream("/storage/emulated/0/MyTest/log/COVID19.log", true), true);
			sb.append("记录时间：" + new Date(System.currentTimeMillis()) + "\r\n");
			sb.append(e.concat("\r\n"));
			pw.println(sb);
		}
		catch(IOException ioe){ioe.printStackTrace();}
		/*
		Timer t = new Timer("WriteData");
		t.schedule(new TimerTask(){
			public void run() {
				PrintWriter pw = null;
				try{
					StringBuilder sb = new StringBuilder(1024);
					pw = new PrintWriter(new FileOutputStream("/storage/emulated/0/MyTest/log/COVID19.log", true), true);
					sb.append("记录时间：" + new Date(System.currentTimeMillis() + "\r\n"));
					sb.append(new Viewer(false).toString().concat("\r\n"));
					pw.println(sb);
				}
				catch(IOException e){}
				//this.cancel();
				System.out.println("写入了一次");
			}
		}, new Date(System.currentTimeMillis()), 1000*30);
		/*
		System.out.println(new Viewer(true));
		*/
	}
	
}

