import java.util.*;
import java.lang.reflect.*;
import java.io.*;

import java.util.regex.*;

public class Decompiling {
	
	private Class c;
	Constructor[] constructors;
	Field[] fields;
	Method[] methods;
	
	static Scanner sc = new Scanner(system.in);
	public Decompiling() throws Exception {
		Properties p = new Properties();
		p.load(new FileReader("list.property"));
		c = Class.forName(p.getProperty("class"));
		
		constructors = c.getConstructors();
		fields = c.getDeclaredFields();
		methods = c.getDeclaredMethods();
		
	}
	
	public void waitFor() {
		Object ins;
		
		while(true) {
			for(;;) {
				
				try {
					System.out.println("构造函数：");
					String[] args = sc.next().replaceAll("^\\(|\\)$", "").split(",\s?");
					
				}
				catch(Exception e) {
					System.out.println("无法调用");
				}
			}
			
		}
	}
	
	public void printClassMessage() {
		for(Constructor c:constructors) {
			System.out.println(c);
		}
		for(Field c:fields) {
			System.out.println(c);
		}
		for(Method c:methods) {
			System.out.println(c);
		}
	}
	public static void main(String[] args) throws Exception {
		new Decompiling();
		
	}
	
}