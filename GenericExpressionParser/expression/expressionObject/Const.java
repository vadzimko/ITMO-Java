package expression.expressionObject;

import expression.myNumber.MyNumber;
import expression.TripleExpression;

public class Const<T> implements TripleExpression<T> {
    private MyNumber<T> value;

    public Const(MyNumber<T> x) {
        value = x;
    }

    @Override
    public MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z) {
        return value;
    }

    @Override
    public void print() {
        System.out.print("Const(");
        System.out.print(value);
        System.out.print(")");
    }
}
