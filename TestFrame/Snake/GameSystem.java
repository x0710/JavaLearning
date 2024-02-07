import javax.imageio.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class GameSystem extends JFrame {
	//其他
	Random rd = new Random();
	Image foodImage;
	
	//物质
	private Snake snake;
	private JPanel gameBoard;
	private Food food = null;
	
	//信息
	private int side;
	private int spaceBetween;
	private double snakeMoveSpeed;
	private double fps; // fps
	
	public GameSystem() {
		this(30, 15, 12.0, 60.0);
	}
	public GameSystem(int side, int spaceBetween, double snakeMoveSpeed, double fps) {
		super("Greedy snake");
		this.side = side;
		this.spaceBetween = spaceBetween;
		this.snakeMoveSpeed = snakeMoveSpeed;
		this.fps = fps;
		
		init();
		
		gameBoard = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				if(food == null) {
					food = new Food(rd.nextInt(side - 1), rd.nextInt(side - 1));
				}
				g.setColor(new Color(160, 32, 240));
				for(int i = 0;i <= side;i++) {
					g.drawLine(i * spaceBetween, 0 , i * spaceBetween, spaceBetween * side);
					g.drawLine(0, i * spaceBetween, spaceBetween * side, i * spaceBetween);
				}
				//LinkedList<Note> notesList = snake.getBody();
				g.setColor(Color.YELLOW);
				Iterator<Note> notes = snake.getBody().iterator();
				while(notes.hasNext()) {
					Note note = notes.next();
					g.fillRect(note.getX() * spaceBetween, note.getY() * spaceBetween, spaceBetween, spaceBetween);
					g.setColor(Color.BLACK);
				}
				g.setColor(Color.GREEN);
				g.fillRect(food.getX() * spaceBetween, food.getY() * spaceBetween, spaceBetween, spaceBetween);
				g.drawImage(foodImage, food.getX() * spaceBetween, food.getY() * spaceBetween, spaceBetween, spaceBetween, null);
			}
		};
		add(gameBoard);
	}
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		snake = new Snake();
		snake.setMoveSpace(side, side);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_UP :
						snake.setMoveDirection(Direction.UP);
						break;
					case KeyEvent.VK_DOWN :
						snake.setMoveDirection(Direction.DOWN);
						break;
					case KeyEvent.VK_LEFT :
						snake.setMoveDirection(Direction.LEFT);
						break;
					case KeyEvent.VK_RIGHT :
						snake.setMoveDirection(Direction.RIGHT);
						break;
				}
			}
		});
		try {
			//foodImage = Toolkit.getDefaultToolkit().createImage("Food.png");
			foodImage = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("Potato.png"));
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void judge() throws Exception {
		Note firstNote = snake.getAheadNote();
		if(firstNote == null) 
			return;
		Iterator notes = snake.getBody().iterator();
		if(food.equals(snake.getFirstNote())) {
			food = null;
			snake.eaten();
		}
		while(notes.hasNext()) {
			if(notes.next().equals(firstNote))
				throw new SnakeBitBody();
		}
	}
	void runGame() {
		setBounds(100, 75, 630, 650);
		setVisible(true);
		java.util.Timer flush = new java.util.Timer("Frame rate, refresh rate");
		//java.util.Timer snakeMove = new java.util.Timer("Snake movement speed, difficulty");
		flush.schedule(new TimerTask() {
			public void run() {
				gameBoard.repaint();
			}
		}, 0, (long)(1.0 / fps * 1000));
		/*snakeMove.schedule(new TimerTask() {
			public void run() {
				try{
					snake.move();
					judge();
				}
				catch(Exception e) {
					cancel();
				}
				
			}
		}, 0, (long)(1.0 / snakeMoveSpeed * 1000));
		*/
		try {
			while(true) {
				snake.move();
				judge();
				Thread.sleep((long)(1.0 / snakeMoveSpeed * 1000));
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	public int getSnakeLength() {
		return snake.getSnakeLength();
	}
	public double getSnakeMoveSpeed() {
		return snakeMoveSpeed;
	}
}