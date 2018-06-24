package expression.constParser;

import expression.myNumber.BigIntegerNumber;
import expression.myNumber.MyNumber;
import java.math.BigInteger;

public class BigIntegerConstParser implements ConstParser<BigInteger> {
    @Override
    public MyNumber<BigInteger> parse(String data) {
        return new BigIntegerNumber(new BigInteger(data));
    }

    @Override
    public MyNumber<BigInteger> convert(int x) {
        return new BigIntegerNumber(x);
    }
}
