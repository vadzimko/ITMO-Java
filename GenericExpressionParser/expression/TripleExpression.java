package expression;

import expression.myNumber.MyNumber;
import expression.exceptions.ArithmeticParserException;

public interface TripleExpression<T> {
    MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z) throws ArithmeticParserException;

    void print();
}
