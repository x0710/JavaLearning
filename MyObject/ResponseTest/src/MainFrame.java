import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class MainFrame extends Frame{
    // isRunning 值为是否处于测试阶段
    private boolean isRunning = false;
    // start和finish分别为测试前和测试后的毫秒数
    private long start, finish;
    private Thread1 t = new Thread1(this);
    final MyLabel l = new MyLabel();

    public MainFrame() {
        // Frame 初始化操纵
        super("Response Test");
        setVisible(true);
        setBounds(100, 100, 600, 500);
        //设置布局及添加组件
        setLayout(new BorderLayout());
        add(l, BorderLayout.SOUTH);
        setBackground(Color.BLUE);
        l.setStatus(Status.INITIAL);
        // 线程添加及启动
        t.start();
        // 注册监听器
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                process();
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                process();
            }
        });
    }
    private void process() {
        // 如果它正处于测试中（isRunning）
        // 如果它还未加载图片（pictureReally）
        // 则本次点击不记录
        if(isRunning && t.getPictureReally()) {
            // 结束阶段
            // 记录结束时间
            recordFinishTime();
            // 初始化
            t.setPictureReallyF();
            isRunning = false;
            setBackground(Color.BLUE);
            // 测试结果打印到控制台和标签栏
            l.setText("" + (finish - start), Status.RESULTS);
            System.out.println(finish - start);
            return;
        }
        // 没有处于测试阶段
        setBackground(Color.YELLOW);
        l.setNextStatus();
        t.resume();
        isRunning = true;

        System.out.println(Thread.currentThread().getName() + "-->49");
    }

    // 获取简单时间函数
    /* 该函数可随机生成：
    *  1.0~4.999任意数字
    *  并返回
    *  使用java.util.Random伪随机生成
    * */
    public static double timeGet() {
        Random sc = new Random();
        double ret = 1.0;
        ret += (sc.nextInt(4)) / 1.0;
        ret += (sc.nextInt(10)) / 10.0;
        ret += (sc.nextInt(10)) / 100.0;
        ret += (sc.nextInt(10)) / 1000.0;
        return ret;
    }
    // 以下方法为记录start和finish值所使用
    public void recordStartTime() {
        start = System.currentTimeMillis();
    }
    public void recordFinishTime() {
        finish = System.currentTimeMillis();
    }
}
