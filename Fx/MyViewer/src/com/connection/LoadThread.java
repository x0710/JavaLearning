package com.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.ui.ColumnContainer;

public class LoadThread extends Service<Void> {
    private ColumnContainer columnType;
    private Connection connection;
    private String tableName;
    private Charset severCharset, sourceCharset;
    private Mode mode;
    private char fieldsTerminatedChar = ',',
        optionallyEnclosed = '"';
    private String linesTerminated = "\\r\\n";
    private int ignore;

    private File path;
    public LoadThread(ColumnContainer ct, Mode mode, Charset charset, String tableName) {
        this.tableName = tableName;
        this.severCharset = charset;
        this.mode = mode;
        columnType = ct;
    }
    public void setConnection(String url) throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void createTable() throws SQLException {
        Statement s = connection.createStatement();
        s.execute("CREATE TABLE "+tableName+columnType.toSQL()+" CHARSET="+severCharset.name().replaceAll("\\W", ""));
    }
    public void dropTable() throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS "+tableName);
    }
    public void setFilePath(File path) {
        this.path = path;
    }
    public LoadThread withFieldsTerminatedBy(char c) {
        this.fieldsTerminatedChar = c;
        return this;
    }
    public LoadThread withOptionallyEnclosedBy(char c) {
        this.optionallyEnclosed = c;
        return this;
    }
    public LoadThread withLinesTerminated(String c) {
        this.linesTerminated = c;
        return this;
    }
    public void setIgnore(int i) {
        this.ignore = i;
    }
    public void setSourceCharset(Charset charset) {
        this.sourceCharset = charset;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
    public void setTotalLines(long num) {
        totalLines = num;
    }
    private long totalLines;
    public Connection getConnection() {
        return connection;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                Statement s = connection.createStatement();
                if(mode == Mode.LOAD) {
                    System.out.println("LOAD大法好");
                    try {
                        String sql = ("LOAD DATA LOCAL INFILE '"+path.getAbsolutePath().replace('\\', '/')+"' INTO TABLE "+tableName+" CHARACTER SET "+sourceCharset.name().replaceAll("\\W", "")+
                        " FIELDS TERMINATED BY '"+fieldsTerminatedChar+
                        "' OPTIONALLY ENCLOSED BY '"+optionallyEnclosed+
                        "' LINES TERMINATED BY '"+linesTerminated+"' IGNORE "+ignore+" LINES");
                        updateProgress(0, 1);
                        s.execute(sql);
                        updateProgress(1, 1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if(mode == Mode.INSERT) {
                    System.out.println("INSERT大法好");
                    CSVParser reader;
                    int colNum = 0;
                    int n = 0;
                    reader = new CSVParser(new InputStreamReader(new FileInputStream(path), sourceCharset), CSVFormat.DEFAULT);
                    
                    Iterator<CSVRecord> iterator = reader.iterator();
                    if(iterator.hasNext()) {
                        colNum = iterator.next().size();
                    }
                    StringBuffer sb = new StringBuffer("INSERT INTO "+tableName+" VALUES(");
                    for(int i = 0;i < colNum;i++) {
                        sb.append("?,");
                    }
                    sb.setLength(sb.length()-1);
                    sb.append(")");
                    // System.out.println(sb);

                    PreparedStatement ps = connection.prepareStatement(sb.toString());
                    int index = ignore;
                    while(iterator.hasNext()) {
                        n++;
                        updateProgress(n, totalLines-ignore);
                        if(index > 0) {
                            index--;
                            iterator.next();
                            continue;
                        }
                        ps.close();
                        ps = connection.prepareStatement(sb.toString());
                        CSVRecord now = iterator.next();
                        Iterator<String> i = now.iterator();
                        for(int j = 1;j <= now.size() && i.hasNext();j++) {
                            // ps.setString(j, new String(i.next().getBytes(), sourceCharset));
                            String next = i.next();
                            // System.out.println(next);
                            ps.setString(j, next);
                        }
                        ps.executeUpdate();
                    }
                }
                succeeded();
                System.out.println("导入结束");
                return null;
            }
            
        };
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        connection.close();
    }
}
