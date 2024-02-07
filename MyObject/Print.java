import java.io.*;

public class print
{
	public static void main(String[] args) throws IOException
	{
		System.setOut(new PrintStream(new FileOutputStream("PrintLog.log", true)));
		
		System.out.println("Hello World!");
		
		//PrintStream ps = new PrintStream();
		
		System.out.println("look log");
	}
}