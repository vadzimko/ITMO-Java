package expression.myNumber;

public class UncheckedIntegerNumber extends AbstractNumber<Integer> implements MyNumber<Integer> {
    public UncheckedIntegerNumber(Integer value) {
        super(value);
    }

    public MyNumber<Integer> add(MyNumber<Integer> element) {
        return new UncheckedIntegerNumber(value + element.getValue());
    }

    public MyNumber<Integer> subtract(MyNumber<Integer> element) {
        return new UncheckedIntegerNumber(value - element.getValue());
    }

    public MyNumber<Integer> multiply(MyNumber<Integer> element) {
        return new UncheckedIntegerNumber(value * element.getValue());
    }

    public MyNumber<Integer> divide(MyNumber<Integer> element) {
        return new UncheckedIntegerNumber(value / element.getValue());
    }

    public MyNumber<Integer> negate() {
        return new UncheckedIntegerNumber(-value);
    }
}