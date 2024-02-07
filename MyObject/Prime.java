import java.util.*;

public class Prime
{/*
	public static void add()
	{
		long add = 0;
		
		//0~1000
		
		for(int cycles = 1000;cycles > 0;cycles--)
		{
			if(judgePrime(cycles))
			{
				add += cycles;
			}
		}
		
		System.out.println(add);
	}*/
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println(judgePrime(sc.nextInt()));
	}
	
	public static int judgePrime(int number)
	{
		if(number < 2) return -1;
		
		for(int testNum = number - 1;testNum > 1;testNum--)
		{
			if(number % testNum == 0) return testNum;
		}
		
		return -1;
	}
}