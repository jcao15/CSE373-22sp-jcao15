package datastructures;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/*
 * WARNING: DO NOT MODIFY THIS FILE - we will be using this class as provided below when
 * we score your assignment (using the provided tests).
 *
 * If you modify it, you risk breaking our stuff in a not-fun way.
 */

/**
 * This class represents a linked list of `int` data. Each piece of data is represented as a
 * `ListNode` object that stores some `int` data (the `data` field), as well as the `ListNode`
 * object that comes after in the list order (the `next` field).
 *
 * Note that this data structure is currently not very useful since it lacks methods for easily
 * adding or accessing its data, and it certainly does not provide the functionality of the
 * list ADT, despite its name.
 *
 * We'll be implementing a linked-node-based data structure for a list-like ADT in the next
 * assignment, though, so stay tuned!
 */

public class LinkedIntList implements Iterable<Integer> {

    public ListNode front;

    public LinkedIntList() { }

    /** Creates a new `LinkedIntList` storing the given array of values. */
    public LinkedIntList(int... array) {
        // this would be inefficient:
        // for (int n : array) {
        //    this.add(n);
        // }

        if (array.length != 0) {
            front = new ListNode(array[0]);
            ListNode temp = front;
            for (int i = 1; i < array.length; i++) {
                temp.next = new ListNode(array[i]);
                temp = temp.next;
            }
        }
    }

    /** Returns a new `LinkedIntList` with the given `ListNode` in front. */
    public LinkedIntList(ListNode front) {
        this.front = front;
    }

    public static class ListNode {
        public final int data;
        public ListNode next;

        public ListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    /** Returns a comma-separated string representation of this list. */
    @Override
    public String toString() {
        if (front == null) {
            return "[]";
        } else {
            Set<ListNode> seen = new HashSet<>();
            String result = "[<" + front.data + ">";
            seen.add(front);
            ListNode current = front.next;
            while (current != null) {
                if (!seen.contains(current)) {
                    result += ", <" + current.data + ">";
                    seen.add(current);
                    current = current.next;
                } else {
                    return result + ", <" + current.data + "> CYCLE DETECTED HERE ...";
                }
            }
            result += "]";
            return result;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new LinkedIntListIterator(this.front);
    }

    private static final class LinkedIntListIterator implements Iterator<Integer> {
        private ListNode next;
        private Set<ListNode> seen;

        private LinkedIntListIterator(ListNode start) {
            this.next = start;
            this.seen = new HashSet<>();
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            ListNode temp = next;
            if (seen.contains(temp)) {
                throw new IllegalStateException("Cycle detected.");
            }
            next = next.next;
            seen.add(temp);
            return temp.data;
        }
    }
}
