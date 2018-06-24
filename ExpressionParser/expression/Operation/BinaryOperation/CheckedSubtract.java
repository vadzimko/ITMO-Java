package expression.Operation.BinaryOperation;
import expression.TripleExpression;
import expression.exceptions.ArithmeticParserException;

public class CheckedSubtract extends AbstractBinaryOperation implements TripleExpression {
    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int count(int left, int right) {
        if (left >= 0 && right < 0 && Integer.MAX_VALUE + right < left
                || left < 0 && right > 0 && Integer.MIN_VALUE + right > left) {

            throw new ArithmeticParserException("Overflow: " + left + "-" + right);
        }
        return left - right;
    }

    @Override
    String getInstance() {
        return "Subtract";
    }
}
