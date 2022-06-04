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
 * Computes the shortest paths using Dijkstra's algorithm.
 *
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
        //return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    /*

    dijkstraShortestPath(G graph, V start)
    Set known; Map edgeTo, distTo;
    initialize distTo with all nodes mapped to ∞, except start to 0

    while (there are unknown vertices):
        let u be the closest unknown vertex
        known.add(u)
        for each edge (u,v) to unknown v with weight w:
            oldDist = distTo.get(v)      // previous best path to v
            newDist = distTo.get(u) + w  // what if we went through u?
            if (newDist < oldDist):
                distTo.put(v, newDist)
                edgeTo.put(v, u)
     */
    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {

        HashMap<V, E> edgeTo = new HashMap<>();
        HashMap<V, Double> distTo = new HashMap<>(); //Map each vertex with its shortest distance from source
        ExtrinsicMinPQ<V> pq = createMinPQ(); //a priority queue to store all the unknown vertices

        //If start and end are the same, the SPT will be empty.
        if (Objects.equals(start, end)) {
            return edgeTo;
        }

        distTo.put(start, 0.0);
        pq.add(start, 0.0);

        while (!pq.isEmpty()) {
            V u = pq.removeMin(); //pop the closest unknown vertex and explore all its outgoing edges.
            for (E edge : graph.outgoingEdgesFrom(u)) {

                double oldDist = distTo.getOrDefault(edge.to(), Double.POSITIVE_INFINITY);
                double newDist = distTo.get(u) + edge.weight();

                if (newDist < oldDist) {
                    distTo.put(edge.to(), newDist);
                    edgeTo.put(edge.to(), edge);

                    //update new pq priority with the new shortest distance
                    if (pq.contains(edge.to())) {
                        pq.changePriority(edge.to(), newDist);
                    } else {
                        //if it is a new discovered vertex, just add it to pq and pending explore
                        pq.add(edge.to(), newDist);
                    }
                }
            }
            //if found the target, just return the shortest path tree map
            if (Objects.equals(u, end)) {
                return edgeTo;
            }
        }
        return edgeTo;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {

        //source and target are the same vertex, just create the shortest path with a single vertex
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }

        E edge = spt.get(end);

        //check if no edge connected to target, there is never a path connect source and target
        if (edge == null) {
            return new ShortestPath.Failure<>();
        }

        List<E> edges = new ArrayList<>();
        boolean notStartYet = true;
        while (notStartYet) {
            //add every edge with in the shortest path.
            edges.add(edge);

            //since it is traversing backward starting the edge connected the end node
            if (Objects.equals(edge.from(), start)) {
                //once reach the start node, break out the loop
                notStartYet = false;
            }
            edge = spt.get(edge.from());
        }
        //reverse the order of edges: end to start -> start to end
        edges = reversePath(edges);
        return new ShortestPath.Success<>(edges);
    }

    //helper function
    private List<E> reversePath(List<E> list) {
        List<E> edges = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            edges.add(list.get(i));
        }
        return edges;
    }

}
