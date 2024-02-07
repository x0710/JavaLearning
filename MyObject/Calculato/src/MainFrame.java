import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame{
    public MainFrame() {
        // 初始化设置
        super("Calculato");
        setBounds(100, 200, 100, 200);
        setVisible(true);

        setLayout(new BorderLayout());

        // 添加监听器
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //添加组件
        add(new Button("a"), BorderLayout.NORTH);
        add(new Button("b"), BorderLayout.SOUTH);
    }
}
