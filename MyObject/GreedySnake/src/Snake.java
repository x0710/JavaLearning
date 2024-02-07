import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Snake extends JFrame {
    int boardSize = 15;
    int squareSize = 40; //Pixel
    JPanel checkerboard;
    Body snakeBody;
    double frame = 60.0; //fps
    int snakeSpeed = 15; //blocks

    public Snake() {
        super("Greedy snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 150, 700, 700); // 设置大小

        snakeBody = new Body();
        initGamePanel(boardSize, squareSize);

        setVisible(true);
        initGameData(); // 初始化数据

    }

    /**
     * @param widthAndHeight 小格边长
     * @param size 网格多少
     */
    private void initGamePanel(int widthAndHeight, final int size) {
        checkerboard = new JPanel() {
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLACK);
                for(int xy = 0;xy <= size;xy++) {
                    g.drawLine(0, xy * widthAndHeight, widthAndHeight * size, xy * widthAndHeight);
                    g.drawLine(xy * widthAndHeight, 0, xy * widthAndHeight, widthAndHeight * size);
                }
                g.setColor(Color.RED);
                for(Note note : snakeBody.getBody()) {
                    g.fillRect(note.getX() * widthAndHeight, note.getY() * widthAndHeight, widthAndHeight, widthAndHeight);
                    g.setColor(Color.BLACK);
                }

            }
        };
        add(checkerboard);
    }

    private void initGameData() {
        //刷新（帧）
        Timer flush = new Timer();
        flush.schedule(new TimerTask() {
            @Override
            public void run() {
                checkerboard.repaint();
            }
        }, 0, (long)(1000 / frame));

        //移动速度（游戏难度）
        Timer snakeSpeed = new Timer();
        snakeSpeed.schedule(new TimerTask() {
            @Override
            public void run() {
                snakeBody.move();
            }
        }, 0, 1000 / this.snakeSpeed);

        //键盘监听
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                switch(e.getKeyCode()) {
                    case KeyEvent.VK_UP : // 38,
                        snakeBody.setMoveDirection(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN :
                        snakeBody.setMoveDirection(Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT :
                        snakeBody.setMoveDirection(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT :
                        snakeBody.setMoveDirection(Direction.RIGHT);
                        break;
                }
            }
        });
    }

}
