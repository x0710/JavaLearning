package calculation;

import java.math.*;
import main.Calculation;

public class HashCode extends Calculation {
	public static final BigDecimal MULTIPLY_VAL = new BigDecimal("17");
	public BigDecimal theWay(BigDecimal antecedent, BigDecimal nextItem) {
		try {
			return antecedent.multiply(MULTIPLY_VAL).add(nextItem);
		}
		catch(Exception e) {
			this.e = e;
		}
		
		return null;
	}
	public HashCode() {
		super("hash");
	}
}