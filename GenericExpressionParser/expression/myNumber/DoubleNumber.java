package expression.myNumber;

public class DoubleNumber extends AbstractNumber<Double> implements MyNumber<Double> {

    public DoubleNumber(Double element) {
        super(element);
    }

    public DoubleNumber(Integer element) {
        super(element.doubleValue());
    }

    public MyNumber<Double> add(MyNumber<Double> element) {
        return new DoubleNumber(value + element.getValue());
    }

    public MyNumber<Double> subtract(MyNumber<Double> element) {
        return new DoubleNumber(value - element.getValue());
    }

    public MyNumber<Double> multiply(MyNumber<Double> element) {
        return new DoubleNumber(value * element.getValue());
    }

    public MyNumber<Double> divide(MyNumber<Double> element) {
        return new DoubleNumber(value / element.getValue());
    }

    public MyNumber<Double> negate() {
        return new DoubleNumber(-value);
    }
}