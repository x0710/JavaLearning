import javafx.scene.control.Label;

public class DataLabel extends Label {
    public static final DataLabel NOT_FOUND = new DataLabel("没有找到");
    public DataLabel(int code, String name, String class_, String grade) {
        setData(code, name, class_, grade);
    }
    private DataLabel(String str) {
        super(str);
    }
    public DataLabel() {}
    public void setData(int code, String name, String class_, String grade) {
        setText(code+" "+name+" "+grade+"级"+class_+"班");
    }
}
