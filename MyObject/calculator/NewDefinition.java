package calculation;

import java.math.*;
import main.Calculation;

public class NewDefinition extends Calculation {
	public BigDecimal theWay(BigDecimal antecedent, BigDecimal nextItem) {
		try {
			return antecedent.min(nextItem);
		}
		catch(Exception e) {
			this.e = e;
		}
		
		return null;
	}
	public NewDefinition() {
		super("min");
	}
}