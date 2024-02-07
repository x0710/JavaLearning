package calculation;

import java.math.*;
import main.Calculation;

public class Add extends Calculation {
	public BigDecimal theWay(BigDecimal antecedent, BigDecimal nextItem) {
		try {
			return antecedent.add(nextItem);
		}
		catch(Exception e) {
			this.e = e;
		}
		
		return null;
	}
	public Add() {
		super("+");
	}
}