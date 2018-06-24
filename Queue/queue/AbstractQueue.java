package queue;

public abstract class AbstractQueue implements Queue {

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public void enqueue(Object element) {
        assert element != null : "element to enqueue is null!";

        enqueueImpl(element);
    }

    protected abstract void enqueueImpl(Object element);

    @Override
    public Object element() {
        assert size() > 0 : "size == 0";

        return elementImpl();
    }

    protected abstract Object elementImpl();

    @Override
    public Object dequeue() {
        assert size() > 0 : "size == 0";

        return dequeueImpl();
    }

    protected abstract Object dequeueImpl();

    protected Object[] makeArrayFromQueue(int size) {
        Object[] elements = new Object[size];
        for (int i = 0; i < size(); i++) {
            elements[i] = dequeue();
            enqueue(elements[i]);
        }
        return elements;
    }

    @Override
    public Object[] toArray() {
        return makeArrayFromQueue(size());
    }
}
