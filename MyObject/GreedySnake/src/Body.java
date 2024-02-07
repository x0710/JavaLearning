import java.util.LinkedList;

public class Body {
    private LinkedList<Note> body;
    private Direction moveDirection = Direction.RIGHT;

    public Body(LinkedList<Note> body) {
        this.body = body;
    }
    public Body() {
        body = new LinkedList<Note>();
        body.add(new Note(12, 12));
        body.add(new Note(12, 13));
        body.add(new Note(12, 14));
        body.add(new Note(12, 15));
        body.add(new Note(12, 16));
//        body.add(new Note(12, 17));
//        body.add(new Note(12, 18));
//        body.add(new Note(12, 19));
//        body.add(new Note(12, 20));
//        body.add(new Note(12, 21));
//        body.add(new Note(12, 22));
//        body.add(new Note(12, 23));
//        body.add(new Note(12, 24));
//        body.add(new Note(12, 25));
    }
    public void move() {
        switch (moveDirection) {
            case UP:
                body.addFirst(new Note(getFirstNote().getX(), getFirstNote().getY() - 1));
                break;
            case DOWN:
                body.addFirst(new Note(getFirstNote().getX(), getFirstNote().getY() + 1));
                break;
            case LEFT:
                body.addFirst(new Note(getFirstNote().getX() - 1, getFirstNote().getY()));
                break;
            case RIGHT:
                body.addFirst(new Note(getFirstNote().getX() + 1, getFirstNote().getY()));
                break;
        }
        System.out.println(getFirstNote().getX() + ", " + getFirstNote().getY());
        removeLastNote();
    }

    public void addLastNote() {
        body.addLast(new Note(getLastNote().getX() - 1, getLastNote().getY()));
    }
    private void removeLastNote() {
        body.removeLast();
    }

    public Note getFirstNote() {
        return body.getFirst();
    }
    public Note getLastNote() {
        return body.getLast();
    }

    public void setMoveDirection(Direction direction) {
        this.moveDirection = direction;
    }
    public LinkedList<Note> getBody() {
        return body;
    }
    public Direction getMoveDirection() {
        return moveDirection;
    }
}
