public class Mine extends Point {
	public Mine(int x, int y) {
		super(x, y);
	}
	public boolean equals(Point p) {
		if(p == null) 
			return false;
		if(p.getX() == x && p.getY() == y) 
			return true;
		return false;
	}
}