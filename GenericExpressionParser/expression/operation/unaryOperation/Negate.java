package expression.operation.unaryOperation;

import expression.TripleExpression;
import expression.myNumber.MyNumber;

public class Negate<T> extends AbstractUnaryOperation<T> implements TripleExpression<T> {
    public Negate(TripleExpression<T> value) {
        super(value);
    }

    @Override
    protected MyNumber<T> count(MyNumber<T> value) {
        return value.negate();
    }

    @Override
    String getInstance() {
        return "Negate";
    }
}
