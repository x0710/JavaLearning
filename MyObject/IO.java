import java.io.*;

public class IO
{
	public static void main(String[] args) throws IOException
	{
		//创建 读，写 径路
		String pathReader,pathWriter;
		pathReader = "D:\\Hlddz\\Computer\\Documents\\YouthWithoutAPeriodOfTime.txt";
		pathWriter = "D:\\Hlddz\\Computer\\Desktop\\MyObject\\CopyBook.txt";
		
		//创建流
		FileReader fr = new FileReader(pathReader);
		FileWriter fw = new FileWriter(pathWriter,false);
		
		//读取数据
		char[] content = new char[512*1024];
		int actualContent;
		//
		int c = 0;
		
		//读取部分
		while((actualContent = fr.read(content)) != -1)
		{
			//写入部分
			fw.write(content, 0, actualContent);
			c++;
		}
		fw.flush();
		System.out.println(c);
	}
}