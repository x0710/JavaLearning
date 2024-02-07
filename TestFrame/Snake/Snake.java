import java.util.*;

public class Snake {
	private LinkedList<Note> body;
	private Direction moveDircetion;
	
	private boolean hasUsedSetMoveSpace = false;
	private int length, height;
	private boolean hasEaten = false;
	
	public Snake(LinkedList<Note> newBody) {
		this.body = newBody;
	}
	public Snake() {
		LinkedList<Note> defaultBody = new LinkedList<Note>();
		defaultBody.add(new Note(2, 2));
		defaultBody.add(new Note(2, 3));
		this.body = defaultBody;
	}
	
	public void move() throws SnakeOutOfBoundsException{
		if(moveDircetion == null) 
			return;
		
		Note first = getFirstNote();
		
		if(!hasEaten) {
			body.removeLast();
			
		} else {
			hasEaten = false;
		}
		if(hasUsedSetMoveSpace) {
			if((first.getX() >= length || first.getX() < 0)||(first.getY() >= length || first.getY() < 0))
				throw new SnakeOutOfBoundsException(first.getX(), first.getY());
		}
		
		body.addFirst(getAheadNote());
	}
	public Note getAheadNote() {
		Note first = getFirstNote();
		Note note = null;
		if(moveDircetion == null) 
			return note;
		switch(moveDircetion) {
			case UP :
				note = new Note(first.getX(), first.getY() - 1);
				break;
			case DOWN :
				note = new Note(first.getX(), first.getY() + 1);
				break;
			case LEFT :
				note = new Note(first.getX() - 1, first.getY());
				break;
			case RIGHT :
				note = new Note(first.getX() + 1, first.getY());
				break;
			
		}
		return note;
	}
	public void eaten() {
		hasEaten = true;
	}
	public Note getFirstNote() {
		return body.getFirst();
	}
	public void setMoveDirection(Direction newDirection) {
		if(moveDircetion == Direction.UP && newDirection == Direction.DOWN)
			return;
		if(moveDircetion == Direction.LEFT && newDirection == Direction.RIGHT)
			return;
		if(moveDircetion == Direction.DOWN && newDirection == Direction.UP)
			return;
		if(moveDircetion == Direction.RIGHT && newDirection == Direction.LEFT)
			return;
		this.moveDircetion = newDirection;
	}
	public LinkedList<Note> getBody() {
		return body;
	}
	public void setMoveSpace(int length, int height) {
		this.length = length;
		this.height = height;
		hasUsedSetMoveSpace = true;
	}
	public int getSnakeLength() {
		return body.size();
	}
}
