package math.type;

import math.MathSystem;
import math.type.symbol.Symbol;
import java.util.Objects;

import static math.type.symbol.Symbol.*;

/**
 * 一个可以进行数学运算的小数数字，一但创建实例，那么它将是不可变的
 * 请尽量尝试{@link MathSystem}，它是有安全检查的
 */
public class Decimal implements Cloneable, Comparable<Decimal> {
    /**
     * 这个有理数的整数值
     */
    protected final String integer;
    /**
     * 这个有理数的小数值
     */
    protected final String decimal;
    /**
     * 这个有理数的符号
     */
    protected final Symbol symbol;

    public String getInteger() {
        return integer;
    }

    public String getDecimal() {
        return decimal;
    }

    public Symbol getSymbol() {
        return symbol;
    }


    public Decimal(Symbol s, String i, String dec) {
        symbol = s==null ? POSITIVE : s;
        integer = "".equals(i) || i == null ? "0" : i;
        decimal = dec == null || "".equals(i) ? "0" : dec;
    }

    /**
     * 做加法运算
     * @param number 加数
     * @return 结果
     */
    public Decimal add(Decimal number) {
        if(MathSystem.ZERO.equals(number)) {
            return new Decimal(symbol, integer, decimal);
        }
        // 实质是将小数扩大后做整数运算
        StringBuilder n1Decimal = new StringBuilder(decimal),
                n2Decimal = new StringBuilder(number.decimal);

		// 对齐小数点
        if(n1Decimal.length() != n2Decimal.length()) {
			//System.out.println(n1Decimal+"!="+n2Decimal);
            while(n1Decimal.length() > n2Decimal.length()) {
                n2Decimal.append("0");
            }
            while(n1Decimal.length() < n2Decimal.length()) {
                n1Decimal.append("0");
            }
        }

        int pointSize = n1Decimal.length(); // 记录小数点共移动多少位

        char lastAdd = 48; // 48-----'0' 上一次的进位数值
        // System.out.println(num1+"+"+num2);
        StringBuilder val = new StringBuilder(); // 和的字符串表示形式
		Decimal num1 = MathSystem.getNum(symbol.getSymbol() + this.integer+n1Decimal),
			num2 = MathSystem.getNum(number.getSymbol().getSymbol() + number.integer+n2Decimal);
		//System.out.println("num1="+num1+" num2="+num2);
        if(number.symbol == symbol) {// 加法法则：同号相加，异号相差
            // 做加法
			// 整数相加部分
			for(int i = 1;i <= num1.integer.length() || i <= num2.getInteger().length();i++) {
				char a = num1.getIntegerNumByPosition(i),
                    b = num2.getIntegerNumByPosition(i);
				char[] add = MathSystem.add(a, b).toCharArray(); // tempAdd用来记录单次算计的值，add是用于进位的
				if(add.length == 1) { // 表示没有进位
					add = MathSystem.add(add[0], lastAdd).toCharArray();
					// System.out.println(a+"+"+b+"="+add);
					lastAdd = add.length==1?'0':'1'; // 如果加上进位大于9表示有进位
				}
				else {
					char tmp = add[0];
					add = MathSystem.add(add[1], lastAdd).toCharArray();
					// System.out.println("进位"+a+"+"+b+"="+add[0]);
					lastAdd = tmp;
				}
				val.insert(0, add[add.length==1?0:1]);
			}
			if(lastAdd != '0')
				val.insert(0, lastAdd);

//        		System.out.println(pointSize);
        	val.insert(0, symbol.getSymbol()); // 加法法则，取相同符号
		}
		else {
		    Decimal maxAbs = maxAbs(num1, num2);
			//System.out.println(maxAbs);
			Decimal max, min;
			/*
			max = (maxAbs==this) ? this.getAbsolute()
								: number.getAbsolute();
			*/
			if(maxAbs == null) {
				return MathSystem.ZERO;
			}
			else if(maxAbs == this) {
				max = num1.getAbsolute();
				min = num2.getAbsolute();
			}
			else {
				max = num2.getAbsolute();
				min = num1.getAbsolute();
			}
			// System.out.println(max+"是较大的绝对值");
			Symbol symbol = maxAbs.getSymbol();


			/*if(maxAbs.equals()) {

			}*/
			//System.out.println("sum");
			//System.out.println("max: "+max);


			//整数运算
			// 理论上讲此值lastAdd只有0和1出现
			for(int i = 1; i <= num1.integer.length() || i <= num2.integer.length(); i++) {
				char subtracted = max.getIntegerNumByPosition(i), // 被减数
                    subtract = min.getIntegerNumByPosition(i); // 减数
				if(subtracted < subtract||(subtracted==subtract&&lastAdd=='1')) { // 表示有进位，向前一位借1
					// 因为这里subtract值不可能是9，如果为9话subtracted<subtract不成立，所以不用考虑进位
					char sub10 = MathSystem.subtract10(subtract).toCharArray()[0]; // 减数与10的差
					//System.out.println(subtract+"与10的差"+sub10);
					sub10 = MathSystem.subtraction(sub10, lastAdd).toCharArray()[0]; // 借位·操纵
					char res = MathSystem.add(subtracted, sub10).toCharArray()[0]; // 被减数和sub10的和，即 被减数-减数的 个位
					//System.out.println(
					val.insert(0, res);
					lastAdd = '1'; // 表示借位过
				}
				else { // 表示没有借位
					char res = MathSystem.subtraction(subtracted, subtract).toCharArray()[0];
					res = MathSystem.subtraction(res, lastAdd).toCharArray()[0];
					lastAdd = '0'; // 表示没有借位
					val.insert(0, res);
				}

				// 57-18=39 中的9是(10-8)+7得出，3是(5-1-1)得出，按此规律
				//
			}
			val.insert(0, symbol.getSymbol());
			//System.out.println("计算: "+max+"-"+min+"="+val);
			// System.out.println("要实例化的值："+val);
		}
		for(int i = 0;i < pointSize;i++) {
			val.insert(1, "0");
		}
		val.insert(val.length()-pointSize, ".");
        return MathSystem.getNum(val.toString());
    }

    /**
     * 做减法运算
     * @param number 减数
     * @return 结果
     */
    public Decimal subtraction(Decimal number) {
		return add(number.getInverse());
	}
    /**
     * 做乘法运算
     * @param number 乘数
     * @return 结果
     */
    public Decimal multiplication(Decimal number) {
        return multiplicationChar(number.getIntegerNumByPosition(1));
        /*String dect1 = this.getTruthDecimal(), dect2 = number.getTruthDecimal();
		Decimal n1 = MathSystem.getNum(integer+dect1),
                n2 = MathSystem.getNum(number.integer+dect2);
		int move = dect1.length() + dect2.length();
		Decimal ret = MathSystem.ZERO;
		for(int i = 1;i <= n2.integer.length();i++) {

        }
		return null;*/
    }

    /**
     * integer+decimal的值乘以c的值
     * @param c 乘数
     * @return c乘以integer+decimal的值
     */
    Decimal multiplicationChar(char c) {
        char lastMul = '0'; // 进位表示
        Decimal n = new Decimal(POSITIVE, integer+getTruthDecimal(), "0"); // 得到this的绝对值
        System.out.println(c+"*"+n);
        StringBuilder val = new StringBuilder();
        for(int i = 1;i < n.integer.length();i++) {
            char mul = n.getIntegerNumByPosition(i); // 本次相乘的数
            char[] result = MathSystem.multiplication(c, mul).toCharArray(); // 与mul相乘后的结果
            char[] addReal;
            if(result.length > 1) { // 表示有进位
                addReal = MathSystem.add(result[1], lastMul).toCharArray();
//                lastMul = result[0];
            }
            else {
                lastMul = '0';
                addReal = MathSystem.add(result[0], lastMul).toCharArray();
            }

            if(addReal.length == 1) {
                lastMul = '0';
                val.insert(0, addReal[0]);
            }
            else {
                lastMul = addReal[0];
                val.insert(0, addReal[1]);
            }
        }
        return MathSystem.getNum(val.toString());
    }
    /**
     * 做加法运算
     * @param number 除数
     * @return 结果
     */
    public Decimal division(Decimal number) {
        return null;
    }


    /**
     * 这个数的整数部分从右往左数第pos位
     * 从1开始，如果大于整数位返回0
     * @param pos 获取第pos位数字
     * @exception IllegalArgumentException 如果pos小于等于0
     * @return 整数部分的第pos位的char表示形式
     */
    public char getIntegerNumByPosition(int pos) {
        if(pos > integer.length())
            return '0';
        if(pos <= 0)
            throw new IllegalArgumentException(pos+" must more than 0");
//        System.out.println(integer+"的第"+pos+"位是"+integer.toCharArray()[integer.length()-pos]);
        return integer.toCharArray()[integer.length()-pos];
    }
    /**
     * 这个数的小数部分从左往右数第pos位
     * 从1开始，如果大于整数位返回0
     * @param pos 获取第pos位数字
     * @exception IllegalArgumentException 如果pos小于等于0
     * @return 整数部分的第pos位的char表示形式
     */
    public char getDecimalNumByPosition(int pos) {
        if(pos > decimal.length())
            return '0';
        if(pos <= 0)
            throw new IllegalArgumentException(pos+" must more than 0");
        System.out.println(integer+"的第"+pos+"位是"+decimal.toCharArray()[pos-1]);
        return decimal.toCharArray()[pos-1];
    }

    /**
     * 得到这个有理数的一般形式
     * @return 这个有理数的一般形式
     */
    @Override
    public String toString() {
//        System.out.println("int: "+integer);
//        System.out.println("decimal: "+decimal);
        return (symbol == POSITIVE ? "" : symbol.getSymbol())+
                integer+
                (decimal.matches("^0*$") ? "" : ("."+decimal));
    }

    /**
     * 返回两数较大的绝对值的内存地址
     * @param d1 数字1
     * @param d2 数字2
     * @return 绝对值较大的数，如果相等返回null
     */
    public static Decimal maxAbs(Decimal d1, Decimal d2) {
        //Decimal d1 = n1.getAbsolute(), d2 = n2.getAbsolute();

        Decimal maxDecimal, minDecimal;

        // 比较整数的长度，较长的是较大的值
        if(d1.integer.length() != d2.integer.length()) {
            maxDecimal = d2;
            minDecimal = d1;
            if(d1.integer.length() > d2.integer.length()) {
                maxDecimal = d1;
                minDecimal = d2;
            }
            return d1.symbol == POSITIVE ? maxDecimal : minDecimal;
        }
        // 如果全相等，则比较它们的每一位
        char[] d1s = (d1.getInteger()+d1.getDecimal()).toCharArray(),
                d2s = (d2.getInteger()+d2.getDecimal()).toCharArray();
        for(int i = 0;i < d1s.length;i++) {
            if(d1s[i] != d2s[i]) {
                if(d1s[i] > d2s[i]) {
                    maxDecimal = d1;
                    minDecimal = d2;
                }
                else {
                    maxDecimal = d2;
                    minDecimal = d1;
                }
                return d1.symbol == POSITIVE ? maxDecimal : minDecimal;
            }
        }
        // 程序到这里，则它们是相等的值
        return null;
    }

    /**
     * 得到这个数字的绝对值
     * 实际上只是得到了一个为正数的这个值
     * @return 这个数字的绝对值
     */
    public Decimal getAbsolute() {
        return new Decimal(POSITIVE, integer, decimal);
    }
	
    /**
     * 比较两个数字的大小
     * 如果它们相等，则返回null
     * @param d1 数字1
     * @param d2 数字2
     * @return 较大值的内存地址，如果相等返回null
     * @see #maxAbs(Decimal, Decimal) 实际调用的
     */
    public static Decimal maxSize(Decimal d1, Decimal d2) {
        // 先比较符号，如果不相等返回正值
        if(d1.symbol != d2.symbol) {
            return d1.symbol.ordinal() < d2.symbol.ordinal() ? d1 : d2;
        }
		
        Decimal maxAbs = maxAbs(d1, d2);
        if(maxAbs == null)
            return null;
		// 正数绝对值越大，值越大，反之亦然
        if(maxAbs == d1) {
            return (d1.getSymbol() == POSITIVE) ? d1 : d2;
        }
        else {
            return (d1.getSymbol() == POSITIVE) ? d2 : d1;
        }
    }
    @Override
    public int compareTo(Decimal o) {
        Decimal d = maxSize(this, o);
        if(d == null)
            return 0;
        if(d == this)
            return 1;
        return -1;
    }

    /**
     * 得到这个数的实际小数位
     * 例如3.72返回2，8.60返回1
     * @return 实际的小数位数量
     */
    public String getTruthDecimal() {
        return decimal.replaceAll("0*$", "");
    }
	
	/**
     * 得到这个数字的相反数的实例
     * @return 这个数的相反数
     */
	public Decimal getInverse() {
		Symbol newSym = (symbol==POSITIVE) ? NEGATIVE : POSITIVE;
		//System.out.println(toString()+"的相反符号："+newSym.getSymbol());
		return new Decimal(newSym, integer, decimal);
	}
	
    @Override
    public int hashCode() {
        return (integer.hashCode()<<1+decimal.hashCode())&symbol.ordinal();
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof Decimal) {
            Decimal n = (Decimal)obj;
            return symbol == n.symbol && Objects.equals(n.integer, integer) && Objects.equals(n.decimal, decimal);
        }
        return false;
    }
}

