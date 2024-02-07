package main;

import java.math.*;
import java.util.*;
import java.io.File;
import calculation.ReturnClass;

/**
* The underlying layer is a BigDecimal class.
* It has more ways to manipulate.
* @see main.Calculation
*/
public class RunningBackground
{
	/** Underlying data. */
	private BigDecimal n;
	/** Currently available calculation methods. */
	private static HashMap<String, Calculation> hh;
	
	/** The data default value is 0 by default. */
	public RunningBackground() {
		this(new BigDecimal(0));
	}
	/**
	* Parameterless constructor
	* Default constructor
	* @param val Data default value.
	*/
	public RunningBackground(double val) {
		this(new BigDecimal(val));
	}
	/**
	* Parameterless constructor
	* Default constructor
	* @param val Data default value.
	*/
	public RunningBackground(BigDecimal val) {
		n = val;
		hh = getCalculation();
	}
	
	/**
	* Initialize its value to 0.
	*/
	public void reset() {
		n = new BigDecimal(0);
	}
	
	/**
	* Safe digital manipulation.
	* Processed at runtime.
	* If the operation symbol does not exist,
	* Skip this time.
	* @param arg The calculation method is defined in the operable class (Calculation).
	* @param m What manipulation of the original number.
	* @return Whether the value was modified successfully.
	* @see #getCalculation()
	*/
	public boolean manipulation(String arg, BigDecimal m) {
		if(!hh.containsKey(arg)) 
			return false;
		Calculation c = hh.get(arg);
		BigDecimal bdt = c.theWay(n, m);
		if(bdt == null) 
			return false;
		else 
			n = bdt;
		return true;
	}
	
	/**
	* Safe digital manipulation.And invokes {@link #manipulation(String, BigDecimal)}
	* Processed at runtime.
	* If the operation symbol does not exist,
	* Skip this time.
	* In fact, invokes {@link #manipulation(String arg, BigDecimal m)}
	* @param arg Way of calculation.
	* @param e 	What manipulation of the original number.
				The bottom layer also calls this type of function.
	* @return Was it modified successfully.
	* @see #manipulation(String arg, BigDecimal m)
	*/
	public boolean manipulation(String arg, double e) {
		return manipulation(arg, new BigDecimal(e));
	}
	/**
	* Return its value.
	* @return Its value.
	*/
	public String toString() {
		//System.out.println(n.toString());
		return n.toString();
	}
	/**
	* Gets Current result.
	* @return Current result.
	*/
	public BigDecimal getValue() {
		return new BigDecimal(n.toString());
	}
	
	/**
	* This method is only safe for internal use,
	* If it is called illegally, it is very likely to be invalidly manipulated.
	* Only return instances with Calculation as the parent class.
	* @deprecated 
	* @return The instantiated class in a certain folder.
	* @see calculation.ReturnClass
	* @see calculation.ReturnClass#getAllClass(String packagepath, Object... args)
	*/
	static HashMap<String, Calculation> getCalculation() {
		ArrayList<Calculation> l = ReturnClass.getAllClass("calculation");
		Iterator<Calculation> i = l.iterator();
		
		HashMap<String, Calculation> map = new HashMap<String, Calculation>();
		while(i.hasNext()) {
			Calculation o = i.next();
			if(o instanceof Calculation) {
				Calculation c = o;
				map.put(c.key, c);
				//System.out.println(c);
			}
		}
		
		return map;
	}
	
}
