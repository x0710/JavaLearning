import java.io.*;
import java.util.*;

public class Buffered
{
	static String in = "Buffered.java";
	static String out = "Buffered.txt";
	static String writeVal = "Hello World!";
	
	public static void main(String[] args)
	{
		try
		{
			//bufferedReader();
			//bufferedWrite();
			bytesCopy();
			//bufferedInputStream();
			//bufferedOutputStream();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File Not Found");
		}
	}
	
	static void bufferedReader() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(in)));
		String str = null;
		
		while((str = br.readLine()) != null)
		{
			System.out.println(str);
		}
		
		br.close();
	}
	
	static void bufferedWrite() throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
		bw.write("hello\nworld");
		bw.flush();
		bw.close();
	}
	
	static void CharCopy() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(in)));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out, false)));
		
		for(String str = null;(str = br.readLine()) != null;)
		{
			bw.write(str + "\n");
		}
		
		bw.flush();
		
		br.close();
		bw.close();
	}
	
	static void bufferedInputStream() throws IOException
	{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(in));
		
		byte[] val = new byte[1024];
		for(int size = 0;(size = bis.read(val)) != -1;)
		{
			System.out.print(new String(val, 0, size));
		}
		
		bis.close();
	}
	
	static void bufferedOutputStream() throws IOException
	{
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(out, false));
		
		bos.write(writeVal.getBytes());
		
		bos.flush();
		
		bos.close();
	}
	
	static void bytesCopy() throws IOException
	{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(in));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(out,false));
		
		byte[] val = new byte[1024];
		for(int size;(size = bis.read(val)) != -1;)
		{
			bos.write(val, 0, size);
		}
		
		bos.flush();
		
		bis.close();
		bos.close();
	}
}