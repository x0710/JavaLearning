public class Note {
    private int x, y;
    public Note(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof Note) {
            Note note = (Note)obj;
            if(note.getX() == this.x && note.getY() == this.y)
                return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
