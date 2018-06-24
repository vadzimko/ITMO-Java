package queue;

public class ArrayQueue extends AbstractQueue implements Queue {
    private int head, tail;
    private Object[] elements = new Object[2];

    @Override
    public void enqueueImpl(Object element) {
        ensureCapacity(size() + 1);
        elements[tail++] = element;
        tail %= elements.length;
    }

    private void ensureCapacity(int capacity) {
        if (capacity < elements.length) {
            return;
        }

        Object[] newElements = makeArrayFromQueue(capacity * 2);
        tail = size();
        head = 0;
        elements = newElements;
    }

    @Override
    public Object dequeueImpl() {
        assert size() > 0 : "size == 0";
        Object element = elements[head++];
        head %= elements.length;
        return element;
    }

    @Override
    public Object elementImpl() {
        return elements[head];
    }

    @Override
    public int size() {
        if (head > tail) {
            return elements.length - head + tail;
        } else {
            return tail - head;
        }
    }

    @Override
    public void clear() {
        elements = new Object[2];
        head = 0;
        tail = 0;
    }
}
