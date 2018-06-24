package expression.operation.binaryOperation;

import expression.TripleExpression;
import expression.myNumber.MyNumber;

public class Divide<T> extends AbstractBinaryOperation<T> implements TripleExpression<T> {
    public Divide(TripleExpression<T> first, TripleExpression<T> second) {
        super(first, second);
    }

    @Override
    protected MyNumber<T> count(MyNumber<T> left, MyNumber<T> right) {
        return left.divide(right);
    }

    @Override
    String getInstance() {
        return "Divide";
    }
}
