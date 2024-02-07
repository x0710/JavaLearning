import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        URL u = new URL("https://v.api.aa1.cn/api/api-fanyi-yd/index.php?msg='我喜欢'&type=1");

        BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream(), "utf-8"));

        for(String buffered;(buffered=br.readLine())!= null;) {
            System.out.println(buffered);
        }
        Platform.exit();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(Platform.isSupported(ConditionalFeature.UNIFIED_WINDOW));
        primaryStage.setHeight(100);
        primaryStage.setWidth(100);
        primaryStage.initStyle(StageStyle.DECORATED);
        // ImageView imageView = new ImageView("file:h:\\Pictures\\picture\\Andromeda.jpg");
        FlowPane root = new FlowPane(new Label("1----------\n-2--------"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
    }
}
