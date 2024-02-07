package core;
import java.util.*;

public class Board implements Cloneable
{
	public static final String WHITE = "○", 
		BLACK = "●", NULL = "×";
	
	private final int length, width;
	private final Set<Chess> pieces;
	
	public Board(int length, int width) {
		this.length = length;
		this.width = width;
		pieces = new HashSet<>();
	}
	/**
	* 放置一个棋子
	* @param piece 被放置的棋子
	* @return 是否放置成功
	*/
	public boolean setChess(Chess piece) {
		if(piece.getY() < 0 || piece.getX() < 0) 
			return false;
		if(piece.getX() > length || piece.getY() > width) 
			return false;
		return pieces.add(piece);
	}
	public boolean hasChess(Note piece) {
//		System.out.println(pieces.contains(piece));
		return pieces.contains(piece);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < length;i++) {
			for(int j = 0;j < width;j++) {
				Chess ctW = new Chess(Color.WHITE, i, j);
				Chess ctB = new Chess(Color.BLACK, i, j);
				if(pieces.contains(ctW)) {
					sb.append(WHITE+" ");
				}
				else if(pieces.contains(ctB)) {
					sb.append(BLACK+" ");
				}
				else {
					sb.append(NULL + " ");
				}
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
}
