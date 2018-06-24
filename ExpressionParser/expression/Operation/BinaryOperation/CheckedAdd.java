package expression.Operation.BinaryOperation;
import expression.TripleExpression;
import expression.exceptions.ArithmeticParserException;

public class CheckedAdd extends AbstractBinaryOperation implements TripleExpression {
    public CheckedAdd(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int count(int left, int right) {
        if (left > 0 && right > 0 && Integer.MAX_VALUE - right < left
                || left < 0 && right < 0 && Integer.MIN_VALUE - right > left) {

            throw new ArithmeticParserException("Overflow: " + left + "+" + right);
        }
        return left + right;
    }

    @Override
    String getInstance() {
        return "Add";
    }
}
