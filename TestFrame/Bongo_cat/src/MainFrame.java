import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("My Bongo_cat By x0710");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setCon();
        setBounds(300, 200, 300, 200);
    }
    private void setCon() {
//        JButton jb = new JButton("BEEP");
//        jb.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Toolkit.getDefaultToolkit().beep();
//            }
//        });
//        add(jb);
        JLabel jl = new JLabel();
//        jl.setDisabledIcon(ImageLoader.cat);
        jl.setIcon(ImageLoader.cat);
        jl.setSize(10, 10);
        add(jl);
    }

}
