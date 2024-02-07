import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) throws Exception{
        Application.launch(AppUI.class, args);
        // for(String val:TypeField.subPassage(readFileByString("C:\\Users\\15940\\OneDrive\\Desktop\\content.txt"), 66)) {
        //     System.out.println(val);
        // }

        
    }
    public static String readFileByInputStream(InputStream stream) throws IOException {
        Reader fr = new InputStreamReader(stream);
        char[] buffer = new char[512];
        StringBuilder sb = new StringBuilder();
        for(int size;(size=fr.read(buffer))!= -1;) {
            sb.append(buffer, 0, size);
        }
        fr.close();
        return sb.toString();
    }
    public static String readFileByFile(File file) throws IOException {
        if(file.exists()) return readFileByInputStream(new FileInputStream(file));
        return null;
    }
    public static String readFileByPath(String path) throws IOException{
        return readFileByFile(new File(path));
    }
}
