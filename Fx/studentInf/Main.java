import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static Connection connection;
    public static void main(String[] args) throws Exception {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection("jdbc:mysql://localhost/life?user=root&password=Gao0312");
        /*PreparedStatement statement = connection.prepareStatement("UPDATE yizhong_student SET picture=? WHERE no=?");
        File d = new File("C:\\Users\\15940\\Pictures\\s");
        for(File pic:d.listFiles()) {
            InputStream is = new FileInputStream(pic);
            statement.setString(2, pic.getName().substring(0, 6));
            statement.setBinaryStream(1, is, pic.length());
            int resultCode = statement.executeUpdate();
            System.out.println(pic.getName()+" 设置"+((resultCode == 1) ? "成功" : "失败"));
        }
        connection.close();
		*/
        launch(args);
        //Platform.exit();

    }
    private DataLabel now = null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField noInput = new TextField();
        AnchorPane data = new AnchorPane(noInput);
        AnchorPane.setLeftAnchor(noInput, 0d);

        noInput.setPromptText("查询人的代码");
        ImageView iv = new ImageView();
        Button left = new Button("上一个"), commit = new Button("检索"), right = new Button("下一个");
        HBox move = new HBox(left, commit, right);
        left.setOnAction(val->{
            try {
                int n = Integer.parseInt(noInput.getText());
                noInput.setText(String.valueOf(n-1));
                commit.fire();
            }
            catch(NumberFormatException e) {}
        });
        right.setOnAction(val->{
            try {
                int n = Integer.parseInt(noInput.getText());
                noInput.setText(String.valueOf(n+1));
                commit.fire();
            }
            catch(NumberFormatException e) {}
        });
        commit.setOnAction(val->{
            try {
                data.getChildren().remove(now);
                int code = Integer.parseInt(noInput.getText());
                Image img = getImage(code);
                iv.setImage(img);
                now = getInformation(code);
                data.getChildren().add(now);
                AnchorPane.setRightAnchor(now, 0d);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });
        noInput.setOnAction(val->commit.fire());
        FlowPane root = new FlowPane(data, move, iv);
        root.setOrientation(Orientation.VERTICAL);
        primaryStage.setWidth(300d);
        primaryStage.setHeight(500d);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("mysql信息查看器");
        primaryStage.show();
    }
    private static Image getImage(int code) throws Exception {
        PreparedStatement ps = connection.prepareStatement("SELECT picture FROM yizhong_student WHERE no=?");
        ps.setInt(1, code);
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()) {
            InputStream is = resultSet.getBinaryStream("picture");
            Image ret = new Image(is);
            return ret;
        }
        return null;
    }
    private static DataLabel getInformation(int code) throws Exception {
        PreparedStatement ps = connection.prepareStatement("SELECT no,grade,class,name FROM yizhong_student WHERE no=?");
        ps.setInt(1, code);
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()) {
            return new DataLabel(resultSet.getInt("no"), resultSet.getString("name"), resultSet.getString("class"), resultSet.getString("grade"));
        }
        return DataLabel.NOT_FOUND;
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        connection.close();
    }
}