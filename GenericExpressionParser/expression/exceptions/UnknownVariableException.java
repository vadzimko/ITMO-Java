package expression.exceptions;

public class UnknownVariableException extends RuntimeException {
    public UnknownVariableException(String msg) {
        super("Unknown variable: " + msg);
    }
}
