package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.UnionBySizeCompressingDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        //return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    /*
        kruskalMST(G graph)
          Set(?) msts; Set finalMST;
          initialize msts with each vertex as single-element MST
          sort all edges by weight (smallest to largest)

          for each edge (u,v) in ascending order:
            uMST = msts.find(u)
            vMST = msts.find(v)
            if (uMST != vMST):
              finalMST.add(edge (u, v))
              msts.union(uMST, vMST)
    */
    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.

        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));

        List<V> vertices = new ArrayList<>(graph.allVertices());

        DisjointSets<V> disjointSets = createDisjointSets();
        Set<E> mst = new HashSet<>();

        //call makeSet on each vertex to create subsets of disjoint set
        for (V vertex : vertices) {
            disjointSets.makeSet(vertex);
        }
        for (E edge : edges) {
            V uMST = edge.from();
            V vMST = edge.to();

            if (disjointSets.findSet(uMST) != disjointSets.findSet(vMST)) {
                mst.add(edge);
                disjointSets.union(uMST, vMST);
            }
        }
        //Check if the MST is completed: edge's amount in MST should be equal to vertices - 1
        if (mst.size() == vertices.size() - 1 || vertices.size() == 0) {
            return new MinimumSpanningTree.Success<>(mst);
        } else {
            return new MinimumSpanningTree.Failure<>();
        }


    }
}
