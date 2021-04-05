package deques;

/** A basic doubly-linked node class. */
class Node<T> {
    T value;
    Node<T> prev;
    Node<T> next;

    Node(T value) {
        this(value, null, null);
    }

    Node(T value, Node<T> prev, Node<T> next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}
