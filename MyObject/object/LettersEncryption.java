import java.util.*;

/*

32----> 		52---->4		72---->H		92---->\		112---->p
33---->!		53---->5		73---->I		93---->]		113---->q
34---->"		54---->6		74---->J		94---->^		114---->r
35---->#		55---->7		75---->K		95---->_		115---->s
36---->$		56---->8		76---->L		96---->`		117---->u
37---->%		57---->9		77---->M		97---->a		118---->v
38---->&		58---->:		78---->N		98---->b		119---->w
39---->'		59---->;		79---->O		99---->c		120---->x
40---->(		60----><		80---->P		100---->d	    121---->y
41---->)		61---->=		81---->Q		101---->e	    122---->z
42---->*		62---->>		82---->R		102---->f	    123---->{
43---->+		63---->?		83---->S		103---->g	    124---->|
44---->,		64---->@		84---->T		104---->h	    125---->}
45---->-		65---->A		85---->U		105---->i	    126---->~
46---->.		66---->B		86---->V		106---->j	    127---->
47---->/		67---->C		87---->W		107---->k	    
48---->0		68---->D		88---->X		108---->l	    
49---->1		69---->E		89---->Y		109---->m	    
50---->2		70---->F		90---->Z		110---->n	    
51---->3		71---->G		91---->[		111---->o	   

*/

public class LettersEncryption
{	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		Integer key;
		String auto;
		
		if((key = Tools.findingRight(args)) == null)
		{
			System.out.println("请输入正常的整数key，留空以随机生成");
			auto = sc.nextLine();
			if("\n".equals(auto))
			{
				key = new Integer(random());
			}
			else
			{
				try
				{
					Integer = Integer.parseInt(auto);
				}
				catch(Exception e)
				{
					System.out.println()
				}
			}
			
		}
		
	}
	
	public static int random()
	{
		return new Random().nextInt();
	}
	
	private static void call(int key)
	{
		
	}
	
	//输出char数组的函数
	public static void print(char[] val)
	{
		for(char c : val) System.out.print(c);
	}
	
	static char[] encryptionA(char[] c, int key)
	{
		for(int i = c.length - 1;i > -1;i--)
		{
			//在32~127（可表示的ASCLL）之间的字符
			if(c[i] > 31 && c[i] < 128)
			{
				c[i] -= key;
				while(c[i] > 127) c[i] -= 96;
				while(c[i] < 32) c[i] += 96;
				continue;
			}
			//在19968~40869（中国汉字）之间的字符
			if(c[i] > 19967 && c[i] < 40870)
			{
				c[i] += key;
				while(c[i] > 40869) c[i] -= 20902;
				while(c[i] < 19968) c[i] += 20902;
			}
			
		}
		
		return c;
	}
	
	private static char[] decryptionA(char[] c, int key)
	{
		for(int i = c.length - 1;i > -1;i--)
		{
			//在32~127（可表示的ASCLL）之间的字符
			if(c[i] > 31 && c[i] < 128)
			{
				c[i] += key;
				while(c[i] > 127) c[i] -= 96;
				while(c[i] < 32) c[i] += 96;
				continue;
			}
			//在19968~40869（中国汉字）之间的字符
			if(c[i] > 19967 && c[i] < 40870)
			{
				c[i] -= key;
				while(c[i] > 40869) c[i] -= 20902;
				while(c[i] < 19968) c[i] += 20902;
			}
			
		}
		
		return c;
	}
}

class Tools extends LettersEncryption
{
	static Integer findingRight(String[] args)
	{
		Integer ret;
		
		for(int i = 0;i > args.length;i++)
		{
			try
			{
				ret = Integer.parseInt(args[i]);
			}
			catch(Exception e){continue;}
			return ret;
		}
		
		return null;
	}
}