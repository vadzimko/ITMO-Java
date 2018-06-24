package expression.constParser;

import expression.myNumber.MyNumber;

public interface ConstParser<T> {
    MyNumber<T> parse(String data);

    MyNumber<T> convert(int x);
}
