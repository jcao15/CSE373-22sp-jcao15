package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A quick-union-by-size data structure with path compression.
 *
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;
    Set<T> seenNode;
    Map<T, Integer> parentIdx;
    Map<T, Integer> weights;

    /*
        map each node with corresponding parent node.
        It's difficult to get the corresponding index of array representation.
        Instead, I map each node with its parent node as reference
    */
    Map<T, T> nodeDict;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        pointers = new ArrayList<>(); //an array to record each item index pointers in a subset
        seenNode = new HashSet<>(); //Used for path compression
        nodeDict = new HashMap<>();
        parentIdx = new HashMap<>(); //root index is corresponding disjoint set's weight * -1
        weights = new HashMap<>(); //record each subset's weight, root as key

    }

    @Override
    public void makeSet(T item) {

        //Elements must be unique across disjoint sets
        if (nodeDict.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        pointers.add(-1); //initially, new set with item has -1 value in array representation
        nodeDict.put(item, item); //initially, item node's parent node is itself
        weights.put(item, 1); //only one item in subset with weight 1
        parentIdx.put(item, pointers.size() - 1);
        /*
            new added node is at the end of the pointers array representation.
            which has the index of the array's length - 1.
        */
    }

    @Override
    public int findSet(T item) {
        //check if the searching item exist in the disjoint sets
        if (!nodeDict.containsKey(item)) {
            throw new IllegalArgumentException();
        }

        T root = findRoot(item);

        return parentIdx.get(root);

    }

    private T findRoot(T item) {

        while (!Objects.equals(item, nodeDict.get(item))) {
            seenNode.add(item);
            item = nodeDict.get(item);
        }
        // while (parentIdx.get(item) >  0) {
        //     item = nodeDict.get(item);
        // }
        T tempRoot = item;
        //after the while loop break, item should be the overallRoot
        //Path compression to have all the seen nodes in front of the search node point to overallRoot.
        if (!seenNode.isEmpty()) {
            for (T node : seenNode) {
                nodeDict.replace(node, nodeDict.get(node), tempRoot);
                //get the overallRoot index in array representation
                int updatedIdx = parentIdx.get(tempRoot);
                pointers.add(parentIdx.get(node), updatedIdx);
            }
        }
        //clear up seenNode set after done the path compression
        seenNode.clear();

        return item;
    }


    @Override
    public boolean union(T item1, T item2) {

        if (!nodeDict.containsKey(item1) || !nodeDict.containsKey(item2)) {
            throw new IllegalArgumentException();
        }

        T root1 = findRoot(item1);
        T root2 = findRoot(item2);

        int root1Id = findSet(item1);
        int root2Id = findSet(item2);

        if (root1Id == root2Id) {
            return false;
        }

        if (weights.get(root1) >= weights.get(root2)) {

            pointers.set(parentIdx.get(root2), parentIdx.get(root1));
            parentIdx.replace(root2, parentIdx.get(root1));
            nodeDict.replace(root2, root1);
            weights.replace(root1, weights.get(root1) + weights.get(root2));
            weights.remove(root2);
            pointers.set(parentIdx.get(root1), -1 * weights.get(root1));

        } else {
            pointers.set(parentIdx.get(root1), parentIdx.get(root2));
            parentIdx.replace(root1, parentIdx.get(root2));
            nodeDict.replace(root1, root2);
            weights.replace(root2, weights.get(root1) + weights.get(root2));
            weights.remove(root1);
            pointers.set(parentIdx.get(root2), -1 * weights.get(root2));
        }
        return true;
    }
}
