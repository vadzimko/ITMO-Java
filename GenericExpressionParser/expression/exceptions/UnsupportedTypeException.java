package expression.exceptions;

public class UnsupportedTypeException extends RuntimeException {
    public UnsupportedTypeException(String msg) {
        super("Type: '" + msg + "' was not expected");
    }
}
