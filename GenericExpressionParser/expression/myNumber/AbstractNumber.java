package expression.myNumber;

public abstract class AbstractNumber<T> implements MyNumber<T>{
    protected T value;

    public AbstractNumber(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
