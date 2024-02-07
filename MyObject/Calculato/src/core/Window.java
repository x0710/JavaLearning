package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class Window extends JFrame {
    Runner r;
    JTextArea result;
    JTextField way;
    String tempMode;
    public Window() {
        super("Algorithm calculator");
        setBounds(500, 400, 600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        componentInitialization();


        setVisible(true);
    }
    private void componentInitialization() {
        r = new Runner();
        way = new JTextField(5);
        
        way.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.setText(r.toString());
                Object o = e.getSource();
                if(o instanceof JTextField) {
                    JTextField jt = (JTextField)o;
                    if("".equals(jt.getText()))
                        return;

                    if(tempMode == null) {
                        tempMode = jt.getText();
                    }
                    else {
                        r.manipulate(tempMode, new BigDecimal(jt.getText()));
                        tempMode = null;
                    }

                    jt.setText("");
                }
            }
        });

        result = new JTextArea(20, 8);
        result.setLineWrap(true);
        add(result, BorderLayout.NORTH);

        JPanel control = new JPanel(new FlowLayout());
        JButton b = new JButton("Calculation");
        JButton j = new JButton("AC");
        j.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runner r = new Runner();
                tempMode = null;
            }
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object o = e.getSource();
                if(o instanceof JTextField) {
                    JTextField jt = (JTextField)o;
                    if(tempMode != null) {
                        tempMode = jt.getText();
                    }
                    else {
                        r.manipulate(tempMode, new BigDecimal(jt.getText()));
                        tempMode = null;
                    }
                }
            }
        });
        control.add(way);
        control.add(b);
        control.add(j);
        add(control);
    }
}
