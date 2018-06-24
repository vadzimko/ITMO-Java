package expression.ExpressionObject;

import expression.TripleExpression;

public class Variable implements TripleExpression {
    private String name;

    public Variable(String name) {
        //assert name != null : "Variable name is null!";

        this.name = name;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default: return 0;
        }
    }

    @Override
    public void print() {
        System.out.print(name);;
    }
}
