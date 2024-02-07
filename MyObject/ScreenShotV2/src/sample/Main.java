package sample;

import com.melloware.jintellitype.JIntellitype;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    /**
     * 截图标识
     * @since 3.0
     */
    private static final int HOT_KEY = 1,
    /**
     * 显示主界面标识
     * @since 3.0
     */
        SHOW = 2;
    /**
     * 配置文件
     * @since 2.0
     */
    private static final Properties property = new Properties();
    /**
     * 可用的标识颜色
     * @see #changePath(String)
     * @since 2.0
     */
    protected static final Background ABLE = new Background(new BackgroundFill(javafx.scene.paint.Paint.valueOf("#BBFFBB"), null, null));
    /**
     * 不可用的标识颜色
     * @see #changePath(String)
     * @since 2.0
     */
    protected static final Background DISABLE = new Background(new BackgroundFill(Paint.valueOf("#FF9797"), null, null));
    static {
        try {
            property.load(new FileInputStream("settings.properties"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // 注册快捷键
        JIntellitype.getInstance().registerHotKey(HOT_KEY, property.getProperty("screenshot", "ctrl+alt+p"));
        JIntellitype.getInstance().registerHotKey(SHOW, property.getProperty("show", "ctrl+alt+d"));
    }

    private static String path; // 写入磁盘的目录
    private TextField fileDirectory;
    private Stage primary;
    private Button start; // 开始截图按钮
    private ImageView imageView; // 显示的截图

    private boolean startHide;
    private boolean isCopyToClipboard;
    private boolean isWrite;

    private boolean isLastIcon; // 是否为快捷键所调用
    @Override
    public void start(Stage primaryStage) {
        this.primary = primaryStage;

        initProperties(); // 加载配置
        VBox root = new VBox(); // root 布局

        //支布局
        HBox layout1 = new HBox();
        Group layout2 = new Group();

        // 开始截图按钮初始化
        start = new Button("SCREENSHOT");
        start.setOnAction(actionEvent->{
            if(!isLastIcon)
                primaryStage.setIconified(true);

            click(new Stage());
        });
        start.setLayoutX(5d);
        start.setLayoutY(10d);

        // 路径栏初始化
        fileDirectory = new TextField();
        fileDirectory.setPromptText("PATH");
        fileDirectory.setPrefWidth(400d);
        EventHandler<ActionEvent> changePath = h->{
            TextField tmp = (TextField) h.getSource();
            String path = tmp.getText();
            changePath(path);
        };
        fileDirectory.setOnAction(changePath);
        fileDirectory.setText(path);
        changePath(fileDirectory.getText()); // 更改默认存储路径

        // 设置隐藏按钮
        Button hide = new Button("Hide");
        hide.setOnAction(act->primaryStage.hide());

        // 自动复制到剪切板
        CheckBox copyToClipboard = new CheckBox("Copy to Clipboard");
        copyToClipboard.setSelected(isCopyToClipboard);
        copyToClipboard.setOnAction(act->isCopyToClipboard = copyToClipboard.isSelected());
        // 自动保存到硬盘
        CheckBox save = new CheckBox("Save");
        save.setSelected(isWrite);
        fileDirectory.setDisable(!save.isSelected());
        save.setOnAction(act->{
            fileDirectory.setDisable(!save.isSelected());
            isWrite = save.isSelected();
        });

        // 设置全局快捷键事件
        JIntellitype.getInstance().addHotKeyListener(act->Platform.runLater(()->{
            if(act == HOT_KEY) {
                isLastIcon = true; // 测试出Platform.runLater()是多线程的
                start.fire();
            }
            if(act == SHOW) {
                primaryStage.show();
                primaryStage.setIconified(false);
                primaryStage.setAlwaysOnTop(true);
                primaryStage.setAlwaysOnTop(false);
            }
        }));
        // 初始化ImageView
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.autosize();
        layout2.getChildren().add(imageView);

        // 窗口初始化工作
        layout1.getChildren().addAll(start, fileDirectory, hide, copyToClipboard, save);
        root.getChildren().addAll(layout1, layout2);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ScreenShots by x0710");
        primaryStage.setWidth(900);
        primaryStage.setHeight(675);
        Platform.setImplicitExit(false); // 设置所有不可见窗口不会自动终止javaFx进程
        primaryStage.setOnCloseRequest(act->Platform.setImplicitExit(true));

        // 做最后的初始化工作
        if(!startHide)
            primaryStage.show();
    }

    /**
     * 截图的位置大小信息
     * @since 1.0
     */
    private double startX, startY, endX, endY, width, height;
    /**
     * 截图完成的图片
     * @since 1.0
     */
    private BufferedImage screenshotPic;
    /**
     * 点击截图按钮后所调用的函数
     * @param stage 要从此窗口进行截图
     * @since 1.0
     */
    public void click(Stage stage) {
        Rectangle2D rectangle2D = Screen.getPrimary().getBounds();
        screenshotPic = screenshot(0, 0, (int)rectangle2D.getWidth(), (int)rectangle2D.getHeight());
        if(screenshotPic == null) {
            primary.setTitle("Error---无法截图");
            return;
        }
        stage.initStyle(StageStyle.TRANSPARENT);
        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(screenshotPic, null));
        Group group = new Group(imageView);
        Scene scene = new Scene(group);
        scene.setCursor(Cursor.CROSSHAIR); // 设置光标样式，以便区分
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setAlwaysOnTop(true);

        HBox hBox = new HBox();
        group.getChildren().add(hBox);
        // 设置个性化颜色
        hBox.setBorder(new Border(
                new BorderStroke(Color.valueOf(property.getProperty("boardColor", "#408080")), BorderStrokeStyle.DASHED, null,
                        new BorderWidths(Double.parseDouble(property.getProperty("boardWidth", "1.5"))))));

        hBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#00000000"), null, null)));
//        hBox.setStyle("-fx-background-color:#00000000");

        // 注册监听
        // 当键盘按下时
        scene.setOnKeyPressed(h->{
            if(KeyCode.ESCAPE.equals(h.getCode()))
                stage.close();
            if(!isLastIcon) {
                primary.setIconified(false);
                isLastIcon = false;
            }
        });
        // 当鼠标点击时
        scene.setOnMouseClicked(h->{
            if(MouseButton.SECONDARY.name().equals(h.getButton().name())) {
                stage.close();
                if(!isLastIcon) {
                    primary.setIconified(false);
                    isLastIcon = false;
                }
            }
        });
        // 当鼠标按下时
        scene.setOnMousePressed(h->{
            if(MouseButton.PRIMARY.name().equals(h.getButton().name())) {
                AnchorPane.setTopAnchor(hBox, 0d);
                AnchorPane.setLeftAnchor(hBox, 0d);
                width = 0;
                height = 0;
                startX = h.getScreenX();
                startY = h.getScreenY();
//                System.out.println("start: "+startX+", "+startY);
            }
//            System.out.println(h.getButton().name());
        });
        // 拖动时
        scene.setOnMouseDragged(t -> {
            endX = t.getScreenX();
            endY = t.getScreenY();
            if(endX >= startX) {
                width= endX - startX;
                hBox.setLayoutX(startX);
            }
            else {
                width= startX - endX;
                hBox.setLayoutX(endX);
            }
            if(endY <= startY) {
                height= startY - endY;
                hBox.setLayoutY(endY);
            }
            else {
                height= endY - startY;
                hBox.setLayoutY(startY);
            }
            hBox.setMinWidth(width);
            hBox.setPrefWidth(width);
            hBox.setMinHeight(height);
            hBox.setPrefHeight(height);
        });
        // 鼠标释放时
        scene.setOnMouseReleased(h->{
            if(!MouseButton.PRIMARY.name().equals(h.getButton().name()))
                return; // 如果不是主键释放，则跳过截图，避免其它键

            if(width > 0 && height > 0) {
                // 先获取屏幕截图，再进行二次修剪截图
                BufferedImage screenshot = screenshotPic.getSubimage(
                        (int) (Math.min(startX, endX)),
                        (int) (Math.min(startY, endY)),
                        (int) width,
                        (int) height);
                dealShot(screenshot); // 处理成品截图
                if(!isLastIcon)
                    primary.setIconified(false);
                else
                    isLastIcon = false;
            }
            else// 如果参数错误就再次截图
                start.fire();
            stage.close(); // 先截图，再关窗
        });
        stage.show();
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
     * 处理完成的截图
     * @param image 要处理的图片
     * @since 3.1
     */
    private void dealShot(BufferedImage image) {
        imageView.setImage(SwingFXUtils.toFXImage(image, null)); // 设置窗口显示截图
        if(isCopyToClipboard)
            writeImageToClipboard(image); // 将图片写入剪切板
        if(isWrite) {
            writeImage(path, image);
//            changePath(path);
        }

    }
    /**
     * 改变图片文件的默认存储路径
     * @param path 所改变为的目标路径
     * @since 2.0
     */
    public final void changePath(String path) {
        if(path == null)
            return;
        // 格式化path
        if(path.matches(".*[^\\\\/]$")) {
            path = path.concat("/");
            fileDirectory.setText(path);
        }
        // 检查path是否可用
        File f = new File(path);
        boolean able = f.exists() && f.isDirectory() && f.canWrite();
        Main.path = path;
        if(able) {
            fileDirectory.setBackground(ABLE); // 可用
        }
        else {
            fileDirectory.setBackground(DISABLE); // 不可用
        }
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

    /**
     * 将image复制到剪切板
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
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void stop() {
        // 保存此次设置，以启动时所用
        property.setProperty("save", isWrite+"");
        property.setProperty("copyToClipboard", isCopyToClipboard+"");
        property.setProperty("path", path);
        FileWriter fw = null;
        try {
            fw = new FileWriter("settings.properties");
            property.store(fw, "settings\r\n" +
                    "tip: BoardWidth and boardWidth must be effective at the same time\r\n" +
                    "hide: Hidden automatically when it is started\r\n" +
                    "show: According to the main interface shortcut\r\n" +
                    "boardWidth: Box widths\r\n" +
                    "boardColor: Box colors\r\n" +
                    "screenshot: Screenshots shortcuts");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(fw != null)
                    fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 释放全局快捷键
        JIntellitype.getInstance().cleanUp();
        try {
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("stop over");
    }

    /**
     * 加载配制文件
     * @since 2.0
     */
    private void initProperties() {
        // 默认路径设置
        path = property.getProperty("path", "D:/");
        // 启动时自动隐藏
        startHide = Boolean.parseBoolean(property.getProperty("hide"," false"));
        // 截图自动复制到剪切板
        isCopyToClipboard = Boolean.parseBoolean(property.getProperty("copyToClipboard", "false"));
        // 是否写入硬盘
        isWrite = Boolean.parseBoolean(property.getProperty("save", "false"));
    }
}
