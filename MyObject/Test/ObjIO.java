import java.io.*;
import java.util.*;

public class ObjIO {
	public static void main(String[] args) throws Exception{
		new FReader();
		
	}
	static void in() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("sys.obj"));
		Object obj = ois.readObject();
		LinkedList<String> testValue = (LinkedList<String>) obj;
		System.out.println(testValue);
	}
	static void out() throws IOException {
		LinkedList<String> testValue = new LinkedList<>();
		testValue.add("test text 1");
		testValue.add("test text 2");
		testValue.add("test text 3");
		testValue.add("test text 4");
		testValue.add("test text 5");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sys.obj"));
		oos.writeObject(testValue);
		oos.flush();
		oos.close();
	}
}

class FReader {
    private LinkedList<String> frame;
    private BufferedReader reader;

    public FReader() {
        frame = new LinkedList<>();
        reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("ikun")));
		run();
		try {
			write();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
    public void run() {
        for(;;) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i < 75;i++) {
                String str;
                try {
                    str = reader.readLine();
                    if(str != null) 
						sb.append(str+System.lineSeparator());
					else {
						reader.close();
						frame.add(sb.toString());
						return;
					}
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            }
            frame.add(sb.toString());
        }
    }
    public void write() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ikun.frame"));
		oos.writeObject(frame);
		oos.flush();
		oos.close();
    }
}
