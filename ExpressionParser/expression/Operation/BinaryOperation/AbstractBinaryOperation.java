package expression.Operation.BinaryOperation;

import expression.TripleExpression;
import expression.exceptions.ArithmeticParserException;

public abstract class AbstractBinaryOperation implements TripleExpression {
    protected TripleExpression first;
    protected TripleExpression second;

    public AbstractBinaryOperation(TripleExpression first, TripleExpression second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int evaluate(int x, int y, int z) throws ArithmeticParserException{
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
    abstract int count(int left, int right);
}
