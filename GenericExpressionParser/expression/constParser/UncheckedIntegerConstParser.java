package expression.constParser;

import expression.myNumber.UncheckedIntegerNumber;
import expression.myNumber.MyNumber;

public class UncheckedIntegerConstParser implements ConstParser<Integer> {
    @Override
    public MyNumber<Integer> parse(String data) {
        return new UncheckedIntegerNumber(Integer.parseInt(data));
    }

    @Override
    public MyNumber<Integer> convert(int x) {
        return new UncheckedIntegerNumber(x);
    }
}
