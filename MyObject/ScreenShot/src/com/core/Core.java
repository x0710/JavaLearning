package com.core;

import com.melloware.jintellitype.JIntellitype;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public abstract class Core extends Application {
    private static final int HOT_KEY = 1;
    private static final int SHOW = 2;

    protected static final Background ABLE = new Background(new BackgroundFill(Paint.valueOf("#BBFFBB"), null, null)),
            DISABLE = new Background(new BackgroundFill(Paint.valueOf("#FF9797"), null, null));
    protected static final Properties property;
    protected static String path = "";
    static {
        property = new Properties();
        try {
            property.load(new FileInputStream("settings.properties"));
        }
        catch (IOException e) {
            e.printStackTrace();
            try {
                URL url = Thread.currentThread().getContextClassLoader().getResource("settings.properties");
                if(url != null)
                    property.load(new FileInputStream(new File(url.toURI())));
            }
            catch (URISyntaxException | IOException e1) {
                e1.printStackTrace();
            }
        }

        String screenshot = "ctrl+alt+p", show = "ctrl+alt+d";
        try {
            JIntellitype.getInstance().registerHotKey(HOT_KEY, property.getProperty("screenshot"));
        }
        catch (Exception e) {
            e.printStackTrace();
            JIntellitype.getInstance().unregisterHotKey(HOT_KEY);
            JIntellitype.getInstance().registerHotKey(HOT_KEY, screenshot);
        }
        try {
            JIntellitype.getInstance().registerHotKey(SHOW, property.getProperty("show"));
        }
        catch (Exception e) {
            e.printStackTrace();
            JIntellitype.getInstance().unregisterHotKey(SHOW);
            JIntellitype.getInstance().registerHotKey(SHOW, show);
        }
    }

    protected TextField fileDirectory;
    protected Stage primary;
    protected Button start; // 开始截图按钮
    protected ImageView imageView;

    private boolean startHide = false;

    protected boolean isLastIcon; // 是否为快捷键所调用
    @Override
    public void start(Stage primaryStage) {
        this.primary = primaryStage;

        // 窗口初始化工作
        VBox root = new VBox();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ScreenShots by x0710");
        primaryStage.setWidth(800);
        primaryStage.setHeight(575);
        Platform.setImplicitExit(false); // 设置所有不可见窗口不会自动终止javaFx进程
        primaryStage.setOnCloseRequest(act->Platform.setImplicitExit(true));

        //支布局
        HBox layout1 = new HBox();
        Group layout2 = new Group();

        // 开始截图按钮初始化
        start = new Button("SCREENSHOT");
        start.setOnAction(actionEvent->{
            if(!isLastIcon)
                primaryStage.setIconified(true);
            check(new Stage());
        });
        start.setLayoutX(5d);
        start.setLayoutY(10d);

        // 路径栏初始化
        fileDirectory = new TextField();
        fileDirectory.setPromptText("PATH");
        fileDirectory.setPrefWidth(400d);
        fileDirectory.setOnAction(h->{
            TextField tmp = (TextField) h.getSource();
            String path = tmp.getText();
            changePath(path);
        });
        fileDirectory.setText("D:/");
        changePath(fileDirectory.getText()); // 更改默认存储路径

        // 设置隐藏按钮
        Button hide = new Button("Hide");
        hide.setOnAction(act->primaryStage.hide());

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

        // 做最后的初始化工作
        layout1.getChildren().addAll(start, fileDirectory, hide);
        root.getChildren().addAll(layout1, layout2);
        initProperties(); // 加载配置
        if(!startHide)
            primaryStage.show();
    }

    /**
     * 改变图片文件的默认存储路径
     * @param path 所选的路径，如果为null则直接返回false
     * @return 更改后的路径是否可用
     */
    public final boolean changePath(String path) {
        if(path == null)
            return false;
        // 格式化path
        if(path.matches(".*[^\\\\/]$")) {
            path = path.concat("/");
            fileDirectory.setText(path);
        }
        // 检查path是否可用
        File f = new File(path);
        boolean able = f.exists() && f.isDirectory() && f.canWrite();
        start.setDisable(!able);
        Core.path = path;
        if(able) {
            fileDirectory.setBackground(ABLE); // 可用
        }
        else {
            fileDirectory.setBackground(DISABLE); // 不可用
        }
        return able;
    }

    /**
     * 开始截图，被点击了截图
     * @param stage 可操纵窗口
     */
    protected abstract void check(Stage stage);

    /**
     * 获取屏幕一部分区域
     * @param x 开始x坐标
     * @param y 开始y坐标
     * @param width 截取长度
     * @param height 截取高度
     * @return 截取区域，如果发生异常则返回null
     */
    public static BufferedImage screenShot(int x, int y, int width, int height) {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        Robot robot;
        BufferedImage bufferedImage = null;
        try {
            robot = new Robot();
            bufferedImage = robot.createScreenCapture(rectangle);
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }
    private void initProperties() {
        // 默认路径设置
        String path = property.getProperty("path");
        if(path != null)
            changePath(path);
        startHide = Boolean.parseBoolean(property.getProperty("hide"));
    }
    @Override
    public void stop() {
        JIntellitype.getInstance().cleanUp();
        try {
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("stop over");
    }

    protected static void run(String... args) {
        launch(args);
    }
}
