import java.util.Scanner;

public class ConsoleMode {
    public ConsoleMode(double a, double b, Operato mod) {
        run(a, b, mod);
    }
    public static double run(double a, double b, Operato mod)
    {
        double end = 0;

        switch(mod) {
            case ADDITION:
                end = a + b;
                break;
            case DIVISION:
                end = a - b;
                break;
            case MULTIPLIE:
                end = a * b;
                break;
            case SUBTRACTION:
                end = a / b;
                break;
        }
        return end;
    }
}
