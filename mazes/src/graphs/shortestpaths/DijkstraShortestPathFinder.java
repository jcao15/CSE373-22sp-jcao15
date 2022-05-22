package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        HashMap<V, E> edgeTo = new HashMap<>();
        HashMap<V, Double> distTo = new HashMap<>();
        ExtrinsicMinPQ<V> perimeter = createMinPQ();
        perimeter.add(start, 0.0);
        distTo.put(start, 0.0);
        if (Objects.equals(start, end)) {
            return edgeTo;
        }

        while (!perimeter.isEmpty()) {
            V takenEdge = perimeter.removeMin();
            for (E edge : graph.outgoingEdgesFrom(takenEdge)) {
                if (edge != end) {
                    double oldDist;
                    oldDist = distTo.getOrDefault(edge.to(), Double.POSITIVE_INFINITY);
                    double newDist = distTo.get(takenEdge) + edge.weight();

                    if (newDist < oldDist) {
                        distTo.put(edge.to(), newDist);
                        edgeTo.put(edge.to(), edge);
                        if (perimeter.contains(edge.to())) {
                            perimeter.changePriority(edge.to(), newDist);
                        } else {
                            perimeter.add(edge.to(), newDist);
                        }
                    }
                }
            }
            if (Objects.equals(takenEdge, end)) {
                return edgeTo;
            }
        }
        return edgeTo;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }

        E edge = spt.get(end);
        if (edge == null) {
            return new ShortestPath.Failure<>();
        }

        List<E> listEdge = new ArrayList<>();
        boolean x = true;
        while (x) {
            listEdge.add(edge);
            if (Objects.equals(edge.from(), start)) {
                x = false;
            }
            edge = spt.get(edge.from());
        }
        listEdge = reverse(listEdge);
        return new ShortestPath.Success<>(listEdge);
    }

    private List<E> reverse(List<E> list) {
        List<E> listEdge = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            listEdge.add(list.get(i));
        }
        return listEdge;
    }

}
