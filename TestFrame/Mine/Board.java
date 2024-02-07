import java.util.*;

public class Board {
	public static final int MINE = -1;
	//public static final int 
	
	private int[][] board;
	private ArrayList<Mine> mines;
	
	private boolean[][] isCanSee;
	
	// Square side is @
	public Board(int side) {
		if(side <= 0) {
			throw new IllegalArgumentException(side + " Error");
		}
		board = new int[side][side];
		isCanSee = new boolean[side][side];
	}
	public boolean[][] isCanSee() {
		return (boolean[][])isCanSee.clone();
	}
	public int[][] getBoard() {
		return (int[][])board.clone();
	}
	public void setMines(Mine n, int amount) {
		if(amount < 0) {
			throw new IllegalArgumentException(amount + " Error");
		}
		if(amount > board.length*board.length - 1) 
			throw new MinesTooBigException(amount + " more than " + board.length + "'s square");
		mines = new ArrayList<>(amount);
		
		Random r = new Random();
		for(int i = 0;i < amount;) {
			int x = r.nextInt(board.length);
			int y = r.nextInt(board.length);
			Mine m = new Mine(x, y);
			if(!mines.contains(m)) {
				mines.add(m);
				i++;
			}
		}
		
		//生成图像
		for(Iterator it = mines.iterator();it.hasNext();) {
			Point p = (Point)it.next();
			add1L(board, p);
		}
		for(Iterator it = mines.iterator();it.hasNext();) {
			Point p = (Point)it.next();
			board[p.getX()][p.getY()] = MINE;
		}
	}
	//单格周围+1
	private static void add1L(int[][] v, Point t) {
		if(v == null || t == null) 
			throw new IllegalArgumentException();
		int[][] values = {{1, 1}, {1, -1}, {1, 0}, {0, 1}};
		
		for(int i = 0;i < values.length;i++) {
			try{
				v[t.getX()+values[i][0]][t.getY()+values[i][1]]++;
			}
			catch(ArrayIndexOutOfBoundsException e) {}
			try{
				v[t.getX()-values[i][0]][t.getY()-values[i][1]]++;
			}
			catch(ArrayIndexOutOfBoundsException e) {}
		}
	}
	private void setOpenedPoint(Point t) {
		boolean[][] v = isCanSee;
		if(t == null) 
			throw new IllegalArgumentException();
		//System.out.println("their");
		int[][] values = {{1, 1}, {1, -1}, {1, 0}, {0, 1}};
		
		for(int i = 0;i < values.length;i++) {
			try{
				if(!v[t.getX()+values[i][0]][t.getY()+values[i][1]]) {
					v[t.getX()][t.getY()] = true;
					if(board[t.getX()+values[i][0]][t.getY()+values[i][1]] == 0 && !v[t.getX()+values[i][0]][t.getY()+values[i][1]]) 
						setOpenedPoint(new Node(t.getX()+values[i][0], t.getX()+values[i][1]));
					
				}
				
			}
			catch(ArrayIndexOutOfBoundsException e) {}
			try{
				if(!v[t.getX()-values[i][0]][t.getY()+values[i][1]]) {
					v[t.getX()][t.getY()] = true;
					if(board[t.getX()-values[i][0]][t.getY()+values[i][1]] == 0 && !v[t.getX()-values[i][0]][t.getY()+values[i][1]]) 
						setOpenedPoint(new Node(t.getX()-values[i][0], t.getX()+values[i][1]));
					
				}
				
			}
			catch(ArrayIndexOutOfBoundsException e) {}
			System.out.println("there");
		}
	}
	public int openPoint(Point p) {
		int t = board[p.getX()][p.getY()];
		if(t == 0) {
			setOpenedPoint(p);
		}
		isCanSee[p.getX()][p.getY()] = true;
		return t;
	}
	public static void main(String[] args) {
		Board b = new Board(10);
		b.setMines(new Mine(7, 3), 20);
		int[][] t = b.getBoard();
		boolean[][] bt = b.isCanSee();
		
		for(int i = 0;i < t.length;i++) {
			for(int j = 0;j < t[i].length;j++) {
				int tt = t[i][j];
				System.out.print((tt == MINE ? "*":tt)+" ");
			}
			System.out.println();
		}
		for(;;) {
			b.openPoint(new Node(sc.nextInt()-1, sc.nextInt()-1));
			b.printArray(bt);
		}
		
	}
	static Scanner sc = new Scanner(System.in);
	public void printArray(boolean[][] bt) {
		for(int i = 0;i < bt.length;i++) {
			for(int j = 0;j < bt[i].length;j++) {
				boolean tt = bt[i][j];
				System.out.print((tt ? getBoard()[i][j]:"?")+" ");
			}
			System.out.println();
		}
	}
}