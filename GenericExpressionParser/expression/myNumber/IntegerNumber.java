package expression.myNumber;

import expression.exceptions.ArithmeticParserException;

public class IntegerNumber extends AbstractNumber<Integer> implements MyNumber<Integer> {
    public IntegerNumber(Integer element) {
        super(element);
    }

    public MyNumber<Integer> add(MyNumber<Integer> element) throws ArithmeticParserException {
        if (value > 0 && element.getValue() > 0 && Integer.MAX_VALUE - element.getValue() < value
                || value < 0 && element.getValue() < 0 && Integer.MIN_VALUE - element.getValue() > value) {

            throw new ArithmeticParserException("Overflow: " + value + "+" + element.getValue());
        }
        return new IntegerNumber(value + element.getValue());
    }

    public MyNumber<Integer> subtract(MyNumber<Integer> element) throws ArithmeticParserException {
        if (value >= 0 && element.getValue() < 0 && Integer.MAX_VALUE + element.getValue() < value
                || value < 0 && element.getValue() > 0 && Integer.MIN_VALUE + element.getValue() > value) {

            throw new ArithmeticParserException("Overflow: " + value + "-" + element.getValue());
        }
        return new IntegerNumber(value - element.getValue());
    }

    public MyNumber<Integer> multiply(MyNumber<Integer> element) throws ArithmeticParserException {
        if (value == -1 && element.getValue() == Integer.MIN_VALUE
                || value > 0 && (element.getValue() > Integer.MAX_VALUE / value || element.getValue() < Integer.MIN_VALUE / value)
                || value < -1 && (element.getValue() < Integer.MAX_VALUE / value || element.getValue() > Integer.MIN_VALUE / value)) {

            throw new ArithmeticParserException("Overflow: " + value + "*" + element.getValue());
        }
        return new IntegerNumber(value * element.getValue());
    }

    public MyNumber<Integer> divide(MyNumber<Integer> element) throws ArithmeticParserException {
        if (element.getValue() == 0) {
            throw new ArithmeticParserException("Division by zero exception: " + value + "\\" + element.getValue());
        } else if (element.getValue() == -1 && value == Integer.MIN_VALUE) {
            throw new ArithmeticParserException("Overflow: " + value + "\\" + element.getValue() + " > Integer.MAX_VALUE");
        }
        return new IntegerNumber(value / element.getValue());
    }

    public MyNumber<Integer> negate() throws ArithmeticParserException {
        if (value == Integer.MIN_VALUE) {
            throw new ArithmeticParserException("Overflow: Cannot negate number: " + value);
        }
        return new IntegerNumber(-value);
    }
}