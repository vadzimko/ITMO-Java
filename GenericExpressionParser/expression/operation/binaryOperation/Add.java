package expression.operation.binaryOperation;

import expression.TripleExpression;
import expression.exceptions.ArithmeticParserException;
import expression.myNumber.MyNumber;

public class Add<T> extends AbstractBinaryOperation<T> implements TripleExpression<T> {
    public Add(TripleExpression<T> first, TripleExpression<T> second) {
        super(first, second);
    }

    @Override
    protected MyNumber<T> count(MyNumber<T> left, MyNumber<T> right) throws ArithmeticParserException {
        return left.add(right);
    }

    @Override
    String getInstance() {
        return "Add";
    }
}
