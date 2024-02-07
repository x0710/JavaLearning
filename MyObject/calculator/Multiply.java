package calculation;

import java.math.*;
import main.*;

public class Multiply extends Calculation {
	public BigDecimal theWay(BigDecimal antecedent, BigDecimal nextItem) {
		try {
			return antecedent.multiply(nextItem);
		}
		catch(Exception e) {
			this.e = e;
		}
		
		return null;
	}
	public Multiply() {
		super("*");
	}
}