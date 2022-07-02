package deques.experiments;

import deques.Deque;

import java.util.function.Supplier;

public class Utils {
    public static Deque<Long> createDequeOfSize(long size, Supplier<Deque<Long>> dequeConstructor) {
        Deque<Long> deque = dequeConstructor.get();
        for (long i = 0; i < size; i++) {
            deque.addLast(i);
        }
        return deque;
    }
}
