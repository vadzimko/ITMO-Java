package expression.expressionObject;

import expression.myNumber.MyNumber;
import expression.TripleExpression;
import expression.exceptions.UnknownVariableException;

public class Variable<T> implements TripleExpression<T> {
    private String name;

    public Variable(String name) {
        assert name != null : "Variable name is null!";

        this.name = name;
    }

    @Override
    public MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new UnknownVariableException(name);
        }
    }

    @Override
    public void print() {
        System.out.print(name);
    }
}
