package algorithm;

import core.Algorithm;
import java.math.BigDecimal;

public class Add extends Algorithm {
    public Add() {
        super();
        super.identifier = "+";
    }
    @Override
    public BigDecimal run(BigDecimal manipulatedValue, BigDecimal operationValue) {
        return manipulatedValue.add(operationValue);
    }
}
