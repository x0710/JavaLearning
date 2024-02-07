import java.io.*;

public class CharTxt
{
	public static void main(String[] args) throws Exception
	{
		char c = 0;
		System.setOut(new PrintStream(new FileOutputStream("CharList.txt", false)));
		while(c < Character.MAX_VALUE) System.out.println((int)c + "---->" + c++);
	}
}