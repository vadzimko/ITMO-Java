package expression.Operation.BinaryOperation;
import expression.TripleExpression;
import expression.exceptions.ArithmeticParserException;

public class CheckedMultiply extends AbstractBinaryOperation implements TripleExpression {
    public CheckedMultiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int count(int left, int right) {
        if (left == -1 && right == Integer.MIN_VALUE
                || left > 0 && (right > Integer.MAX_VALUE / left || right < Integer.MIN_VALUE / left)
                || left < -1 && (right < Integer.MAX_VALUE / left || right > Integer.MIN_VALUE / left)) {

            throw new ArithmeticParserException("Overflow: " + left + "*" + right);
        }

        return left * right;
    }

    @Override
    String getInstance() {
        return "Multiply";
    }
}
