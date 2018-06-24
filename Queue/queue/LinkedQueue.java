package queue;

public class LinkedQueue extends AbstractQueue implements Queue{
    private class Node {
        private Object value;
        private Node next;
    }

    private int size;
    private Node head = new Node();
    private Node tail = head;

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void enqueueImpl(Object element) {
        tail.value = element;
        tail.next = new Node();
        tail = tail.next;
        size++;
    }

    @Override
    protected Object elementImpl() {
        return head.value;
    }

    @Override
    protected Object dequeueImpl() {
        Object value = head.value;
        head = head.next;
        size--;
        return value;
    }

    @Override
    public void clear() {
        head = tail;
        size = 0;
    }
}
