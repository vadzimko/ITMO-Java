package expression.myNumber;

public class ShortNumber extends AbstractNumber<Short> implements MyNumber<Short> {

    public ShortNumber(Short value) {
        super(value);
    }

    public ShortNumber(Integer value) {
        super(value.shortValue());
    }

    public MyNumber<Short> add(MyNumber<Short> element) {
        return new ShortNumber(value + element.getValue());
    }

    public MyNumber<Short> subtract(MyNumber<Short> element) {
        return new ShortNumber(value - element.getValue());
    }

    public MyNumber<Short> multiply(MyNumber<Short> element) {
        return new ShortNumber(value * element.getValue());
    }

    public MyNumber<Short> divide(MyNumber<Short> element) {
        return new ShortNumber(value / element.getValue());
    }

    public MyNumber<Short> negate() {
        return new ShortNumber(-value);
    }
}