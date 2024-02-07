import java.util.Random;

public class Percentage extends Random
{
	public boolean percentage(float val)
	{
		return pecimal(val / 100.0);
	}
	public boolean pecimal(double val)
	{
		return nextDouble() < val;
	}
	public Percentage()
	{
		super();
	}
	public Percentage(long seed)
	{
		super(seed);
	}
}