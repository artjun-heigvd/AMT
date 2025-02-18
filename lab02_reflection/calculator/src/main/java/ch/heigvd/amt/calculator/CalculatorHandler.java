package ch.heigvd.amt.calculator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * An invocation handler that logs method calls and delegates them to an original instance of a calculator.
 */
public class CalculatorHandler implements InvocationHandler {

    private final Calculator original;

    /**
     * Creates a new calculator handler.
     *
     * @param original the original calculator instance
     */
    public CalculatorHandler(Calculator original) {
        this.original = original;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.println("Invoking method: " + method.getName() + "(" + args[0] + ", " + args[1] + ")");
        Object result = null;
        try{
            result = method.invoke(original, args);
        } catch(Exception ignored){}
        if((int)args[0] > 0 && (int)args[1] > 0 && (int)args[0] + (int)args[1] < 0 ||
                (int)args[0] < 0 && (int)args[1] < 0 && (int)args[0] + (int)args[1] > 0 ||
                (int)args[0] < 0 && (int)args[1] > 0 && (int)args[0] - (int)args[1] > 0 ||
                (int)args[0] > 0 && (int)args[1] < 0 && (int)args[0] - (int)args[1] < 0) {
            System.out.println("Overflow: " + result);
            result = 0;
        }
        // TODO: implement this method to handle method calls and delegate them to the original calculator instance
        return result;
    }

    /**
     * Creates a proxy instance of a calculator.
     *
     * @param original the original calculator instance
     * @return the proxy instance
     */
    public static Calculator createProxy(Calculator original) {
        // TODO: implement this method to create a proxy instance of a calculator
        return (Calculator) Proxy.newProxyInstance(
                Calculator.class.getClassLoader(),
                new Class[] {Calculator.class},
                new CalculatorHandler(original)
        );
    }

    /**
     * Demonstrate the use of the calculator handler by creating a proxy instance of a calculator.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create an original calculator instance
        Calculator originalCalculator = new BasicCalculator();

        // Create a proxy instance
        Calculator proxyCalculator = createProxy(originalCalculator);

        // Use the proxy instance
        System.out.println("Result of addition: " + proxyCalculator.add(1, 2));
        System.out.println("Result of subtraction: " + proxyCalculator.subtract(4, 2));
        System.out.println("Result of overflow: " + proxyCalculator.add(Integer.MAX_VALUE, 1));
    }
}
