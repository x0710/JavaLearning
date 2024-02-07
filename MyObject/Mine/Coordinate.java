public abstract class Coordinate {
	protected int x, y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object obj) {
		if(obj == this) 
			return true;
		if(obj instanceof Coordinate) {
			Coordinate ct = (Coordinate)obj;
			
			return (ct.getX() == x && ct.getY() == y);
		}
		return false;
	}
	@Override
	public int hashCode() {
		int n = 17;
		n = n * 11 + x;
		n = n * 11 + y;
		return n;
	}
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	// setter And getter.
	public final int getX() {
		return x;
	}
	public final int getY() {
		return y;
	}
	public final void setX(int x) {
		this.x = x;
	}
	public final void setY(int y) {
		this.y = y;
	}
	
}