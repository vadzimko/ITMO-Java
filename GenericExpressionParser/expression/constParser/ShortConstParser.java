package expression.constParser;

import expression.myNumber.ShortNumber;
import expression.myNumber.MyNumber;

public class ShortConstParser implements ConstParser<Short> {
    @Override
    public MyNumber<Short> parse(String data) {
        return new ShortNumber(Short.valueOf(data));
    }

    @Override
    public MyNumber<Short> convert(int x) {
        return new ShortNumber(x);
    }
}
