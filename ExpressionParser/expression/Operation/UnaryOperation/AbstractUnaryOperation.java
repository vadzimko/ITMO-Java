package expression.Operation.UnaryOperation;
import expression.TripleExpression;

public abstract class AbstractUnaryOperation implements TripleExpression {
    protected TripleExpression value;

    public AbstractUnaryOperation(TripleExpression value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return count(value.evaluate(x, y, z));
    }

    @Override
    public void print() {
        System.out.print(getInstance() + "(");
        value.print();
        System.out.print(")");
    }

    abstract String getInstance();

    abstract int count(int value);
}
