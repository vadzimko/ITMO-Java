package queue;

public interface Queue {
    // Pre: element != null
    // Post: size() == size'() + 1
    void enqueue(Object element);

    // Pre: size > 0
    // Post: size() == size'() - 1
    Object dequeue();

    // Pre: size > 0
    // Post: queue == queue'
    Object element();

    // Pre: -
    // Post: queue == queue'
    int size();

    // Pre: -
    // Post: queue == queue'
    boolean isEmpty();

    // Pre: -
    // Post: size() == 0
    void clear();

    // Pre: -
    // Post: queue == queue'
    Object[] toArray();
}
