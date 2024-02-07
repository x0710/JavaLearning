package core;

import core.FormulaMode;
import java.math.BigDecimal;
import java.util.*;
import java.io.*;

public class Calculator {
	private BigDecimal value;
	private HashMap<FormulaMode, String> existingAlgorithm;
	
	public Calculator() {
		value = new BigDecimal(0);
		System.out.println(getAlgorithmClass());
		
	}
	private ArrayList<FormulaMode> getAlgorithmClass() {
		File path;
		try {
			path = new File(Thread.currentThread().getContextClassLoader().getSystemResource("algorithm").toURI());
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
			
		}
		ArrayList<String> filePath = new ArrayList<String>();
		ArrayList<FormulaMode> ret = new ArrayList<FormulaMode>();
		if(path.isDirectory()) {
			for(File f : path.listFiles()) {
				if(f.isFile()) {
					filePath.add(f.getName());
				}
			}
		}
		for(Iterator it = filePath.iterator();it.hasNext();) {
			try {
				Class c = Thread.currentThread().getContextClassLoader().loadClass("algorithm\\" + it);
				Object o = c.newInstance();
				if(o instanceof FormulaMode) 
					ret.add((FormulaMode)o);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	public static void main(String[] args) {
		new Calculator();
	}
}