package main;

import java.math.*;

/**
* This class is operation class
* Arithmetic, other numerical manipulation,
* Please inherit this class to complete the requirements.
*/
public abstract class Calculation {
	/**
	* The abnormality that occurred is saved by it.
	*/
	protected Exception e = null;
	/**
	* Conditions for activation.
	*/
	protected String key = null;
	
	/**
	* Every operation class should implement it, Otherwise it will not be able to calculate correctly.
	* @param antecedent Manipulation value.
	* @param nextItem Manipulated value.
	* @return Value after operation.If an exception occurs, return null.
	* @see main.RunningBackground
	*/
	public abstract BigDecimal theWay(BigDecimal antecedent, BigDecimal nextItem);
	
	/**
	* Check if this class contains exceptions.
	* If there is, return it, otherwise null.
	* And what you need.
	* @return The exception that occurred, or null if none.
	*/
	public final Exception getException() {
		return e;
	}
	/**
	* @param key The conditions required to activate the class.
	*/
	public Calculation(String key) {
		this.key = key;
	}
}
