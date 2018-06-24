package expression.ExpressionObject;

import expression.TripleExpression;

public class Const implements TripleExpression {
    private int value = 0;

    public Const(int x) {
        value = x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public void print() {
        System.out.print("Const(");
        System.out.print(value);
        System.out.print(")");
    }
}
