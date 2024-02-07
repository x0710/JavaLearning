public class Launch {
	private GameSystem gs;
	
	public Launch() {
		gs = new GameSystem();
	}
	public Launch(int[] args) {
		gs = new GameSystem(args[0], args[1], args[2], args[3]);
	}
	public String startGame() {
		long startTime, finishTime;
		startTime = System.currentTimeMillis();
		int startLength = gs.getSnakeLength();
		gs.runGame();
		finishTime = System.currentTimeMillis();
		int finishLength = gs.getSnakeLength();
		int length = finishLength - startLength;
		String str = ("The game is over, your final score: " + ((long)(length*0.5*gs.getSnakeMoveSpeed())));
		return str;
	}
	public static void main(String[] args) {
		Launch l = null;
		try{
			if(args.length >= 4) {
				int[] data = new int[4];
				for(int i = 0;i < data.length;i++) {
					data[i] = Integer.parseInt(args[i]);
				}
				l = new Launch(data);
			}
			else {
				l = new Launch();
			}
		}
		catch(Exception e) {
			System.out.println(e);
			l = new Launch();
		}
		System.out.println(l.startGame());
	}
}