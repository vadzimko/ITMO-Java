package expression.myNumber;

public class LongNumber extends AbstractNumber<Long> implements MyNumber<Long> {
    public LongNumber(Long value) {
        super(value);
    }

    public LongNumber(Integer value) {
        super(Long.valueOf(value));
    }

    public MyNumber<Long> add(MyNumber<Long> element) {
        return new LongNumber(value + element.getValue());
    }

    public MyNumber<Long> subtract(MyNumber<Long> element) {
        return new LongNumber(value - element.getValue());
    }

    public MyNumber<Long> multiply(MyNumber<Long> element) {
        return new LongNumber(value * element.getValue());
    }

    public MyNumber<Long> divide(MyNumber<Long> element) {
        return new LongNumber(value / element.getValue());
    }

    public MyNumber<Long> negate() {
        return new LongNumber(-value);
    }
}