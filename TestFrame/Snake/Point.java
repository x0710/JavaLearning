public abstract class Point {
	protected int x, y;
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj instanceof Point) {
			Point p = (Point)obj;
			if(p.getX() == x && p.getY() == y)
				return true;
		}
		return false;
	}
}