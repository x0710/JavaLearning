import java.awt.*;

public class Thread1 extends Thread {
    MainFrame f;

    // 如果已经变色为true
    // 指示是否已经出现了图片
    private boolean pictureReally;

    // 构造器
    public Thread1(MainFrame f)
    {
        this.f = f;
    }

    // 线程函数
    public void run() {
        while (true) {
            // 线程开启，默认进入阻塞状态
            Thread.currentThread().suspend();
            System.out.println(Thread.currentThread().getName() + "-->21");
            try {
                //睡眠一段时间后切换图片，睡眠为了等待放映图片
                Thread.sleep((long) (MainFrame.timeGet() * 1000));
            } catch (Exception e) {
                System.exit(1);
            }
            // 图片已准备好
            pictureReally = true;
            // 视觉化
            f.setBackground(Color.GREEN);
            f.l.setStatus(Status.ALLOWABLE);
            // 记录开始变换时间
            f.recordStartTime();
        }

    }

    //Getter与Setter

    public boolean getPictureReally() {
        return pictureReally;
    }
    public void setPictureReallyF()
    {
        pictureReally = false;
    }
}
