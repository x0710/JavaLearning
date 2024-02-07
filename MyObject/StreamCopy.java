import java.io.*;
import java.util.*;

public class StreamCopy
{
	public static void main(String[] args) throws IOException
	{
		FileInputStream fis = null;
		FileOutputStream fos = null;
		Scanner sc = new Scanner(System.in);
		String in,out;
		byte[] b; 
		
		System.out.println("请输入复制文件径路");
		in = sc.next();
		System.out.println("请输入复制路径");
		out = sc.next();
		
		try
		{
			fis = new FileInputStream(in);
			fos = new FileOutputStream(out);
			
			{
			System.out.println("请输入速度MB，最高512.0MB");
			double d = sc.nextDouble();
			if(d > 512.0) throw new ReadAndWriteMemoryIsTooLargeException(d + " is too big.");
			if(d < 0) throw new ReadAndWriteTheSizeException(d + " is too small.");
			if(d == 0) throw new ReadAndWriteTheSizeException("Read/write size abnormal.");
			
			b = new byte[(int)(d * 1024 * 1024)];
			}
			
			for(int n;(n = fis.read(b)) != -1;)
			{
				fos.write(b, 0, n);
			}
			
			fos.flush();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("拷贝文件未找到");
		}
		finally
		{
			if(fis != null) fis.close();
			if(fos != null) fos.close();
			if(sc != null) sc.close();
		}
		
	}
}

class ReadAndWriteMemoryIsTooLargeException extends ReadAndWriteTheSizeException
{
	ReadAndWriteMemoryIsTooLargeException(){}
	
	ReadAndWriteMemoryIsTooLargeException(String message)
	{
		super(message);
	}
}

class ReadAndWriteTheSizeException extends RuntimeException
{
	ReadAndWriteTheSizeException(){}
	
	ReadAndWriteTheSizeException(String message)
	{
		super(message);
	}
}