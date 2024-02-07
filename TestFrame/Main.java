import java.io.*;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test.jvav"));
		oos.writeObject(new Temp());
		oos.flush();
	}
}