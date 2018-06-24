package expression.operation.binaryOperation;

import expression.TripleExpression;
import expression.myNumber.MyNumber;

public class Subtract<T> extends AbstractBinaryOperation<T> implements TripleExpression<T> {
    public Subtract(TripleExpression<T> first, TripleExpression<T> second) {
        super(first, second);
    }

    @Override
    protected MyNumber<T> count(MyNumber<T> left, MyNumber<T> right) {
        return left.subtract(right);
    }

    @Override
    String getInstance() {
        return "Subtract";
    }
}
