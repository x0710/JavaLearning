package players;

import core.*;
import java.util.Scanner;

public class TesterPlayer extends Player
{
	static Scanner sc = new Scanner(System.in);
	@Override
	public Chess play(Board now)
	{
		System.out.println(now);
		System.out.print(color+": ");
		while(true)
			try {
				String[] s = sc.next().split("\\D");
				int y = Integer.parseInt(s[0])-1;
				int x = Integer.parseInt(s[1])-1;
				return new Chess(color, x, y);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}

	
}
