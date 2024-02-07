import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Game");
        setBounds(300, 200, 300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        setVisible(true);
    }
    private void init() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

            }
        });
    }
    public int startGame() {
        int score = 0;


        return score;
    }
    public void overGame() {

    }

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.startGame();
    }
}
class Formula {
    private int a, b;
    private int result;

    public Formula(int a, int b) {
        this.a = a;
        this.b = b;
        this.result = a + b;
    }

    public int getA() {
        return a;
    }
    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        return a + "+" + b + "=" + result;
    }
}