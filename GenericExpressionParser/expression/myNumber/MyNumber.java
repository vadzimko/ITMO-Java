package expression.myNumber;

public interface MyNumber<T> {
    MyNumber<T> add(MyNumber<T> element);

    MyNumber<T> subtract(MyNumber<T> element);

    MyNumber<T> divide(MyNumber<T> element);

    MyNumber<T> multiply(MyNumber<T> element);

    MyNumber<T> negate();

    T getValue();
}
