package core;

import java.math.BigDecimal;

public abstract class FormulaMode {
	protected String identity = null;
	public abstract BigDecimal process(BigDecimal originalNumber);
	public final String getIdentity() {
		return identity;
	}
}