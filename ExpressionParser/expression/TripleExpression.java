package expression;

import expression.exceptions.ArithmeticParserException;

public interface TripleExpression {
    int evaluate(int x, int y, int z) throws ArithmeticParserException;

    void print();
}