package reflection;

import java.lang.reflect.*;

public class Reflecter {
    public static void main(String[] args) throws Exception{
        Class c1 = System.class;
        Constructor co1 = c1.getDeclaredConstructor(null);
        co1.setAccessible(true);
        System sys = (System)co1.newInstance();
        System.out.println(sys);
        Method m1 = c1.getDeclaredMethod("checkKey", String.class);
        m1.setAccessible(true);
        m1.invoke(null, "9094");
    }
}
