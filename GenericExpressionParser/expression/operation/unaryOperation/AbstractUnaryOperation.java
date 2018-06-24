package expression.operation.unaryOperation;

import expression.TripleExpression;
import expression.myNumber.MyNumber;

public abstract class AbstractUnaryOperation<T> implements TripleExpression<T> {
    protected TripleExpression<T> value;

    public AbstractUnaryOperation(TripleExpression<T> value) {
        this.value = value;
    }

    @Override
    public MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z) {
        return count(value.evaluate(x, y, z));
    }

    abstract MyNumber<T> count(MyNumber<T> value);

    @Override
    public void print() {
        System.out.print(getInstance() + "(");
        value.print();
        System.out.print(")");
    }

    abstract String getInstance();
}
