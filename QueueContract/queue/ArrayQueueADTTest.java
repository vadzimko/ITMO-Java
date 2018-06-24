package queue;

public class ArrayQueueADTTest {
    public static void fill(ArrayQueueADT queue) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, i * 11);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        int i = 1;
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println("size: "
                    + ArrayQueueADT.size(queue)
                    + "; element № " + i++ + ": "
                    + ArrayQueueADT.dequeue(queue)
            );
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = new ArrayQueueADT();
        ArrayQueueADT queue2 = new ArrayQueueADT();
        fill(queue1);
        fill(queue2);
        dump(queue1);
        dump(queue2);
    }
}
