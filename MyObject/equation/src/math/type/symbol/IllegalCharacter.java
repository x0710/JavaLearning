package math.type.symbol;

/**
 * 如果一个字符不符合规范时抛出此异常
 */
public class IllegalCharacter extends IllegalArgumentException {
    public IllegalCharacter() {}
    public IllegalCharacter(String message) {
        super(message);
    }
}
