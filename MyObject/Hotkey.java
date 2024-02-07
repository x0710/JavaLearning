import com.melloware.jintellitype.JIntellitype;
import java.util.Date;

public class Hotkey {
    public static void main(String[] args) {
        JIntellitype t = JIntellitype.getInstance();
        t.registerHotKey(1,
                JIntellitype.MOD_CONTROL,
                (int)'P');
        t.addHotKeyListener(num->{
            System.out.println(new Date() + " 1");
        });
		System.out.println("over");
    }
}
