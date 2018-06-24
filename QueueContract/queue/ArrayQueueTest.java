package queue;

public class ArrayQueueTest {
    public static void fill(ArrayQueue queue) {
        for (int i = 0; i < 6; i++) {
            queue.enqueue(i * 11);
        }
    }

    public static void dump(ArrayQueue queue) {
        int i = 1;
        while (!queue.isEmpty()) {
            System.out.println("size: "
                    + queue.size()
                    + "; element № " + i++ + ": "
                    + queue.dequeue()
            );
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        fill(queue1);
        fill(queue2);
        dump(queue1);
        dump(queue2);
    }
}
