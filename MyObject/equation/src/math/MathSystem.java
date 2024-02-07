package math;

import math.type.Integer;
import math.type.Decimal;
import math.type.symbol.IllegalCharacter;
import math.type.symbol.Symbol;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathSystem {
    private static final Pattern INTEGER_MOD = Pattern.compile("^([+-])?0*(\\d+)(?:\\.(\\d+))?$");
    public static final Decimal ZERO = getNum("0");

    /**
     * 在硬盘中缓存的{@link Decimal}值，为保证效率可能更高
     */
    private static ArrayList<Decimal> buffer;
    /**
     * 缓存中还没有的数，但已经使用过
     * 将会将其写入内存与硬盘
     */
    private static ArrayList<Decimal> willWrite;

    public static Decimal getNum(String value) {
        String integer, decimal, symbolStr;
        Matcher matcher = INTEGER_MOD.matcher(value);
        if(!matcher.matches()) {
            throw new IllegalCharacter(value+" 不符合格式");
        }

        // 提取符号，整数位，小数位
        symbolStr = matcher.group(1);
        integer = matcher.group(2);
        decimal = matcher.group(3);
        if(integer == null)
            integer = "0";

//        System.out.println("整："+integer);

        Decimal ret = null;

        // 如果val没有符号，默认为正数
        Symbol symbol = Symbol.POSITIVE;
        if(symbolStr != null && Symbol.NEGATIVE.getSymbol() == symbolStr.toCharArray()[0]) {
            symbol = Symbol.NEGATIVE;
        }

        // 如果小数部分是0，则创建一个整数形
        if(decimal == null) {
            ret = new Integer(symbol, integer);
        }
        else {
            ret = new Decimal(symbol, integer, decimal);
        }
        System.out.println("已实例化"+ret);
        return ret;
    }

    /**
     * 加上一个数，只支持单数
     * 此方法在调用时较为方便，但并不支持超过{@link java.lang.Integer#MAX_VALUE}的值
     * @param a 加数的字符表示形式
     * @param b 加数的字符表示形式
     * @return a, b的和
     */
    public static String add(char a, char b) {
        if(a < 48 || a > 57)
            throw new IllegalCharacter("'"+a+"' not a true Number");
        return (a-48)+(b-48)+"";
    }
    /**
     * 减去一个数，只支持单数
     * 此方法在调用时较为方便，但并不支持超过{@link java.lang.Integer#MAX_VALUE}的值
     * @param a 被减数的字符表示形式
     * @param b 减数的字符表示形式
     * @return a, b的差
     */
    public static String subtraction(char a, char b) {
        if(a < 48 || a > 57)
            throw new IllegalCharacter("'"+a+"' not a true Number");
        return (a-48)-(b-48)+"";
    }

    /**
     * 返回与10的差的表示形式
     * @param a 与其差，总是正数
     * @return 与10的差
     */
    public static String subtract10(char a) {
        if(a < 48 || a > 57)
            throw new IllegalCharacter("'"+a+"' not a true Number");
			System.out.println("10-"+a+"是"+(char)(106-a));
        return (char)(106-a)+"";
    }
    /*
    *//**
     * 乘以一个数，只支持单数
     * 此方法在调用时较为方便，但并不支持超过{@link java.lang.Integer#MAX_VALUE}的值
     * @param a 乘数的字符表示形式
     * @param b 乘数的字符表示形式
     * @return a, b的积
     */
    public static String multiplication(char a, char b) {
        return (a-48)*(b-48)+"";
    }

    /**
     * 检查这个字符是否可看作一个数字
     * 如果不可用则抛出异常
     * @param a 要检查的数字
     * @see IllegalCharacter
     */
    static void throwNumChar(char a) {
        if(a < 48 || a > 57)
            throw new IllegalCharacter("'"+a+"' not a true Number");
    }

    private MathSystem() {}
}
