public class Node extends Point {
	public Node(int x, int y) {
		super(x, y);
	}
	public boolean equals(Node p) {
		if(p == null) 
			return false;
		if(p.getX() == x && p.getY() == y) 
			return true;
		return false;
	}
}