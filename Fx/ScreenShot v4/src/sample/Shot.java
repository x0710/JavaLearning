package sample;

import com.melloware.jintellitype.JIntellitype;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

import java.awt.image.BufferedImage;
import java.io.File;

public class Shot extends Application {
    private String writePath = "";
    private Color borderColor = Color.WHITE;
    private boolean clipToClipboard;
    private boolean save;

    private final ImageView imageView = new ImageView();

    @Override
    public void start(Stage stage) throws Exception {
        Button screenshotButton = new Button("截图");
        Slider sizeSlider = new Slider(0,10,1);
        sizeSlider.setPrefWidth(400);
        Button reset = new Button("恢复大小");
        FlowPane picFlowPane = new FlowPane(screenshotButton, sizeSlider, reset);

        Label help = new Label("选区颜色");
        help.setPadding(new Insets(5,5,0,0));
        ColorPicker borderColorPicker = new ColorPicker();
        borderColorPicker.setPrefWidth(50);
        Pane color = new HBox(help, borderColorPicker);
        TextField saveFilePathField = new TextField();
        imageView.imageProperty().addListener((o, i1, i2)->{
            sizeSlider.setDisable(i2 == null);
        });
        sizeSlider.setDisable(true);
        saveFilePathField.setOnAction(action->{
            String path = ((TextField)action.getSource()).getText();
            System.out.println("path=".concat(path));
            File newPath = new File(path);
            if(!newPath.exists()) { // 不存在自动创建目录
                if(!newPath.mkdirs()) { // 没有成功创建文件夹
                    Alert alert = new Alert(Alert.AlertType.ERROR, "创建文件夹失败");
                    alert.showAndWait();
                }
            }
            writePath = path;
        });
        saveFilePathField.setPromptText("存储路径/URL");
        MenuItem openURL = new MenuItem("打开URL");
        openURL.setOnAction(actionEvent->{
            try {
                Image image = new Image(saveFilePathField.getText(), true);
                image.exceptionProperty().addListener((o, n1, latest)-> {
                    new Alert(Alert.AlertType.ERROR, latest.getMessage()).showAndWait();
                });
                this.imageTo = image;
                setImage(image);
            }
            catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            }
            finally {
                saveFilePathField.setText(writePath);
            }
        });
        ContextMenu cm = new ContextMenu(openURL);
        saveFilePathField.setContextMenu(cm);
        Button selectDir = new Button("选择路径");
        CheckBox clip = new CheckBox("复制到剪切板"),
                save = new CheckBox("保存到本地");
        HBox sets = new HBox(clip/*, new Label("   ")*/, save);
        sets.setAlignment(Pos.CENTER);
        Pane settings = new FlowPane(color, saveFilePathField, selectDir, sets);

        imageView.setPreserveRatio(true);

        initShot();
        // 注册监听
        screenshotButton.setOnAction(action->{
            shotCut.show();
        });
        reset.setOnAction(action->sizeSlider.setValue(1.0d));
        selectDir.setOnAction(action->{
            DirectoryChooser dc = new DirectoryChooser();
            Stage open = new Stage();
            open.setTitle("保存路径");
            File directory = dc.showDialog(open);

            if(directory != null) {
                saveFilePathField.setText(directory.getAbsolutePath());
                saveFilePathField.commitValue();
            }
        });
        borderColorPicker.valueProperty().addListener((o, l, latest)->rectangle.setFill(Paint.valueOf(latest.toString())));
        clip.selectedProperty().addListener((o, a, b)->clipToClipboard = b);
        save.selectedProperty().addListener((o, a, b)->this.save = b);

        VBox root = new VBox(picFlowPane, settings, imageView);

        stage.setScene(new Scene(root));
        stage.setTitle("截图工具");
        stage.setWidth(600);
        stage.show();
        sizeSlider.valueProperty().addListener((observableValue, last, latest)->{
            imageView.setFitWidth(imageTo.getWidth()*latest.doubleValue());
//            System.out.println(imageTo.getWidth()*latest.doubleValue());
        });
        JIntellitype.getInstance().addHotKeyListener(i->{
            Platform.runLater(()->{
                switch(i) {
                    case SHOW_HOT:
                        stage.show();
                        break;
                    case SCREENSHOT_HOT:
                        screenshotButton.fire();
                        break;
                }
            });
        });
        Platform.setImplicitExit(false);
    }

    private final Stage shotCut = new Stage();
    private double x1, y1, x2, y2;
    private double w, h;
    private final Rectangle rectangle = new Rectangle();
    private BufferedImage all;

    private void initShot() {
        // 截图界面
        Rectangle2D screen = Screen.getPrimary().getBounds();
        all = Main.screenshot(0,0,(int)screen.getWidth(),(int)screen.getHeight());



        ImageView dealImage = new ImageView();
        dealImage.setLayoutX(0);
        dealImage.setLayoutY(0);

        rectangle.setLayoutX(0);
        rectangle.setLayoutY(0);
        Pane p = new AnchorPane(rectangle, dealImage);
        Scene scene = new Scene(p);
        scene.setCursor(Cursor.CROSSHAIR);

        shotCut.setScene(scene);
        shotCut.setTitle("截图");
        shotCut.setFullScreen(true);
        shotCut.setFullScreenExitHint("");
        shotCut.setOpacity(1);
//        shotCut.setAlwaysOnTop(true);
//        shotCut.initModality(Modality.WINDOW_MODAL);
//        shotCut.initStyle(StageStyle.UNDECORATED);
        shotCut.setX(0);
        shotCut.setY(0);
        shotCut.setWidth(500);
        shotCut.setHeight(400);
        shotCut.setResizable(false);

//        p.setOpacity(0.01);
//        scene.setFill(Paint.valueOf("#00000000"));
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            all = Main.screenshot(0,0,(int)screen.getWidth(),(int)screen.getHeight());
            if(MouseButton.SECONDARY.equals(mouseEvent.getButton())) { // 右键关闭
                mouseEvent.consume();
                shotCut.close();
            }
            if(!MouseButton.PRIMARY.equals(mouseEvent.getButton())) { // 除主键重新截图
                mouseEvent.consume();
                return;
            }
            if(mouseEvent.getClickCount() == 2) { // 全屏截图
                x1 = 0;
                y1 = 0;
                x2 = all.getWidth()+x1;
                y2 = all.getHeight()+y1;
                dealShot(all, x1, y1, x2, y2);
                shotCut.close();
            }
            else {
                x1 = mouseEvent.getSceneX();
                y1 = mouseEvent.getSceneY();
//                System.out.println("x1"+x1);
                rectangle.setX(x1);
                rectangle.setY(x1);

            }
        });
//        scene.addEventHandler(MouseEvent.ANY, System.out::println);
        scene.setOnMouseDragged(mouseEvent -> {
            x2 = mouseEvent.getSceneX();
            y2 = mouseEvent.getSceneY();

            w = Math.abs(x2 - x1);
            h = Math.abs(y2 - y1);

//            System.out.println("W:" + w + "   H:" + h);
            if(x2 < x1) {
                rectangle.setX(mouseEvent.getSceneX());
            }
            if(y2 < y1) {
                rectangle.setY(mouseEvent.getSceneY());
            }

            rectangle.setWidth(w);
            rectangle.setHeight(h);
        });
        scene.setOnMouseReleased(mouseEvent -> {
            w = Math.abs(x2 - x1);
            h = Math.abs(y2 - y1);

            if(rectangle.getWidth() > 0 && rectangle.getHeight() > 0) { // 避免只左击
                dealShot(all, x1, y1, x2, y2);
                rectangle.setWidth(0); // 初始化
                rectangle.setHeight(0);
                shotCut.close();
            }
        });
        scene.setOnKeyPressed(keyEvent->{
            if(KeyCode.ESCAPE.equals(keyEvent.getCode())) {
                shotCut.close();
            }
        });
    }
    private void dealShot(BufferedImage deal, double x1, double y1, double x2, double y2) {
//        System.out.println("X1: "+ x1 +"  Y1: "+ y1);
//        System.out.println("X2: "+ x2 +"  Y2: "+ y2);

        double x = Math.min(x1, x2),
                y = Math.min(y1, y2);
        double width = Math.abs(x1-x2),
                height = Math.abs(y1-y2);
        BufferedImage result = deal.getSubimage((int)x,(int)y,(int)width,(int)height);
//        System.out.println((int)x);
//        System.out.println((int)y);
        Image dealt = SwingFXUtils.toFXImage(result,null);
        setImage(dealt);

    }
    private Image imageTo;

    public static final int SHOW_HOT = 0;
    public static final int SCREENSHOT_HOT = 1;

    @Override
    public void init() throws Exception {
        super.init();
        JIntellitype.getInstance().registerHotKey(SHOW_HOT,"CTRL+ALT+D");
        JIntellitype.getInstance().registerHotKey(SCREENSHOT_HOT,"CTRL+ALT+P");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
    private void setImage(Image image) {
        imageTo = image;
        imageView.setImage(image);
    }
}
