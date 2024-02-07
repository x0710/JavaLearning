import java.text.NumberFormat;

import com.control.ui.TypeBar;
import com.control.ui.TypeField;
import com.tool.SpeedFlusher;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AppUI extends Application {
    public static final Font infBar = new Font("宋体", 15);

    private Thread timer;
    private static final NumberFormat rateFormat = NumberFormat.getPercentInstance();
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("x0710的打字通");
        primaryStage.setX(650);
        primaryStage.setY(200);
        primaryStage.setWidth(1024);
        primaryStage.setHeight(800);

        Label correctLabel = new Label("正确："), correctNumber = new Label("0");
        Label correctRateLabel = new Label("正确率："), correctRateNumber = new Label("-");
        Label incorrectLabel = new Label("错误："), incorrectNumber = new Label("0");
        Label incorrectRateLabel = new Label("错误率："), incorrectRateNumber = new Label("-");
        // incorrectLabel.setLayoutX(0);
        Label typedLabel = new Label("已完成："), typedNumber = new Label("0");
        Label typedRateLabel = new Label("进度："), typedRateNumber = new Label("-");
        FlowPane correctGroup = new FlowPane(correctLabel, correctNumber);
        FlowPane correctRateGroup = new FlowPane(correctRateLabel, correctRateNumber);
        FlowPane incorrectGroup = new FlowPane(incorrectLabel, incorrectNumber);
        FlowPane incorrectRateGroup = new FlowPane(incorrectRateLabel, incorrectRateNumber);
        FlowPane typedGroup = new FlowPane(typedLabel, typedNumber);
        FlowPane typedRateGroup = new FlowPane(typedRateLabel, typedRateNumber);

        HBox information = new HBox(correctGroup, correctRateGroup, incorrectGroup, incorrectRateGroup, typedGroup, typedRateGroup);
        
        Label speedLabel = new Label("速度"), speedNumber = new Label("0");
        Label timeLabel = new Label("时间"), timeNumber = new Label("0");
        Label allTimeLabel = new Label("平均速度"), allTimeNumber = new Label("0");
        
        FlowPane speedPane = new FlowPane(speedLabel, speedNumber);
        FlowPane timePane = new FlowPane(timeLabel, timeNumber);
        HBox stateHBox = new HBox(speedPane, timePane);
        information.setAlignment(Pos.CENTER);
        information.setStyle("-fx-font-size: 20px;");
        information.setSpacing(25);
        TypeBar tb = new TypeBar(TypeField.subPassage(Main.readFileByInputStream(Main.class.getResourceAsStream("resources/Youth.psg")), 66), 5);
        timer = new Thread(new SpeedFlusher(tb.onTyped(), speedNumber));
        int allWords = tb.getAllWords();
        tb.onCorrects().addListener((obs, oldV, newV)->correctNumber.setText(String.valueOf(newV)));
        tb.onIncorrects().addListener((obs, oldV, newV)->incorrectNumber.setText(String.valueOf(newV)));
        tb.onTyped().addListener((obs, oldV, newV)->{
            
            typedNumber.setText(String.valueOf(newV));
            correctRateNumber.setText(rateFormat.format(Double.parseDouble(correctNumber.getText())/Double.parseDouble(typedNumber.getText())));
            incorrectRateNumber.setText(rateFormat.format(Double.parseDouble(incorrectNumber.getText())/Double.parseDouble(typedNumber.getText())));
            typedRateNumber.setText(rateFormat.format(tb.onTyped().get()/(double)allWords));
        });
        VBox typeVBox = new VBox(tb);
        typeVBox.setAlignment(Pos.CENTER);

        TextField tf = new TextField("");
        tf.setOnKeyReleased(change->{
            tf.commitValue();
        });
        tf.setTextFormatter(new TextFormatter<String>(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                if(object == null) return "";
                return object;
            }
            @Override
            public String fromString(String object) {
                System.out.println(object);
                if(object == null) return "";
                if(Math.abs(object.length()-tf.getLength())!=1)
                    return tf.getText();
                return object;
            }
            
        }));
        HBox inRoot = new HBox(typeVBox);
        inRoot.setAlignment(Pos.CENTER);
        BorderPane root = new BorderPane(inRoot);
        root.setTop(new VBox(information, stateHBox));
        root.setBottom(tf);
        Scene sc = new Scene(root);
        
        primaryStage.setScene(sc);
        primaryStage.setResizable(false);
        primaryStage.show();
        timer.start();
    }
    // @Deprecated
    @Override
    public void stop() throws Exception {
        super.stop();
        timer.interrupt();
        // timer.stop();
    }
    
}
