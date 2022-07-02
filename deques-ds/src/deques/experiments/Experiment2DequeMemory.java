package deques.experiments;

import deques.ArrayDeque;
import deques.Deque;
import deques.LinkedDeque;
import edu.washington.cse373.experiments.PlotWindow;

import java.util.List;
import java.util.function.LongUnaryOperator;

import static deques.experiments.Utils.createDequeOfSize;
import static edu.washington.cse373.experiments.AnalysisUtils.estimateObjectMemoryUsage;
import static edu.washington.cse373.experiments.AnalysisUtils.range;

public class Experiment2DequeMemory {
    /*
    Note: please do not change these constants (or the constants in any of the other experiments)
    while working on your writeup
    */
    public static final long MAX_SIZE = 10000;
    public static final long STEP = 100;

    public static void main(String[] args) {
        new Experiment2DequeMemory().run();
    }

    public void run() {
        List<Long> sizes = range(0, MAX_SIZE, STEP);

        /*
        Note: we're measuring memory usage, which is deterministic---that is, two instances of a
        data structure with the same elements will always use the same amount of memory (assuming
        our data structures are implemented correctly).
        So, there's no need to conduct multiple trials, as they would end up being exactly the same.
        */

        PlotWindow.launch("Experiment 2", "Deque Size", "Memory Used (bytes)",
            new LongUnaryOperator[]{this::f1, this::f2},
            new String[]{"f1", "f2"}, sizes);
    }

    protected Deque<Long> createLinkedDequeOfSize(long size) {
        return createDequeOfSize(size, LinkedDeque::new);
    }

    protected Deque<Long> createArrayDequeOfSize(long size) {
        return createDequeOfSize(size, ArrayDeque::new);
    }

    public long f1(long size) {
        Deque<Long> deque = createLinkedDequeOfSize(size);

        // estimate memory usage of the deque (including memory used by its items)
        return estimateObjectMemoryUsage(deque);
    }

    public long f2(long size) {
        Deque<Long> deque = createArrayDequeOfSize(size);

        return estimateObjectMemoryUsage(deque);
    }
}
