package main.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.connection.LoadThread;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class UI extends Application {
    private Stage primary;
    
    private Scene initScene;
    private File selectedFile;

    @Override
    public void init() throws Exception {
        super.init();
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primary = primaryStage;
        VBox vBox = new VBox();
        TextField path = new TextField();
        Button next = new Button("下一步");
        // next.setDisable(true);
        
        path.setPrefColumnCount(30);
        path.setEditable(false);
        Button selectFile = new Button("选择文件");
        selectFile.setOnAction(action->{
            FileChooser fc = new FileChooser();
            fc.setTitle("选择读取的CSV文件");
            fc.setSelectedExtensionFilter(new ExtensionFilter("CSV 文件(*.csv)", "*.csv"));
            File file = fc.showOpenDialog(primaryStage);
            if(file == null) return;
            path.setText(file.getAbsolutePath());
            selectedFile = file;
            next.setDisable(false);
        });
        
        vBox.setOnDragOver(action -> {
            action.acceptTransferModes(TransferMode.ANY);
        });
        vBox.setOnDragDropped(action -> {
            Dragboard db = action.getDragboard();
            if (db.hasFiles()) {
                File f = db.getFiles().get(0);
                path.setText(f.getAbsolutePath());
                selectedFile = f;
                next.setDisable(false);
            }
        });
        Text label = new Text("（其实拖拽也可以）");
        VBox.setMargin(label, new Insets(40));
        label.setFont(Font.font("微软雅黑", 20));
        Group select = new Group(path, selectFile);
        next.setPrefWidth(100);
        next.setPrefHeight(15);
        path.setPrefHeight(15);
        next.setOnAction(action->{
            deal(selectedFile);
        });
        vBox.getChildren().addAll(select, label, next);
        vBox.setAlignment(Pos.CENTER);
        
        BorderPane bp = new BorderPane(vBox);
        bp.setStyle("-fx-background-color: lightblue");
        initScene = new Scene(bp);
        primaryStage.setScene(initScene);
        primaryStage.setX(50);
        primaryStage.setWidth(550);
        primaryStage.setHeight(400);
        primaryStage.setTitle("欢迎使用CSV导入MySQL工具");
        primaryStage.show();
        selectFile.setLayoutX(path.getWidth());
    }
    private ColumnContainer columnRoot;

    private long totalLines;

    private void deal(File source) {
        // 数据字符设置
        ObservableList<Charset> charsets = FXCollections.observableArrayList(Charset.availableCharsets().values());
        ComboBox<Charset> charset = new ComboBox<>(charsets);
        Label charsetTip = new Label("数据源字符集", charset);
        charset.getSelectionModel().select(Charset.defaultCharset());

        // 数据分隔符
        TextField separator = new TextField(",");
        Label separatorTip = new Label("数据分隔符", separator);
        separator.setPrefColumnCount(1);
        separator.setPromptText("分隔符");
        separator.setOnAction(val->{
            if(separator.getText().length() != 1)
                separator.setText(",");
        });

        // 忽略行数
        TextField ignoreNum = new TextField("1");
        ignoreNum.textProperty().addListener((v, o, n)->{
            if(!n.matches("\\d")) ignoreNum.setText(o);
        });
        ignoreNum.setPrefColumnCount(2);
        Label ignoreNumTip = new Label("忽略行数", ignoreNum);

        // 下一步
        Button next = new Button("下一步");
        next.setOnAction(val1->{
            Button next2 = new Button("下一步");
            next2.setOnAction(val2->{
                ModeRadioButton insert = new ModeRadioButton("INSERT"), load = new ModeRadioButton("LOAD");
                ToggleGroup mode = new ToggleGroup();
                mode.getToggles().addAll(insert, load);
                // System.out.println(((ColumnContainer)columnRoot).toSQL());
                TextField tableName = new TextField(source.getName().split("\\.")[0]);

                VBox connectLayout = new VBox();
                VBox connectedLayout = new VBox();
                connectedLayout.setDisable(true);
                
                TextArea connectmsg = new TextArea("jdbc:mysql://localhost:3306/life?user=root&password=123&allowLoadLocalInfile=true");
                connectmsg.setPromptText("连接信息(URL)");
                LoadThread t = new LoadThread(columnRoot, null, Charset.forName("UTF-8"), tableName.getText());
                Button loadButton = new Button("导入");
                t.setFilePath(source);
                t.setIgnore(Integer.parseInt(ignoreNum.getText()));
                t.setTotalLines(totalLines);
                t.setSourceCharset(charset.getSelectionModel().getSelectedItem());
                // t.addEventHandler(WorkerStateEvent.ANY, event->{
                //     loadButton.setDisable(!(State.SCHEDULED.equals(t.getState()) || State.CANCELLED.equals(t.getState()) || State.FAILED.equals(t.getState())));
                // });



                Button connect = new Button("连接");
                connectLayout.getChildren().addAll(connect, connectmsg);
                connect.setOnAction(act->{
                    connect.setDisable(true);
                    try {
                        primary.setTitle("正在连接数据库");
                        t.setConnection(connectmsg.getText());
                        primary.setTitle("已建立连接");
                        connectedLayout.setDisable(false);

                    } catch (SQLException e) {
                        e.printStackTrace();
                        connect.setDisable(false);
                        primary.setTitle("发生异常，代码："+e.getErrorCode());
                    }
                });

                loadButton.setDisable(true);
                mode.selectedToggleProperty().addListener((obv, oldV, newV)->{
                    loadButton.setDisable(newV == null);
                    t.setMode(((ModeRadioButton)newV).getMode());
                });
                Button createTable = new Button("创建表"),
                    dropTable = new Button("删除表");
                
                createTable.setOnAction(action->{
                    try {
                        tableName.setDisable(true);
                        primary.setTitle("建表中");
                        t.createTable();
                        primary.setTitle("建表完成");
                        loadButton.setDisable(false);
                    }
                    catch(SQLException e) {
                        e.printStackTrace();
                        primary.setTitle("建表失败");
                        tableName.setDisable(false);
                    }
                });
                dropTable.setOnAction(action->{
                    try {
                        primary.setTitle("删表中");
                        t.dropTable();
                        primary.setTitle("删表完成");
                    }
                    catch(SQLException e) {
                        e.printStackTrace();
                        primary.setTitle("异常，请检查日志");
                    }
                });
                loadButton.setOnAction(action->{
                    try {
                        if(t.getConnection() == null || t.getConnection().isClosed())
                            t.setConnection(connectmsg.getText());
                        loadButton.setDisable(true);
                        t.reset();
                        primary.setTitle("导入中，请稍等");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    t.restart();
                });
                // ResultSet rs;
                // try {
                //     rs = t.get();
                //     while(rs.) {
                //         System.out.println(rs.getString(0));
                //     }
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }
                ProgressIndicator progressIndicator = new ProgressIndicator();
                progressIndicator.setPrefHeight(100);
                progressIndicator.setPrefWidth(100);
                t.exceptionProperty().addListener((ob, o, n)->{
                    n.printStackTrace();
                    primary.setTitle("导入错误("+n.getMessage()+")，请检查日志");
                });
                t.setOnSucceeded(val3->{
                    primary.setTitle("导入成功-"+(new Date()).toString());
                });
                t.progressProperty().addListener((v, o, n)->{
                    progressIndicator.setProgress(n.doubleValue());
                });

                connectedLayout.getChildren().addAll(new HBox(insert, load), tableName, new HBox(createTable, dropTable), new FlowPane(loadButton, progressIndicator));
                VBox root = new VBox(connectLayout, connectedLayout);
                primary.setScene(new Scene(root));
                
                

            });
            columnRoot = new ColumnContainer();
            CSVParser sr = null;
            int columnCount = 0;
            try {
                sr = new CSVParser(new InputStreamReader(new FileInputStream(source), charset.getSelectionModel().getSelectedItem()), CSVFormat.DEFAULT);
                Iterator<CSVRecord> firstIterator = sr.iterator();
                if(firstIterator.hasNext()) {
                    totalLines++;
                    CSVRecord first = firstIterator.next();
                    columnCount = first.size();
                    first.forEach(action->{
                        columnRoot.getChildren().add(new ColumnType(action));
                    });
                }
                Map<Integer, Integer> map = new HashMap<>();
                while(firstIterator.hasNext()) {
                    totalLines++;
                    CSVRecord t = firstIterator.next();
                    for(int j = 0;j < t.size();j++) {
                        ColumnType ct = ((ColumnType)columnRoot.getChildren().get(j));
                        // if(!(ct.getTypeOther().length() == 0||"INT".equals(ct.getTypeOther()))) continue; // 存在“漏网”
                        // if("CHAR".equals(ct.getTypeOther())) continue; // 低效
                        String ju = t.get(j);
                        if("CHAR".equals(ct.getTypeOther())) {
                            Integer now = map.get(j);
                            map.put(j, now!=null ? Math.max(now, ju.length()) : ju.length());
                        }
                        else if((!"DOUBLE".equals(ct.getTypeOther()))&&ju.matches("^[+-]?[0-9]+$")) {
                            ct.setTypeOther(ju.length() >= 10 ? "BIGINT" : "INT");
                            Integer now = map.get(j);
                            map.put(j, now!=null ? Math.max(now, ju.length()) : ju.length());
                        }
                        else if(ju.matches("^[+-]?[0-9]+(\\.[0-9]+)?$")) {
                            ct.setTypeOther("DOUBLE");
                            String[] parts = ju.replaceAll("[+-]", "").split("\\.");
                            int ret2 = parts[0].length(), ret1 = 0;

                            if(parts.length == 2) ret1 = parts[1].length();
                            // ret2 整数部分，ret1 小数部分
                            int length = ret1+ret2;
                            int result = (ret1 << 16) | length;
                            Integer now = map.get(j);
                            
                            if(now != null) map.put(j, max(result, now));
                            else map.put(j, result);
                        }
                        else {
                            Integer now = map.get(j);
                            map.put(j, now!=null ? Math.max(now, ju.length()) : ju.length());
                            ct.setTypeOther("CHAR");
                        }
                    }
                    
                }

                for(int i = 0;i < columnCount;i++) {
                    ColumnType now = ((ColumnType)columnRoot.getChildren().get(i));
                    Integer size = map.get(i);
                    String type = now.getTypeOther();
                    if(size != null) now.setTypeOther(type+"("+("DOUBLE".equals(type) ? (size&0xffff)+","+(size>>>16) : size)+")");
                }
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(sr != null)
                    try {
                        sr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            Text fieldEdiText = new Text("请自行指定属性");
            fieldEdiText.setFont(Font.font("", 20.5));
            VBox root = new VBox(fieldEdiText, new BorderPane(columnRoot), next2);
            root.setAlignment(Pos.CENTER);
            Scene fieldEdit = new Scene(root);
            primary.setScene(fieldEdit);
            root.setStyle("-fx-background-color: lightgreen");
            primary.setHeight(20*columnCount+50);
        });

        VBox fp = new VBox(charsetTip, separatorTip, ignoreNumTip, next);
        fp.setAlignment(Pos.CENTER);
        fp.setStyle("-fx-background-color: lightpink");
        primary.setScene(new Scene(new BorderPane(fp)));

        // CsvReader cr = new CsvR
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        
    }

    public static int max(int i, int j) {
        int i1 = i>>>16, i2 = i & 0xffff;
        int j1 = j>>>16, j2 = j & 0xffff;
        int iMax = Math.max(i1, j1), jMax = Math.max(i2, j2);
        int result = (iMax<<16)|jMax;
        return result;
    }
}
