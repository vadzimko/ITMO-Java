package queue;

public class ArrayQueueModuleTest {
    public static void fill() {
        for (int i = 0; i < 7; i++) {
            ArrayQueueModule.enqueue(i * 11);
        }
    }

    public static void dump() {
        int i = 1;
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println("size: "
                    + ArrayQueueModule.size()
                    + "; element № " + i++ + ": "
                    + ArrayQueueModule.dequeue()
            );
        }
    }

    public static void main(String[] args) {
        fill();
        dump();
        ArrayQueueModule.clear();
        fill();
        dump();
        System.out.println(ArrayQueueModule.toArray());
    }
}
