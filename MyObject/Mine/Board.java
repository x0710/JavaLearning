import java.util.*;

public class Board {
	// 棋盘信息
	public static final char mine = '@';
	public static final char unknown = '?';
	private ArrayList<Thunder> thunder;
	private int[][] board;
	
	// Player Message
	private LinkedList<Tick> markMines;
	
	public Board(int a, int b, int thunderVal) {
		board = new int[a][b];
		thunder = new ArrayList<>();
		// flushBoard();
		setThunders(thunderVal);
	}
	public Board() {
		this(10, 10, 10);
	}
	
	public void putLocation(Coordinate c) {
		
	}
	public void markLocation(Coordinate c, Mark mark) {
		
	}
	
	private void setThunders(int num) {
		Random r = new Random();
		
		// 放置雷
		for(;num > 0;) {
			int x = r.nextInt(board.length);
			int y = r.nextInt(board[x].length);
			Thunder t = new Thunder(x, y);
			if(thunder.contains(t)) 
				continue;
			thunder.add(t);
			num--;
		}
		// System.out.println(thunder);
		Iterator<Thunder> it = thunder.iterator();
		
		// 放置数字
		int[] value = {1, -1, 0};
		while(it.hasNext()) {
			Thunder t = it.next();
			for(int j = 0;j < value.length;j++) {
				try {
					board[t.getX() - value[j]][t.getY()]++;
				}
				catch(ArrayIndexOutOfBoundsException aioobe){}
				try {
					board[t.getX()][t.getY() - value[j]]++;
				}
				catch(ArrayIndexOutOfBoundsException aioobe){}
				
				if(value[j] == 0) 
					continue;
				try {
					board[t.getX() + value[j]][t.getY() - value[j]]++;
				}
				catch(ArrayIndexOutOfBoundsException aioobe){}
				try {
					board[t.getX() - value[j]][t.getY() - value[j]]++;
				}
				catch(ArrayIndexOutOfBoundsException aioobe){}
			}
			
		}
		for(it = thunder.iterator();it.hasNext();) {
			Thunder t = it.next();
			board[t.getX()][t.getY()] = -1;
		}
	}


	public String getBoard() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < board.length;i++) {
			for(int j = 0;j < board[i].length;j++) {
				sb.append((board[i][j]>=0 ? board[i][j]+"":mine) + " ");
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < board.length;i++) {
			for(int j = 0;j < board[i].length;j++) {
				sb.append((board[i][j]>=0 ? board[i][j]+"":mine) + " ");
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	/*
	0 0 0
	1 2 1 1
	@ 2 @ 1
	1 2 1 1
	0 0 0 0
	*/
	
	
}
