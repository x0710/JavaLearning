package sample;

import javafx.scene.control.TextArea;

import java.io.File;

/**
 * 一个正在被打开并被编辑的文件
 */
public class EditFile extends TextArea {
    private File path;

    public EditFile(String name, File path) {
        super(name);
        this.path = path;
    }
    public EditFile(File path) {
        this.path = path;
    }
    public File getPath() {
        return path;
    }
    public void setPath(File path) {
        this.path = path;
    }
}
