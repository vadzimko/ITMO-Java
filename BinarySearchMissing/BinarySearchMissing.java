package search;

public class BinarySearchMissing {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java BinarySearch x a1 a2 a3 ... an");
            return;
        }
        // args[i] - integer numbers between [Integer.MIN_VALUE ... Integer.MAX_VALUE] for all i
        int x = Integer.valueOf(args[0]);

        // numbers.length = args.length - 1
        int[] numbers = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            numbers[i - 1] = Integer.valueOf(args[i]);
        }

        //int answer = iterativeBinarySearch(x, numbers);
        int answer = recursiveBinarySearch(x, numbers, -1, numbers.length);

        // if numbers[answer] != x --> array doesn't contain X --> R = -answer - 1
        if (answer == numbers.length || numbers[answer] != x) {
            System.out.println(-answer - 1);
        } else {
            System.out.println(answer);
        }

    }

    // condition for BinSearch is "x >= numbers[i]"

    // Pre: numbers != null and numbers[i] >= numbers[i + 1] for all i between [0 ... numbers.length - 1]
    // Post: if array contains fitting element - numbers[R] fits the condition, numbers[R - 1] doesn't
    //      else R = numbers.length and numbers[i] == numbers'[i] for all i between [0 ... numbers.length - 1]
    private static int iterativeBinarySearch(int x, int[] numbers) {
        int left = -1;
        int right = numbers.length;

        // right <= right'
        // left >= left'
        // left < right
        // numbers[right] fits the condition
        // numbers[left] doesn't fit the condition
        while (right - left > 1) {
            int mid = left + (right - left) / 2;
            // right - left >= 2
            // mid = left + (right - left) / 2 >= left + 2 / 2 = left + 1 > left
            // mid = (left + right) / 2 <= (right - 2 + right) / 2 = right - 1 < right
            // left < mid < right
            // 0 <= mid <= numbers.length - 1
            // left > left' xor right < right' == 1
            if (x >= numbers[mid]) {
                // x >= numbers[(left + right) / 2]
                // x >= numbers[i] for all i which: i >= (left + right) / 2
                // part of array from ((left + right) / 2)..(numbers.length - 1) fits the condition
                // so we can forget about elements ((left + right) / 2 + 1)..(numbers.length - 1) and not consider them
                // and move the right border to (left + right) / 2 (> left' and < right')
                right = mid;
            } else {
                // x < numbers[(left + right) / 2]
                // x < numbers[i] for all i which: i <= (left + right) / 2
                // part of array from 0..(left + right) / 2 doesn't fit the condition
                // so we can forget about elements 0..(left + right) / 2 - 1 and not consider them
                // and move the left border to (left + right) / 2 (< right' and > left')
                left = mid;
            }
        }
        // right - left = 1
        // left doesn't fit the condition
        // right fits the condition
        return right;
    }

    // Pre: numbers != null and numbers[i] >= numbers[i + 1] for all i between[0 ... numbers.length], left < right
    //      and (numbers[right] fits the condition and numbers[left] doesn't fit the condition)
    // Post: if array contains fitting element - numbers[R] fits the condition, numbers[R - 1] doesn't
    //       else R = numbers.length and numbers[i] == numbers'[i] for all i between [0 ... numbers.length - 1]
    private static int recursiveBinarySearch(int x, int[] numbers, int left, int right) {
        if (right - left < 2) {
            // right > left --> right = left + 1
            // numbers[right] fits the condition
            // numbers[left] doesn't fit the condition
            return right;
        }

        // right - left >= 2
        int mid = left + (right - left) / 2;
        // mid = left + (right - left) / 2 >= left + 2 / 2 = left + 1 > left
        // mid = (left + right) / 2 <= (right - 2 + right) / 2 = right - 1 < right
        // left < mid < right
        // 0 <= mid <= numbers.size - 1
        if (x >= numbers[mid]) {
            // x >= numbers[(left + right) / 2]
            // x >= numbers[i] for all i which: i >= (left + right) / 2
            // part of array from ((left + right) / 2)..(numbers.length - 1) fits the condition
            // so we can forget about elements ((left + right) / 2 + 1)..(numbers.length - 1) and not consider them
            // and move the right border to (left + right) / 2 (> left' and < right')
            return recursiveBinarySearch(x, numbers, left, mid);
        } else {
            //x < numbers[(left + right) / 2]
            //x < numbers[i] for all i which: i <= (left + right) / 2
            //part of array from 0..(left + right) / 2 doesn't fit the condition
            //so we can forget about elements 0..(left + right) / 2 and not consider them
            //and move the left border to (left + right) / 2 (< right' and > left')
            return recursiveBinarySearch(x, numbers, mid, right);
        }
    }
}
