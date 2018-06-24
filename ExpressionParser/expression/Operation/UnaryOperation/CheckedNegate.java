package expression.Operation.UnaryOperation;

import expression.TripleExpression;
import expression.exceptions.ArithmeticParserException;

public class CheckedNegate extends AbstractUnaryOperation implements TripleExpression {
    public CheckedNegate(TripleExpression value) {
        super(value);
    }

    @Override
    protected int count(int value) {
        if (value == Integer.MIN_VALUE) {
            throw new ArithmeticParserException("Overflow: Cannot negate number: " + value);
        }
        return -value;
    }

    @Override
    String getInstance() {
        return "Negate";
    }
}
