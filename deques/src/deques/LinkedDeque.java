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
        front = new Node<>(null, null, null); //front references the front sentinel node
        back = new Node<>(null, null, null); //back references the back sentinel node
        front.next = back;
        back.prev = front;
    }

    public void addFirst(T item) {
        Node<T> addNode = new Node<>(item);
        if (size == 0) {
            front.next = addNode;
            addNode.prev = front;

            back.prev = addNode;
            addNode.next = back;
        } else {
            front.next.prev = addNode;
            addNode.next = front.next;

            front.next = addNode;
            addNode.prev = front;
        }
        size += 1;
    }

    public void addLast(T item) {
        Node<T> addNode = new Node<>(item);
        if (size == 0) {
            front.next = addNode;
            addNode.prev = front;

            back.prev = addNode;
            addNode.next = back;
        } else {
            back.prev.next = addNode;
            addNode.prev = back.prev;

            addNode.next = back;
            back.prev = addNode;
        }
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node<T> rmNode = front.next;
        front.next = rmNode.next;
        rmNode.next.prev = front;
        size -= 1;

        return rmNode.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node<T> rmNode = back.prev;
        back.prev = rmNode.prev;
        rmNode.prev.next = back;
        size -= 1;

        return rmNode.value;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> tempNode = front.next;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        return tempNode.value;
    }

    public int size() {
        return size;
    }
}
