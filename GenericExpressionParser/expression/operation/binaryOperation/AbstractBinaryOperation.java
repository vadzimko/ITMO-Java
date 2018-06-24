package expression.operation.binaryOperation;

import expression.TripleExpression;
import expression.exceptions.ArithmeticParserException;
import expression.myNumber.MyNumber;

public abstract class AbstractBinaryOperation<T> implements TripleExpression<T> {
    protected TripleExpression<T> first;
    protected TripleExpression<T> second;

    public AbstractBinaryOperation(TripleExpression<T> first, TripleExpression<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z) throws ArithmeticParserException {
        return count(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public void print() {
        System.out.print(getInstance() + "(");
        first.print();
        System.out.print(", ");
        second.print();
        System.out.print(")");
    }

    abstract String getInstance();

    abstract MyNumber<T> count(MyNumber<T> left, MyNumber<T> right);
}
