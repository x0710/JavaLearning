import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private Conversion c;

    private JRadioButton[] codingFormatButton = new JRadioButton[3];
    private JRadioButton[] outputModeButton = new JRadioButton[2];
    private JRadioButton[] actionButton = new JRadioButton[2];
    private JTextArea results = new JTextArea(10, 8);
    private JTextField path = new JTextField(20);
    private String filePath;

    public MainFrame() {
        super("Encoding conversion");
        setBounds(600, 200, 1200, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setEnabled(false);
        setResizable(false);
        //设置自动换行
        results.setLineWrap(true);
        //results.setEnabled(true);
        //设置不可编辑
        results.setEditable(false);

        //初始化
        init();

        setVisible(true);
    }
    private void init() {
        JButton close = new JButton("CloseStream");
        JPanel[] mod = setMod();
        JPanel north = new JPanel(new GridLayout(1, 2));
        JPanel east = new JPanel(new GridLayout(20, 1));

        //转换按钮
        JButton determination = new JButton("Confirmation");

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c != null)
                    c.closeStream();
                c = null;
                System.gc();
            }
        });
        determination.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                results.setText("");
                // 测试文件路径是否为文件
                if(!new File((filePath = path.getText())).isFile()) {
                    path.setEditable(true);
                    results.setText("Path name incorrect or not");
                    return;
                }
                // 为开始转码做准备
                //得到编码解码位数，并创建转换器
                //1.0.3
                try {
                    if(codingFormatButton[0].isSelected())
                        c = new Conversion(filePath, Encoded.EIGHT);
                    else if(codingFormatButton[1].isSelected())
                        c = new Conversion(filePath, Encoded.SIXTEEN);
                    else if(codingFormatButton[2].isSelected())
                        c = new Conversion(filePath, Encoded.TWENTY_FOUR);
                    else results.setText("Occurs when the encoding decoding bar is not selected");
                }
                catch (IOException exception) {
                    results.setText("IO occurrence");
                    exception.printStackTrace();
                    return;
                }

                // 2.1.0
                try {
                    if(actionButton[0].isSelected()) {

                        if(outputModeButton[0].isSelected())
                            c.writeDec();
                        else if(outputModeButton[1].isSelected())
                            results.setText(c.retDec());
                        else
                            results.setText("This happens when the output box is not selected");
                    }
                    else if(actionButton[1].isSelected()) {

                        if(outputModeButton[0].isSelected())
                            c.writeBin();
                        else if(outputModeButton[1].isSelected())
                            results.setText(c.retBin());
                        else
                            results.setText("This happens when the output box is not selected");
                    }
                    else
                        results.setText("The conversion mode bar is not selected");
                }
                catch (IllegalCharacterException ice) {
                    results.setText("There are illegal characters in the text");
                }
                catch (CharacterSetTooSmalException be) {
                    results.setText("The character set is too small, please increase the number of code bits");
                }
                catch(IOException ioE) {
                    results.setText("IO occurrence");
                    ioE.printStackTrace();
                    return;
                }

            }
        });
        // 添加“释放流”按钮，以便操纵文件
        path.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() instanceof JTextField)
                    filePath = ((JTextField) e.getSource()).getText();
                results.setEditable(false);
            }
        });

        // 添加路径栏
        north.add(path);
        north.add(determination);

        //添加编码解码格式
        east.add(new Label("\tCoding format"));
        east.add(mod[0]);
        east.add(new Label("\tOutput mode"));
        //添加输出模式
        east.add(mod[1]);
        east.add(new Label("\tTo"));
        //设置转换器
        east.add(mod[2]);
        east.add(close);

        //添加布局方案
        add(north, BorderLayout.NORTH);
        //add(determination);
        add(results, BorderLayout.CENTER);
        add(east, BorderLayout.EAST);
    }
    private JPanel[] setMod() {
        ButtonGroup codingFormat = new ButtonGroup();
        ButtonGroup outputMode = new ButtonGroup();
        ButtonGroup action = new ButtonGroup();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();

        JRadioButton j1 = new JRadioButton("EIGHT");
        JRadioButton j2 = new JRadioButton("SIXTEEN");
        JRadioButton j3 = new JRadioButton("TWENTY_FOR");

        JRadioButton j4 = new JRadioButton("write", true);
        JRadioButton j5 = new JRadioButton("output");

        JRadioButton j6 = new JRadioButton("BinToDec");
        JRadioButton j7 = new JRadioButton("DecToBin");


        codingFormat.add(j1);
        codingFormat.add(j2);
        codingFormat.add(j3);
        codingFormatButton[0] = j1;
        codingFormatButton[1] = j2;
        codingFormatButton[2] = j3;

        outputMode.add(j4);
        outputMode.add(j5);
        outputModeButton[0] = j4;
        outputModeButton[1] = j5;

        action.add(j6);
        action.add(j7);
        actionButton[0] = j6;
        actionButton[1] = j7;

        p1.add(j1);
        p1.add(j2);
        p1.add(j3);

        p2.add(j4);
        p2.add(j5);

        p3.add(j6);
        p3.add(j7);

        return new JPanel[] {
            p1, p2, p3
        };
    }
}
