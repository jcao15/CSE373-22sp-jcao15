package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    List<PriorityNode<T>> items;

    //create a hashmap to store the priority Node with corresponding index in items ArrayList.
    HashMap<T, Integer> pNodeMap;
    private int size;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        pNodeMap = new HashMap<>();
        size = 0;
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {

        //Get the priority Nodes<T> at index a,b respectively
        PriorityNode<T> itemA = items.get(a);
        PriorityNode<T> itemB = items.get(b);

        //swap in the ArrayList
        items.set(a, itemB);
        items.set(b, itemA);

        //update the HashMap as well
        pNodeMap.replace(itemA.getItem(), b);
        pNodeMap.replace(itemB.getItem(), a);

    }

    @Override
    public void add(T item, double priority) {

        if (contains(item)) {
            throw new IllegalArgumentException();
        }

        int idx = 0;
        items.add(new PriorityNode<>(item, priority));
        size++;

        if (size > 1) {
            idx = swimUp(size - 1);
        }

        //update the new add Node in HashMap
        pNodeMap.put(item, idx);

    }

    //helper function of add()
    private int swimUp(int currentIdx) {

        int parentIdx = (currentIdx - 1) / 2;

        while (isValidIndex(parentIdx) && items.get(parentIdx).getPriority() > items.get(currentIdx).getPriority()) {

            swap(parentIdx, currentIdx);
            currentIdx = parentIdx;
            parentIdx = (currentIdx - 1) / 2;
        }
        return currentIdx;
    }

    @Override
    public boolean contains(T item) {
        //check HashMap to see if the item key exist or not.
        return pNodeMap.containsKey(item);
    }

    @Override
    public T peekMin() {

        if (size == 0) {
            throw new NoSuchElementException();
        }
        return items.get(0).getItem();
    }

    @Override
    public T removeMin() {

        if (size == 0) {
            throw new NoSuchElementException();
        }
        T min = items.get(0).getItem();
        pNodeMap.remove(min);
        items.set(0, items.get(size - 1));
        items.remove(size - 1);
        size--;

        if (size > 1) {
            sinkDown(0);
        }
        return min;
    }

    private void sinkDown(int currentIndex) {

        int leftChildIdx = currentIndex * 2 + 1;
        boolean isLeftChildValid = isValidIndex(leftChildIdx);
        int smallerChildIdx;
        int rightChildIdx;

        while (isLeftChildValid) {
            smallerChildIdx = leftChildIdx;
            rightChildIdx = currentIndex * 2 + 2;

            //check whether leftChild is smaller or rightChild
            if (isValidIndex(rightChildIdx) &&
                items.get(leftChildIdx).getPriority() > items.get(rightChildIdx).getPriority()) {
                smallerChildIdx = rightChildIdx;
            }
            if (items.get(smallerChildIdx).getPriority() < items.get(currentIndex).getPriority()) {
                swap(smallerChildIdx, currentIndex);
                currentIndex = smallerChildIdx;
                leftChildIdx = currentIndex * 2 + 1;
                isLeftChildValid = isValidIndex(leftChildIdx);
            } else {
                break;
            }
        }
    }

    @Override
    public void changePriority(T item, double priority) {

        if (!contains(item)) {
            throw new NoSuchElementException();
        }

        int index = pNodeMap.get(item);
        items.get(index).setPriority(priority);

        swimUp(index);
        sinkDown(index);
    }

    @Override
    public int size() {

        return size;
    }

    private boolean isValidIndex(int idx) {
        return idx >= 0 && idx < items.size();
    }
}
