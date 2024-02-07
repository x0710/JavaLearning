public class SnakeOutOfBoundsException extends Exception {
	public SnakeOutOfBoundsException() {}
	public SnakeOutOfBoundsException(String message) {
		super(message);
	}
	public SnakeOutOfBoundsException(int x, int y) {
		this("The snake is in position (" + x +", " + y + "), beyond the demarcated boundary");
	}
}