package expression.operation.binaryOperation;

import expression.TripleExpression;
import expression.myNumber.MyNumber;

public class Multiply<T> extends AbstractBinaryOperation<T> implements TripleExpression<T> {
    public Multiply(TripleExpression<T> first, TripleExpression<T> second) {
        super(first, second);
    }

    @Override
    protected MyNumber<T> count(MyNumber<T> left, MyNumber<T> right) {
        return left.multiply(right);
    }

    @Override
    String getInstance() {
        return "Multiply";
    }
}
