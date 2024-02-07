package math.type;

import math.type.symbol.Symbol;

/**
 * 分数
 */
public class DivisionNum extends Decimal {
    /**
     * 被除数
     */
    private Decimal dividend;
    /**
     * 除数
     */
    private Decimal divisor;

    public DivisionNum(Symbol s, String i, String dec) {
        super(s, i, dec);
    }

    @Override
    public Decimal subtraction(Decimal number) {
        return null;
    }

    @Override
    public Decimal multiplication(Decimal number) {
        return null;
    }

    @Override
    public Decimal division(Decimal number) {
        return null;
    }

}
