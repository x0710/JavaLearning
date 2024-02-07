package core;

import static core.Color.*;

public class GameGobang
{
	public static final int WIN = 5;

	private Board board;
	private Player white, black;

	public GameGobang(Player player1, Player Player2) {
		this(player1, Player2, new Board(15, 15));
	}
	public GameGobang(Player player1, Player player2, Board board) {
		white = player1;
		player1.setColor(WHITE);
		black = player2;
		player2.setColor(BLACK);
		this.board = board;
	}
	public Color start()
	{
		while (true)
		{
			Chess setChess;
			while (true)
			{
				Player playPlayer = getPlayerByColor(this.playPlayer);
				setChess = playPlayer.play(board);
				if (board.setChess(setChess))
				{
					reverse();
					break;
				}
			}
			Color winner = hasWinner(setChess);
			if (winner != null) 
				return winner;
		}
	}
	private Color hasWinner(Chess d)
	{
		int[][] n = {{1,1},{1,0},{0,1},{1,-1}};
		for (int[] ints : n) {
			for (int anInt : ints) {
				Chess ic = d.clone(); // max value
				Chess ict = d.clone(); // index
				for (int r = 0; board.hasChess(ict); r++) {
					ic = ict;
					ict = new Chess(d.getColor(),
							ict.getX() - anInt,
							ict.getY() - anInt);
					if (r >= WIN)
						return d.getColor();
				}
				ict = ic.clone();
				for (int r = 0; board.hasChess(ict); r++) {
					ict = new Chess(d.getColor(),
							ict.getX() + anInt,
							ict.getY() + anInt);
					if (r >= WIN)
						return d.getColor();

				}
			}
		}
		return null;
	}

	private Player getPlayerByColor(Color color)
	{
		if (color == null) 
			return null;
		if (color == BLACK) 
			return black;
		else
			return white;
	}
	private Color playPlayer = BLACK;
	private void reverse()
	{
		playPlayer = (playPlayer == BLACK) 
			? WHITE : BLACK;
	}
}
