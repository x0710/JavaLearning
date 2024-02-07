import java.util.*;
import java.math.*;
import java.io.*;

public class PiCalculation
{
	public static void main(String[] args) throws Exception
	{
		new Nilakantha(50000000L);
		
		//new MonteCarlo(1000000l, (byte)5);
		//new Nilakantha(100L);
		/*
		double x = -0.000001;
		System.out.println(2 * Math.asin(Math.sqrt(1 - x * x)) + Math.abs(Math.asin(x)));
		*/
	}
}
class Nilakantha
{
	Nilakantha(long n) throws Exception {
		
		System.setOut(new PrintStream(new FileOutputStream("PI.log")));
		//System.out.println(getVal(n));
		getVal(n);
		//ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/emulated/0/Pi.cls"));
		//System.out.println((BigDecimal)ois.readObject());
	}
	public BigDecimal getVal(long n)
	{
		BigDecimal pi = new BigDecimal(3);
		
		for(long i = 2;i < n;)
		{
			pi = pi.add(new BigDecimal(4.0/(i * ++i * ++i)));
			pi = pi.subtract(new BigDecimal(4.0/(i * ++i * ++i)));
			//System.out.print(pi);
			//System.out.println(pi);
			System.out.println(pi);
		}
		return pi;
	}
}


class InfiniteSeries
{
	InfiniteSeries(long n)
	{
		System.out.println(getVal(n));
	}
	private double getVal(long n)
	{
		double t = 1;
		
		for(long i = 1;i < n;)
		{
			i += 2;
			t -= 1.0 / i;
			i += 2;
			t += 1.0 / i;
		}
		t *= 4;
		return t;
	}
}


class MonteCarlo
{
	Random r;
	final long m;
	final byte thread;
	ArrayList<Long> al;

	MonteCarlo(long v, byte thread)
	{
		m = v;
		this.thread = thread;
		al = new ArrayList<Long>((int)m*thread);
		r = new Random();
		System.out.println(getVal());
	}
	public Object getVal()
	{
		More[] m = new More[thread];
		for(int i = 0;i < m.length;i++) m[i] = new More();
		for(More t : m) t.start();
		try
		{
			for(More t : m) t.join();
			System.out.println("okok");
		}
		catch(Exception e)
		{
			System.out.println("error");
		}
		Iterator i = al.iterator();
		double v = 0;
		while(i.hasNext())
		{
			v += ((double)((Long)i.next()).longValue()) / (double)this.m;
			//System.out.println(v);
		}
		v /= this.al.size();
		return v * 4;

	}
	class More extends Thread
	{
		public void run()
		{
			double a, b;
			long y = 0;

			for(long n = m;n > 0;n--)
			{
				a = r.nextDouble();
				b = r.nextDouble();
				//if(Math.sqrt(a * a + b * b) <= 1)
				if(a * a + b * b <= 1)
					y++;
				//System.out.println(Thread.currentThread().getName());

			}
			al.add(y);
			System.out.println("over-" + Thread.currentThread().getName());
		}
	}
}
