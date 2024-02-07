package calculation;

import java.math.*;
import main.*;

public class Subtract extends Calculation {
	public BigDecimal theWay(BigDecimal antecedent, BigDecimal nextItem) {
		try {
			return antecedent.subtract(nextItem);
		}
		catch(Exception e) {
			this.e = e;
		}
		
		return null;
	}
	public Subtract() {
		super("-");
	}
}