import java.lang.Math;

public class Self_Powered
{
	public static void main(String[] args)
	{
		for(long num = 10000000;num < 100000000;num++)
		{
			if(judge(num)) print(new Long(num));
		}
	}
	
	public static void print(Object o)
	{
		System.out.println(o);
	}
	
	public static boolean judge(long number)
	{
		byte[] bits = assignment(number);
		byte bit = (byte)(number + "").length();
		long added = 0;
		
		for(int i = 0;i < bit;i++)
		{
			added += Math.pow((double)bits[i], (double)bit);
		}
		
		if(added == number) return true;
		
		return false;
	}
	
	/*
	 * 该方法传入一个long的number参数
     * 返回一个byte类型的数组
     * 该方法可将传入的number每个数位转为单个数字
	 * 从0开始，依次向下排列
	 */
	public static byte[] assignment(long number)
	{
		byte[] bits = new byte[(number + "").length()];
		for(int temp = 0, mu = 1;bits.length > temp;)
		{
			bits[temp++] = (byte)((number / mu) % 10);
			mu *= 10;
		}
		
		return bits;
	}
}