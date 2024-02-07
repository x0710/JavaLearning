import java.util.*;

public class Conversion
{
	static
	{
		System.out.println("欢迎使用进制转换器2.2版本。\n");
	}
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		boolean temp = true;
		
		do
		{
			try
			{
				temp = run();
			}
			catch(Exception e)
			{
				System.out.println("输入有误！");
				return;
			}
		}
		while(temp);
		
		sc.close();
	}
	
	protected static boolean run()
	{
		System.out.print("转换进制：");
		switch(sc.nextInt())
		{
			case 0 : 
				return false;
			case 10 : 
				System.out.print("DEC: ");
				dec(sc.nextInt());
				break;
			case 8 :
				System.out.print("OCT: ");
				oct(sc.next());
				break;
			case 2 :
				System.out.print("BIN: ");
				bin(sc.next());
				break;
			case 16 :
				System.out.print("HEX: ");
				hex(sc.next());
				break;
			default : 
				System.out.println("未知进制");
				System.out.println();
		}
		return true;
	}
	
	public static void dec(int val)
	{
		System.out.println("BIN: " + Integer.toBinaryString(val));
		System.out.println("OCT: " + Integer.toOctalString(val));
		System.out.println("HEX: " + Integer.toHexString(val));
		System.out.println();
	}
	public static void bin(String val)
	{
		int number = Integer.parseInt(val, 2);
		
		System.out.println("DEC: " + number);
		System.out.println("OCT: " + Integer.toOctalString(number));
		System.out.println("HEX: " + Integer.toHexString(number));
		System.out.println();
	}
	public static void oct(String val)
	{
		int number = Integer.parseInt(val, 8);
		
		System.out.println("DEC: " + number);
		System.out.println("BIN: " + Integer.toBinaryString(number));
		System.out.println("HEX: " + Integer.toHexString(number));
		System.out.println();
	}
	public static void hex(String val)
	{
		int number = Integer.parseInt(val, 16);
		
		System.out.println("DEC: " + number);
		System.out.println("BIN: " + Integer.toBinaryString(number));
		System.out.println("OCT: " + Integer.toOctalString(number));
		System.out.println();
	}
}

