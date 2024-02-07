package algorithm;

import core.Algorithm;

import java.math.BigDecimal;

public class Subtraction extends Algorithm {
    public Subtraction() {
        super();
        super.identifier = "-";
    }
    @Override
    public BigDecimal run(BigDecimal manipulatedValue, BigDecimal operationValue) {
        return manipulatedValue.subtract(operationValue);
    }
}
