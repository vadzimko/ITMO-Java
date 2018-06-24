package queue;

public class ArrayQueueADT {
    private int head, tail;
    private Object[] elements = new Object[2];

    // Pre: element != null
    // Post: size() = size'() + 1
    // & elements[(tail - 1) % elements.length] == element
    // & elements[(head + i) % elements.length] == elements'[(head' + i) % elements'.length] for all i = 0..size() - 1
    // & 0 <= tail < elements.length
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null : "element to enqueue is null!";
        ensureCapacity(queue, size(queue) + 1);
        queue.elements[queue.tail++] = element;
        queue.tail %= queue.elements.length;
    }

    // Pre: -
    // Post: elements[(head + i) % elements.length] == elements'[(head' + i) % elements'.length]
    // for all i = 0..size()
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity < queue.elements.length) {
            return;
        }

        Object[] newElements = makeArrayFromQueue(queue, capacity * 2);
        queue.tail = size(queue);
        queue.head = 0;
        queue.elements = newElements;
    }

    // Pre: size > 0
    // Post: R == elements[head]
    // & elements[(head + i) % elements.length] == elements'[(head' + i) % elements'.length] for all i = 0..size()
    // & size() == size()' - 1
    // & 0 <= head < elements.length
    public static Object dequeue(ArrayQueueADT queue) {
        assert size(queue) > 0 : "size == 0";

        Object element = queue.elements[queue.head++];
        queue.head %= queue.elements.length;
        return element;
    }

    // Pre: size > 0
    // Post: R = elements[head]
    // & elements[i] == elements'[i] for all i = 0..elements.length - 1
    public static Object element(ArrayQueueADT queue) {
        assert size(queue) > 0 : "size == 0";

        return queue.elements[queue.head];
    }

    // Pre: -
    // Post: R == (tail - head + elements.length) % elements.length
    // & head == head'
    // & tail == tail'
    public static int size(ArrayQueueADT queue) {
        if (queue.head > queue.tail) {
            return queue.elements.length - queue.head + queue.tail;
        } else {
            return queue.tail - queue.head;
        }
    }

    public static boolean isEmpty(ArrayQueueADT queue) { return size(queue) == 0; }

    // Pre: -
    // Post: size() == 0
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[2];
        queue.head = 0;
        queue.tail = 0;
    }

    // Pre: size >= size()
    // Post: newElements[i] == elements'[(head' + i) % elements'.length]
    // for all i = 0..size()
    // & elements[i] == elements'[i] for all i = 0..elements.length - 1
    // & head == head'
    // & tail == tail'
    private static Object[] makeArrayFromQueue(ArrayQueueADT queue, int size) {
        assert size >= size(queue) : "Cannot copy queue to array of smallest size";

        Object[] newElements = new Object[size];
        if (queue.tail >= queue.head) {
            System.arraycopy(queue.elements, queue.head, newElements, 0, size(queue));
        } else {
            System.arraycopy(queue.elements, queue.head, newElements, 0, queue.elements.length - queue.head);
            System.arraycopy(queue.elements, 0, newElements,
                    queue.elements.length - queue.head, size(queue) - (queue.elements.length - queue.head)
            );
        }
        return newElements;
    }

    // Pre: -
    // Post: R[i] ==  elements'[(head' + i) % elements'.length] for all i = 0..size()
    // & R.length == size()
    public static Object[] toArray(ArrayQueueADT queue) {
        return makeArrayFromQueue(queue, size(queue));
    }
}
