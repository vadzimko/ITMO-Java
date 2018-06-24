package expression.parser;

import expression.ExpressionObject.Const;
import expression.ExpressionObject.Variable;
import expression.Operation.BinaryOperation.*;

import expression.Operation.UnaryOperation.CheckedNegate;
import expression.TripleExpression;
import expression.exceptions.ParsingException;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExpressionParser implements Parser {
    private Set<String> correctVariables = new HashSet<>(Arrays.asList("x", "y", "z"));
    //private Set<String> correctFunctionNames = new HashSet<>();
    private int index;
    private int size;
    private String data;
    private char currentSymbol;
    private String lastWord;

    @Override
    public TripleExpression parse(String input) throws ParsingException {
        data = deleteSpaces(input);
        size = data.length();
        if (size == 0) {
            return new Const(0);
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

    private TripleExpression parseData(int priority, boolean isBrackets) throws ParsingException{
        TripleExpression left = parseExpressionObject();

        BigInteger a = new BigInteger("11");
        while (index < size) {
            Token token = getToken(ch());
            nextCh();
            if (token == Token.CLOSING_BRACKET && isBrackets || getPriority(token) <= priority) {
                prevCh();
                return left;
            }

            TripleExpression right = parseData(getPriority(token), isBrackets);
            switch (token) {
                case ADD:
                    left = new CheckedAdd(left, right);
                    break;
                case SUBTRACT:
                    left = new CheckedSubtract(left, right);
                    break;
                case MULTIPLY:
                    left = new CheckedMultiply(left, right);
                    break;
                case DIVIDE:
                    left = new CheckedDivide(left, right);
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


    private TripleExpression parseExpressionObject() throws ParsingException{
        Token token = getToken(ch());

        switch (token) {
            case VARIABLE:
                return new Variable(lastWord);
            case CONST:
                return parseConstant();
            case OPENING_BRACKET:
                nextCh();
                TripleExpression temp = parseData(0, true);
                nextCh();
                return temp;
            case SUBTRACT:
                nextCh();
                return new CheckedNegate(parseData(getPriority(Token.NEGATE), false));
            default:
                throw new ParsingException(
                        "Input data is incorrect: variable, constant or subexpression was expected at position "
                        + index
                );
        }
    }

    private TripleExpression parseConstant() throws ParsingException {
        int begin = index;
        if (ch() == '-') {
            nextCh();
        }
        while (index < size && isDigit(ch())) {
            nextCh();
        }

        try {
            return new Const(Integer.parseInt(data.substring(begin, index)));
        } catch (NumberFormatException e) {
            throw new ParsingException("Input data is incorrect: Error while parsing constant(overflow): " + e.getMessage());
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


    private Token getToken(char ch) throws ParsingException{
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
                                "Input data is incorrect: wrong variable name " + lastWord + " at position " + index
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
        return ch == '+' || ch == '-' ||ch == '/' || ch == '*';
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
