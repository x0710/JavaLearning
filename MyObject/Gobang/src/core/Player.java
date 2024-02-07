package core;

public abstract class Player implements Play{
	protected Color color;
	
	public Player() {
	}
	
	void setColor(Color color) {
		this.color = color;
	}
}
