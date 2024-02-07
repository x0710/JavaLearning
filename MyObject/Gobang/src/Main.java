import players.*;
import core.*;

public class Main
{
	public static void main(String[] args) {
		Color winner = new GameGobang(new TesterPlayer(), new TesterPlayer()).start();
		System.out.println("GAME OVER, THE WINNER IS: "+winner);
		/*
		Collection<Note> c = new HashSet<>();
		Note n1 = new Note(1, 3);
		Chess c1 = new Chess(Color.BLACK, 1, 3);
		Note n2 = new Note(1, 3);
		c.add(n1);
		//c.add(c1);
		System.out.println(c.contains(n2));
		/*
		Collection l = new TreeSet();
		String str = new String("hello");
		boolean b1 = l.add(new String("hello"));
		boolean b2 = l.add(new String("hello"));
		boolean b3 = l.add(str);
		boolean b4 = l.add(str);
		boolean b5 = l.add(new String("zib"));
		l.add(new String("hz"));
		// l.add(null);
		
		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b3);
		System.out.println(b4);
		System.out.println(b5);
		System.out.println(l);
		*/
	}
}
