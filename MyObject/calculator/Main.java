package main;

import java.util.*;
import calculation.*;
import main.*;
import java.io.*;
import java.math.*;

public class Main {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		//new RunningFrame();
		/*
		File f = new File(new File((new File("Main.class").getAbsolutePath())).
			getParent() + "\\calculation");
		
		//System.out.println("fgawg.754.arwh.9m.class".replaceAll("(\\.\\w*)$", ""));
		
		
		//System.out.println(RunningBackground.getCalculation("RunningBackground.class"));
		
		File f = new File("calculation\\Divide.class");
		
		System.out.println(f.isFile());
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName());
		
		//Object o = Class.forName("calculation.Divide");"\\calculator"
		
		//File f1 = new File(new File(new File("").getAbsolutePath()).getParent() + "\\calculation");
		
		
		String[] str = f1.list();
		
		//for(String s : str) 
		//	System.out.println(s);
		
		//RunningBackground.getCalculation();
		
		File f = new File(new File("").getAbsolutePath());
		System.out.println(f.getAbsolutePath());
		if(f.isDirectory()) 
			for(String str : f.list()) 
				System.out.println(str);
		else System.out.println("over--false");
		Class.forName("main.Main");
		*/
		//System.out.println(RunningBackground.getCalculation());
		//
		
		//System.out.println(ReturnClass.getAllClass());
		//Class.forName("main.Main");
		//System.out.println(RunningBackground.getCalculation());
		/*
		List l = ReturnClass.getAllClass("calculation");
		Iterator i = l.iterator();
		while(i.hasNext()){
			Object o = i.next();
			if(o instanceof Calculation) 
				System.out.println((Calculation)o);
			//System.out.println(o instanceof Calculation);
		}
		*/
		/*
		RunningBackground rb = new RunningBackground();
		
		while(true) {
			rb.manipulation(sc.next(), sc.nextDouble());
			System.out.println(rb);
		}
		
		*/
		//new RunningFrame();
		RunningBackground rb = new RunningBackground();
		for(;;) {
			System.out.println(rb.getValue());
			System.out.print("arg: ");
			String arg = sc.next();
			System.out.print("NUM: ");
			double num = sc.nextDouble();
			boolean canCourse = rb.manipulation​(arg, num);
			System.out.println(canCourse + ", " + rb.getValue());
		}
		
		
		
		
	}
}