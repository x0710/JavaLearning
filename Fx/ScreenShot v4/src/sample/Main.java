package sample;

import javafx.application.Application;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        Application.launch(Shot.class, args);
    }


    /**
     * 访问硬盘，写入一张{@link java.awt.image.BufferedImage}照片
     * 格式指定为png，内部实际调用{@link ImageIO#write(RenderedImage, String, File)}
     * 文件将会以{@link java.lang.System#currentTimeMillis()}作为名字
     * @param path 要写入的目录，而不是文件位置
     * @param image 要写入硬盘的图片资源
     * @see ImageIO#write(RenderedImage, String, File)
     * @since 1.0
     */
    public static void writeImage(String path, BufferedImage image) {
        try {
            ImageIO.write(image, "png", new File(path + System.currentTimeMillis() + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 将图片复制到剪切板
     * @param image 要复制到时剪切板的目标图片
     * @since 3.1
     */
    public static void writeImageToClipboard(BufferedImage image) {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] {DataFlavor.imageFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                if(isDataFlavorSupported(flavor))
                    return image;
                throw new UnsupportedFlavorException(flavor);
            }
        };
        cb.setContents(t, null);
    }
    /**
     * 获取屏幕的部分区域
     * @param x 起始横坐标
     * @param y 起始纵坐标
     * @param width 宽度
     * @param height 长度
     * @return 所获区域，如果有错误则为null
     * @since 1.0
     */
    public static BufferedImage screenshot(int x, int y, int width, int height) {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        BufferedImage bufferedImage = null;
        try {
            Robot robot = new Robot();
            bufferedImage = robot.createScreenCapture(rectangle);
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

}
