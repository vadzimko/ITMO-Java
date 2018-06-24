package expression.constParser;

import expression.myNumber.DoubleNumber;
import expression.myNumber.MyNumber;

public class DoubleConstParser implements ConstParser<Double> {
    @Override
    public MyNumber<Double> parse(String data) {
        return new DoubleNumber(new Double(data));
    }

    @Override
    public MyNumber<Double> convert(int x) {
        return new DoubleNumber(x);
    }
}
