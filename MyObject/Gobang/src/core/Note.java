package core;

public class Note
{
	protected int x, y;

	public Note(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) 
			return true;
		if(o instanceof Note) {
			Note oi = (Note)o;
			return oi.x == this.x && oi.y == this.y;
		}
		return false;
	}
	@Override
	public int hashCode() {
		int hash = 7 + x;
		hash *= 13;
		hash += y;
		hash *= 13;
		return hash;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
