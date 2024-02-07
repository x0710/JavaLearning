package calculation;

import java.math.*;
import main.Calculation;

public class C extends Calculation {
	public BigDecimal theWay(BigDecimal antecedent, BigDecimal nextItem) {
		try {
			return new BigDecimal("0");
		}
		catch(Exception e) {
			this.e = e;
		}
		
		return null;
	}
	public C() {
		super("AC");
	}
}