package math.type.symbol;

/**
 * 一个数字的符号标识
 */
public enum Symbol {
    POSITIVE('+'), NEGATIVE('-');

    private final char symbol;

    private Symbol(char type) {
        this.symbol = type;
    }

    public char getSymbol() {
        return symbol;
    }
	public static Symbol getOpposite(Symbol symbol) {
		return symbol==POSITIVE ? NEGATIVE : POSITIVE;
	}
	public Symbol getOpposite() {
		return getOpposite(this);
	}
}
