import java.io.*;

public class Main
{
	public static void main(String[] args)
	{
		String path = "/storage/emulated/0/Music/";
		Rename r = new Rename(new File(path), "\\s\\[\\w{4}\\]", "", true);
		System.out.println("\r\nover");
	}
}
