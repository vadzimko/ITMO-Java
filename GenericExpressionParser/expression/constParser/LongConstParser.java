package expression.constParser;

import expression.myNumber.LongNumber;
import expression.myNumber.MyNumber;

public class LongConstParser implements ConstParser<Long> {
    @Override
    public MyNumber<Long> parse(String data) {
        return new LongNumber(Long.valueOf(data));
    }

    @Override
    public MyNumber<Long> convert(int x) {
        return new LongNumber(x);
    }
}
