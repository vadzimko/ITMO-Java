package expression.Operation.BinaryOperation;
import expression.TripleExpression;
import expression.exceptions.ArithmeticParserException;

public class CheckedDivide extends AbstractBinaryOperation implements TripleExpression {
    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    protected int count(int left, int right) {
        if (right == 0) {
            throw new ArithmeticParserException("Division by zero exception: " + left + "\\" + right);
        } else if (right == -1 && left == Integer.MIN_VALUE){
            throw new ArithmeticParserException("Overflow: " + left + "\\" + right + " > Integer.MAX_VALUE");
        }
        return left / right;
    }

    @Override
    String getInstance() {
        return "Divide";
    }
}
