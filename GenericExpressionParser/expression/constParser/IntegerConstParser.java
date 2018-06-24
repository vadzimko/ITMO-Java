package expression.constParser;

import expression.myNumber.IntegerNumber;
import expression.myNumber.MyNumber;

public class IntegerConstParser implements ConstParser<Integer> {
    @Override
    public MyNumber<Integer> parse(String data) {
        return new IntegerNumber(Integer.parseInt(data));
    }

    @Override
    public MyNumber<Integer> convert(int x) {
        return new IntegerNumber(x);
    }
}
