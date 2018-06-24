package expression.myNumber;

import expression.exceptions.ArithmeticParserException;

import java.math.BigInteger;

public class BigIntegerNumber extends AbstractNumber<BigInteger> implements MyNumber<BigInteger> {
    public BigIntegerNumber(BigInteger element) {
        super(element);
    }

    public BigIntegerNumber(Integer element) {
        super(BigInteger.valueOf(element));
    }

    public MyNumber<BigInteger> add(MyNumber<BigInteger> element) {
        return new BigIntegerNumber(value.add(element.getValue()));
    }

    public MyNumber<BigInteger> subtract(MyNumber<BigInteger> element) {
        return new BigIntegerNumber(value.subtract(element.getValue()));
    }

    public MyNumber<BigInteger> multiply(MyNumber<BigInteger> element) {
        return new BigIntegerNumber(value.multiply(element.getValue()));
    }

    public MyNumber<BigInteger> divide(MyNumber<BigInteger> element) {
        return new BigIntegerNumber(value.divide(element.getValue()));
    }

    public MyNumber<BigInteger> negate() {
        return new BigIntegerNumber(value.negate());
    }
}