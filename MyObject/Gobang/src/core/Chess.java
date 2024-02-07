package core;

public class Chess extends Note implements Cloneable
{
	private final Color color;

	public Chess(Color color, int x, int y) {
		super(x, y);
		this.color = color;
	}

	@Override
	protected Chess clone() {
		try {
			return (Chess)super.clone();
		}
		catch(CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public boolean equals(Object o) {
		if(!super.equals(o)) 
			return false;
		if(o instanceof Chess) {
			Chess c = (Chess)o;
			return c.color == this.color;
		}
		return false;
	}
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash += color.ordinal();
		return hash;
	}
	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return color + "(" + x + ", " + y + ")";
	}
}
