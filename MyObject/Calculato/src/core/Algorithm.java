package core;

import java.math.BigDecimal;

public abstract class Algorithm {
//    protected BigDecimal priorValue;
    protected String identifier;

    public Algorithm() {}
    public abstract BigDecimal run(BigDecimal manipulatedValue, BigDecimal operationValue);
    public String getIdentifier() {
        return identifier;
    }
}
