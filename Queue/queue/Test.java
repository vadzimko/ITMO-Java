package queue;

public class Test {
    public static void main(String[] args) {
        Queue q = new LinkedQueue();
        Integer a = 5;
        q.enqueue(a);
        System.out.println(q.element());

    }
}
