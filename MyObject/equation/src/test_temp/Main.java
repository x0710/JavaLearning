package test_temp;

import math.MathSystem;
import math.type.Decimal;
import math.type.Integer;
import math.type.symbol.Symbol;

import java.util.Random;

public class Main {
    public strictfp static void main(String[] args) {
        /*Decimal n = new Integer(Symbol.NEGATIVE, "1857694018759234672895421864546874868761846416876961019761910987642534687926954783254");
        Decimal result = n.add(MathSystem.getNum("43647156465809124677978888159013180467910156932198241697030619324832468135750678932569"));
        System.out.println("result="+result);*/
        //Decimal n3 = MathSystem.getNum("10.6"),
        //        n4 = MathSystem.getNum("72");
		//System.out.println(6.77046+99017.830);
        //System.out.println(n3+" - "+n4+" + "+n3.add(n4)+"=0");
//        MathSystem.add(a, b).toCharArray();
        /*Random r = new Random();
        int t = 0;
        for(int i = 0;i < 60000;i++) {
            String s1 = r.nextInt(10000)+"", s2 = r.nextInt(5000)+"";
            String s4 = r.nextInt(10000)+"", s3 = r.nextInt(5000)+"";

            String ss1 = s1.concat("."+s2), ss2 = s3.concat("."+s4);
            Decimal n1 = MathSystem.getNum(ss1),
                    n2 = MathSystem.getNum(ss2);
            Decimal result = n1.add(n2);
            double d = Double.parseDouble(ss1)+Double.parseDouble(ss2);
            if(Math.abs(Double.parseDouble(result.toString())-d)<0.1) {
                t++;
                continue;
            }
            System.out.println(ss1+"+"+ss2+"="+result);
            System.out.println(ss1+"+"+ss2+"="+d);
        }
        System.out.println(t/60000);*/

        Decimal d1 = MathSystem.getNum("18"),
                d2 = MathSystem.getNum("4");
        System.out.println(d1.multiplication(d2));
    }
}
