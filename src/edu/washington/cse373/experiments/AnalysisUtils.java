package edu.washington.cse373.experiments;

import org.openjdk.jol.info.GraphLayout;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * This class contains utility methods for running experiments.
 * You do NOT need to understand how each method works.
 * Instead, focus on reading the method header comments so you understand what each method DOES.
 */
public class AnalysisUtils {

    /**
     * Returns the approximate amount of memory used by the entire object, in bytes.
     */
    public static long estimateObjectMemoryUsage(Object obj) {
        return GraphLayout.parseInstance(obj).totalSize();
    }

    /**
     * Constructs a stream of longs starting from `startInclusive`, going to `endInclusive`,
     * in `step` increments.
     * (`endInclusive` is not actually included if it doesn't line up with `step`, e.g.
     * `range(0, 10, 3)` would produce a stream of [0, 3, 6, 9].)
     */
    public static List<Long> range(long startInclusive, long endInclusive, long step) {
        long length = (endInclusive - startInclusive) / step;
        return LongStream.rangeClosed(0, length).map(x -> x * step + startInclusive)
            .boxed().collect(Collectors.toList());
    }
}
