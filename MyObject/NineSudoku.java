import java.util.Scanner;

public class NineSudoku
{
	public static Scanner sc = new Scanner(System.in);
	public static void main(String[] args)
	{
		int nu = sc.nextInt();
		if (other(nu)) return;
		course(nu);
		System.out.println("over");
	}

	public static boolean other(int num)
	{
		if (num % 3 == 0) return false;
		System.out.println("Error");
		return true;
	}

	static void course(int nu)
	{
		int[][] end = new int[3][3];
		end[1][1] = nu / 3;
		final int MAX_NUM = (nu / 3);
		int i = 1;

		//................................
		while (true)
		{
			if (judgment(end,nu))
				{
					print(end);
					
					System.out.println("\t\t\t" + i++);
				}

			if (++end[0][0] > MAX_NUM)
			{
				end[0][1]++;
				end[0][0] = 0;
				if (end[0][1] > MAX_NUM)
				{
					end[0][2]++;
					end[0][1] = 0;
					if (end[0][2] > MAX_NUM)
					{
						end[1][0]++;
						end[0][2] = 0;
						if (end[1][0] > MAX_NUM)
						{
							end[1][2]++;
							end[1][0] = 0;
							if (end[1][2] > MAX_NUM)
							{
								end[2][0]++;
								end[1][2] = 0;
								if (end[2][0] > MAX_NUM)
								{
									end[2][1]++;
									end[2][0] = 0;
									if (end[2][1] > MAX_NUM)
									{
										end[2][2]++;
										end[2][1] = 0;
										if (end[2][2] > MAX_NUM)
										{
											break;
										}
									}
								}
							}
						}
					}
				}
				
			}

		}
		//................................

	}

	public static boolean judgment(int[][] end,int addEnd)
	{
		boolean temp = end[0][0] + end[1][1] + end[2][2] == addEnd && end[0][2] + end[1][1] + end[2][0] == addEnd;
		for (int ij = 0;ij < end.length && temp;ij++)
		{
			temp = (end[ij][0] + end[ij][1] + end[ij][2] == addEnd || end[0][ij] + end[1][ij] + end[2][ij] == addEnd);
		}
		return temp;
	}
	
	public static void print(int[][] val)
	{
		for(int i = 0;i < val.length;i++)
		{
			for(int j = 0;j < val[i].length;j++)
			{
				System.out.print(val[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
