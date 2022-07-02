package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        size = 0;
        // TODO: replace this with your code
    }

    public void addFirst(T item) {
        size += 1;
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void addLast(T item) {
        size += 1;
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int size() {
        return size;
    }
}
