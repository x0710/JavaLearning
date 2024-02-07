import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ImageLoader {
    public static final ImageIcon cat;

    static {
        cat = new ImageIcon("D:\\Hlddz\\Computer\\Desktop\\Cat.png");
        System.out.println(cat.getIconHeight());
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("Cat.png"));
    }
    private ImageLoader() {
    }
}
