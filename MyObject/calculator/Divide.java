package calculation;

import java.math.*;
import main.*;

public class Divide extends Calculation {
	public BigDecimal theWay(BigDecimal antecedent, BigDecimal nextItem) {
		try {
			return antecedent.divide(nextItem);
		}
		catch(Exception e) {
			this.e = e;
		}
		
		return null;
	}
	public Divide() {
		super("/");
	}
}