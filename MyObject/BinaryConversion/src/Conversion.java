import java.io.*;

public class Conversion
{
	private char[] v;
	public static int speed = 16;
	final byte bits;
	
	private FileReader fr;
	private FileWriter fw;
    private File f;
	final Encoded mod;
	
	//构造器 1.2.0
	public Conversion(String path, Encoded mod) throws IOException
	{
        f = new File(path);
		fr = new FileReader(path);
		this.mod = mod;

		switch(mod)
		{
			case EIGHT:
				bits = 8;
				break;
			case SIXTEEN:
				bits = 16;
				break;
			case TWENTY_FOUR:
				bits = 24;
				break;
			default:
				bits = 0;
				break;
		}
	}
	//1.0.1
	public Conversion(Encoded mod) throws IOException
	{
		this("/temp", mod);
	}
	
	//编码 2.2.1
	public String retBin() throws IOException
	{
		StringBuilder sb = new StringBuilder(8 * 128);
		
		v = new char[speed];
		for(int i;(i = fr.read(v)) != -1;)
		{
			for(int j = 0;j < i;j++)
			{
				sb.append(new String(allConBIN(v[j]).toCharArray()));
			}
		}
		return sb.toString();
	}
	
	//解码 2.2.0
	public String retDec() throws IOException
	{
		StringBuilder sb = new StringBuilder(128);
		StringBuilder temp = new StringBuilder(bits * 2);
		
		v = new char[bits];
		for(int i;(i = fr.read(v)) != -1;)
		{
			if(i != bits) throw new CharacterSetTooSmalException("File is modified");
			for(int j = 0;j < bits;j++)
			{
				temp.append(v[j]);
			}
			sb.append(allConDEC(temp.toString()));
			temp.setLength(0);
		}
		return sb.toString();
	}
	
	//供外部内部使用，DEC转换为BIN
	// 2.1.3
	public String allConBIN(char v)
	{
		String str = Integer.toBinaryString(v);
		String n = "";
		
		for(int i = bits - str.length();i > 0;i--) n = n.concat("0");
		if(str.length() > bits) throw new CharacterSetTooSmalException("Character does not apply the current code");
		
		return n + str;
	}
	//供外部内部使用，BIN转换为DEC
	// 3.0.1
	public char allConDEC(String bin)
	{
		String add = "";
		
		for(int i = bits - bin.length();i > 0;i--) add = add.concat("0");
		bin = add + bin;
		char ret;
		try {
			ret = (char)Integer.parseInt(bin, 2);
		}
		catch (NumberFormatException e) {
			throw new IllegalCharacterException("There are illegal characters in the text");
		}
		return ret;
	}
	
	//关闭流 1.0.0
	public Exception closeStream()
	{
		try
		{
			fw.flush();
			fw.close();
			fr.close();
		}
        catch(Exception e)
        {
            return e;
        }
		return null;
	}
	
	// 1.0.0
	public void writeBin() throws IOException
	{
        fw = new FileWriter(f.getParent() + "/Con" + f.getName(), false);
		fw.write(new String(retBin()).toCharArray());
		fw.flush();
	}
	public void writeDec() throws IOException
	{
        fw = new FileWriter(f.getParent() + "/Con" + f.getName(), false);
		fw.write(new String(retDec()).toCharArray());
		fw.flush();
	}
}
