import java.util.*;

public class Tools
{
	/*
	 * 该方法传入一个long的number参数
     * 该方法可将传入的number每个数位转为单个数字
     * 返回一个byte类型的数组
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
	
	/*
	 * 此函数用以打印一维数组每一个数据
	 * 传入int类型数组
	 */
	public static void printArray(int[] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	/*
	 * 此函数用以打印一维数组每一个数据
	 * 传入byte类型数组
	 */
	public static void printArray(byte[] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	/*
	 * 此函数用以打印一维数组每一个数据
	 * 传入short类型数组
	 */
	public static void printArray(short[] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	/*
	 * 此函数用以打印一维数组每一个数据
	 * 传入long类型数组
	 */
	public static void printArray(long[] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	/*
	 * 此函数用以打印一维数组每一个数据
	 * 传入double类型数组
	 */
	public static void printArray(double[] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	/*
	 * 此函数用以打印一维数组每一个数据
	 * 传入float类型数组
	 */
	public static void printArray(float[] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	/*
	 * 此函数用以打印一维数组每一个数据
	 * 传入char类型数组
	 */
	public static void printArray(char[] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	/*
	 * 此函数用以打印一维数组每一个数据
	 * 传入boolean类型数组
	 */
	public static void printArray(boolean[] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	/*
	 * 此函数用以打印一维数组每一个数据
	 * 传入Object类型数组
	 */
	public static void printArray(Object[] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			System.out.print(array[i]+" ");
		}
	}
	
	public static void printArray(int[][] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			for(int j = 0;j < array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printArray(byte[][] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			for(int j = 0;j < array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printArray(short[][] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			for(int j = 0;j < array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printArray(long[][] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			for(int j = 0;j < array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printArray(double[][] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			for(int j = 0;j < array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printArray(float[][] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			for(int j = 0;j < array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printArray(boolean[][] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			for(int j = 0;j < array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printArray(char[][] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			for(int j = 0;j < array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printArray(Object[][] array)
	{
		for(int i = 0;i < array.length;i++)
		{
			for(int j = 0;j < array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	
}