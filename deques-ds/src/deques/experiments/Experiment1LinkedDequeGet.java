package deques.experiments;

import deques.Deque;
import deques.LinkedDeque;
import edu.washington.cse373.experiments.AnalysisUtils;
import edu.washington.cse373.experiments.PlotWindow;

import java.util.List;
import java.util.function.LongUnaryOperator;

import static deques.experiments.Utils.createDequeOfSize;

public class Experiment1LinkedDequeGet {
    /*
    Note: please do not change these constants (or the constants in any of the other experiments)
    while working on your writeup
    */
    public static final int NUM_TIMES_TO_REPEAT = 1000;
    public static final long MAX_SIZE = 20000;
    public static final long STEP = 100;

    long dummy = 0;

    public static void main(String[] args) {
        new Experiment1LinkedDequeGet().run();
    }

    public void run() {
        List<Long> sizes = AnalysisUtils.range(STEP, MAX_SIZE, STEP);

        PlotWindow.launch("Experiment 1", "Deque Size", "Elapsed Time (ns)",
            new LongUnaryOperator[]{this::f1, this::f2, this::f3},
            new String[]{"f1", "f2", "f3"}, sizes, 1000, .05);
    }

    protected Deque<Long> createLinkedDequeOfSize(long size) {
        return createDequeOfSize(size, LinkedDeque::new);
    }

    public long f1(long size) {
        return timeGetsAtIndex(size, 0);
    }

    public long f2(long size) {
        return timeGetsAtIndex(size, size - 1);
    }

    public long f3(long size) {
        return timeGetsAtIndex(size, size / 2);
    }

    /**
     * Returns the runtime in nanoseconds of `LinkedDeque.get` calls on a deque with the given size
     * and at the given index.
     */
    public long timeGetsAtIndex(long size, long index) {
        // construct deque before starting the timing
        Deque<Long> deque = createLinkedDequeOfSize(size);

        long start = System.nanoTime();

        /*
        try getting the same thing multiple times, since a single `get` is too fast to reliably
        measure runtime. By testing the same amount of `get`s for each index, we come up with a
        kind of "average" runtime of `get` for that index.
         */
        for (int i = 0; i < NUM_TIMES_TO_REPEAT; i++) {
            // mutate a dummy field to discourage Java from doing any clever optimization
            this.dummy ^= deque.get((int) index);
        }

        return System.nanoTime() - start;
    }
}
