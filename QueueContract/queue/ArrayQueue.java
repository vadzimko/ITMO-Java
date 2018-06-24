package queue;

public class ArrayQueue {
    private int head, tail;
    private Object[] elements = new Object[2];

    // Pre: element != null
    // Post: size() = size'() + 1
    // & elements[(tail - 1) % elements.length] == element
    // & elements[(head + i) % elements.length] == elements'[(head' + i) % elements'.length] for all i = 0..size() - 1
    // & 0 <= tail < elements.length
    public void enqueue(Object element) {
        assert element != null : "element to enqueue is null!";

        ensureCapacity(size() + 1);
        elements[tail++] = element;
        tail %= elements.length;
    }

    // Pre: -
    // Post: elements[(head + i) % elements.length] == elements'[(head' + i) % elements'.length]
    // for all i = 0..size()
    private void ensureCapacity(int capacity) {
        if (capacity < elements.length) {
            return;
        }

        Object[] newElements = makeArrayFromQueue(capacity * 2);
        tail = size();
        head = 0;
        elements = newElements;
    }

    // Pre: size > 0
    // Post: R == elements[head]
    // & elements[(head + i) % elements.length] == elements'[(head' + i) % elements'.length] for all i = 0..size()
    // & size() == size()' - 1
    // & 0 <= head < elements.length
    public Object dequeue() {
        assert size() > 0 : "size == 0";
        Object element = elements[head++];
        head %= elements.length;
        return element;
    }

    // Pre: size > 0
    // Post: R = elements[head]
    // & elements[i] == elements'[i] for all i = 0..elements.length - 1
    public Object element() {
        assert size() > 0 : "size == 0";

        return elements[head];
    }

    // Pre: -
    // Post: R == (tail - head + elements.length) % elements.length
    // & head == head'
    // & tail == tail'
    public int size() {
        if (head > tail) {
            return elements.length - head + tail;
        } else {
            return tail - head;
        }
    }

    public boolean isEmpty() { return size() == 0; }

    // Pre: -
    // Post: size() == 0
    public void clear() {
        elements = new Object[2];
        head = 0;
        tail = 0;
    }

    // Pre: size >= size()
    // Post: newElements[i] == elements'[(head' + i) % elements'.length]
    // for all i = 0..size()
    // & elements[i] == elements'[i] for all i = 0..elements.length - 1
    // & head == head'
    // & tail == tail'
    private Object[] makeArrayFromQueue(int size) {
        assert size >= size() : "Cannot copy queue to array of smallest size";

        Object[] newElements = new Object[size];
        if (tail >= head) {
            System.arraycopy(elements, head, newElements, 0, size());
        } else {
            System.arraycopy(elements, head, newElements, 0, elements.length - head);
            System.arraycopy(elements, 0, newElements,
                    elements.length - head, size() - (elements.length - head)
            );
        }
        return newElements;
    }

    // Pre: -
    // Post: R[i] ==  elements'[(head' + i) % elements'.length] for all i = 0..size()
    // & R.length == size()
    public Object[] toArray() {
        return makeArrayFromQueue(size());
    }
}
