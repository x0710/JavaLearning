public abstract class Point {
	protected int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public boolean equals(Object obj) {
		if(obj == null) 
			return false;
		if(obj instanceof Point) {
			Point p = (Point)obj;
			if(p.getX() == x && p.getY() == y) 
				return true;
		}
		return false;
	}
	public int hashCode() {
		int ret = 13;
		ret *= 7;
		ret += x;
		ret *= 7;
		ret += y;
		return ret;
	}
	public String toString() {
		return "Point("+x+", "+y+")";
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
}