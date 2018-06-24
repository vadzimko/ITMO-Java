package expression.parser;

import expression.expressionObject.Const;
import expression.expressionObject.Variable;
import expression.constParser.ConstParser;
import expression.operation.binaryOperation.*;

import expression.operation.unaryOperation.Negate;
import expression.TripleExpression;
import expression.exceptions.ParsingException;

import java.util.*;

public class ExpressionParser<T> implements Parser {
    private Set<String> correctVariables = new HashSet<>(Arrays.asList("x", "y", "z"));
    private ConstParser<T> constParser;
    private int index;
    private int size;
    private String data;
    private char currentSymbol;
    private String lastWord;

    public ExpressionParser(ConstParser<T> constParser) {
        this.constParser = constParser;
    }

    @Override
    public TripleExpression<T> parse(String input) throws ParsingException {
        data = deleteSpaces(input);
        size = data.length();
        if (size == 0) {
            return new Const<>(constParser.parse("0"));
        }
        index = 0;
        currentSymbol = data.charAt(0);
        return parseData(getPriority(Token.BEGIN), false);
    }

    private String deleteSpaces(String data) throws ParsingException {
        int balance = 0;
        StringBuilder sb = new StringBuilder();
        int length = data.length();
        char ch;
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(ch = data.charAt(i))) {
                sb.append(ch);
            }
            if (ch == '(') {
                balance++;
            } else if (ch == ')') {
                balance--;
            }
            if (balance < 0) {
                throw new ParsingException(
                        "Input data is incorrect: wrong parenthesis location: ')' was not expected at position " + i
                );
            }
        }
        if (balance != 0) {
            throw new ParsingException(
                    "Input data is incorrect: wrong parenthesis quantity: " + balance + " more ')' are expected"
            );
        }
        return sb.toString();
    }

    private TripleExpression<T> parseData(int priority, boolean isBrackets) throws ParsingException {
        TripleExpression<T> left = parseExpressionObject();

        while (index < size) {
            Token token = getToken(ch());
            nextCh();
            if (token == Token.CLOSING_BRACKET && isBrackets || getPriority(token) <= priority) {
                prevCh();
                return left;
            }

            TripleExpression<T> right = parseData(getPriority(token), isBrackets);
            switch (token) {
                case ADD:
                    left = new Add<>(left, right);
                    break;
                case SUBTRACT:
                    left = new Subtract<>(left, right);
                    break;
                case MULTIPLY:
                    left = new Multiply<>(left, right);
                    break;
                case DIVIDE:
                    left = new Divide<>(left, right);
                    break;
                default:
                    throw new ParsingException(
                            "Input data is incorrect: sign '+', '-', '*' or '/' was expected at position "
                                    + index
                    );
            }
        }
        return left;
    }


    private TripleExpression<T> parseExpressionObject() throws ParsingException {
        Token token = getToken(ch());

        switch (token) {
            case VARIABLE:
                return new Variable<>(lastWord);
            case CONST:
                return parseConstant();
            case OPENING_BRACKET:
                nextCh();
                TripleExpression<T> temp = parseData(0, true);
                nextCh();
                return temp;
            case SUBTRACT:
                nextCh();
                return new Negate<>(parseData(getPriority(Token.NEGATE), false));
            default:
                throw new ParsingException(
                        "Input data is incorrect: variable, constant or subexpression was expected at position "
                                + index
                );
        }
    }

    private TripleExpression<T> parseConstant() throws ParsingException {
        int begin = index;
        if (ch() == '-') {
            nextCh();
        }
        while (index < size && (isDigit(ch()) || ch() == '.')) {
            nextCh();
        }

        try {
            return new Const<>(constParser.parse(data.substring(begin, index)));
        } catch (NumberFormatException e) {
            throw new ParsingException("Input data is incorrect: Error while parsing constant: " + e.getMessage());
        }
    }

    private boolean isDigit(char symbol) {
        return Character.isDigit(symbol);
    }

    private boolean isLetter(char symbol) {
        return Character.isLetter(symbol);
    }

    private char ch() {
        return currentSymbol;
    }

    private void nextCh() {
        index++;
        if (index < size) {
            currentSymbol = data.charAt(index);
        }
    }

    private void prevCh() {
        if (index > 0) {
            index--;
            currentSymbol = data.charAt(index);
        }
    }

    private int getPriority(Token token) {
        switch (token) {
            case BEGIN:
                return 0;
            case ADD:
            case SUBTRACT:
                return 1;
            case MULTIPLY:
            case DIVIDE:
                return 2;
            default:
                return 3;
        }
    }


    private Token getToken(char ch) throws ParsingException {
        if (isDigit(ch) || ch == '-' && index < size - 1 && isDigit(data.charAt(index + 1))
                && (index == 0 || isSign(data.charAt(index - 1)) || data.charAt(index - 1) == '(')) {
            return Token.CONST;
        }

        switch (ch) {
            case '+':
                return Token.ADD;
            case '-':
                return Token.SUBTRACT;
            case '*':
                return Token.MULTIPLY;
            case '/':
                return Token.DIVIDE;
            case '(':
                return Token.OPENING_BRACKET;
            case ')':
                return Token.CLOSING_BRACKET;
            default:
                if (isLetter(ch)) {
                    lastWord = parseWord();
                    if (correctVariables.contains(lastWord)) {
                        return Token.VARIABLE;
                    } else {
                        throw new ParsingException(
                                "Input data is incorrect: wrong variable or function name "
                                        + lastWord + " at position " + index
                        );
                    }
                } else {
                    throw new ParsingException(
                            "Input data is incorrect: cannot recognize symbol " + ch + " at position " + index
                    );
                }
        }
    }

    private String parseWord() {
        int begin = index;
        while (index < size && isLetter(ch())) {
            nextCh();
        }
        return data.substring(begin, index);
    }

    private boolean isSign(char ch) {
        return ch == '+' || ch == '-' || ch == '/' || ch == '*';
    }
}

enum Token {
    BEGIN,
    ADD, SUBTRACT,
    MULTIPLY, DIVIDE,
    NEGATE,
    VARIABLE, CONST,
    OPENING_BRACKET, CLOSING_BRACKET
}
