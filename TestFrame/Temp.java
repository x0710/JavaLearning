import java.io.*;

public class Temp implements Serializable
{
	File f = new File("TestFile.txt");
	
	public static void main(String[] args) throws Exception
	{
		for(int i = 0;i < 4000;i++) {
			Thread th = new Thread(i+"");
			System.out.println(th);
		}
		Thread.sleep(60*1000);
		/*
		FileWriter wr = new FileWriter("TestFile.txt", false);
		for(int i = Integer.MAX_VALUE;i > Integer.MIN_VALUE;i--)
		{
			if(i % 20 == 0) wr.write("\n".toCharArray());
			wr.write((i + " ").toCharArray());
		}
		wr.flush();
		*/
		//FileReader fr = new FileReader("TestFile.txt");
		//char[] size = new char[1 * 1024 * 1024];
		//for(int i;(i = fr.read(size)) != -1;)
		//{
		//	System.out.print(new String(size));
		//}
		//fr.close();
	}
}