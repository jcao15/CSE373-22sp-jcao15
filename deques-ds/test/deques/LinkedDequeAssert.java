package deques;

import org.assertj.core.api.FactoryBasedNavigableIterableAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.ObjectAssertFactory;

// Don't worry about the crazy things happening in the generic types of this class.
public class LinkedDequeAssert<T> extends FactoryBasedNavigableIterableAssert<
        LinkedDequeAssert<T>, LinkedDeque<? extends T>, T, ObjectAssert<T>> {

    public LinkedDequeAssert(LinkedDeque<T> actual) {
        super(actual, LinkedDequeAssert.class, new ObjectAssertFactory<>());
    }

    public LinkedDequeAssert<T> isValid() {
        String message = getErrorMessageIfInvalid(this.actual.front, this.actual.back);
        if (message != null) {
            as("invariant checks").failWithMessage(message);
        }
        return this;
    }

    /**
     * Returns null if the given front and back nodes form a valid linked deque. Otherwise, returns
     * a string describing the error.
     */
    static String getErrorMessageIfInvalid(Node<?> front, Node<?> back) {
        if (front == null) {
            return "Unexpected reference: front should reference a sentinel node but was <null>";
        }
        if (front.prev != null) {
            return String.format("Unexpected reference: front.prev should be <null> but was <%s>",
                describe(front.prev));
        }
        if (back == null) {
            return "Unexpected reference: back should reference a sentinel node but was <null>";
        }
        if (back.next != null) {
            return String.format("Unexpected reference: back.next should be <null> but was <%s>",
                describe(back.next));
        }

        Node<?> curr = front;
        int i = -1;
        String message = checkNode(curr, i++);
        if (message != null) {
            return message;
        }
        curr = curr.next;
        while (curr != back) {
            message = checkNode(curr, i++);
            if (message != null) {
                return message;
            }
            curr = curr.next;
        }

        return null;
    }

    private static String checkNode(Node<?> node, int index) {
        if (node.next == null) {
            return String.format("Unexpected null reference in node at index %d: <%s>",
                index, describe(node));
        }
        if (node.next.prev == node) {
            return null;
        } else if (node.next.prev == null) {
            return String.format("Unexpected null reference in node at index %d: <%s>",
                index+1, describe(node.next));
        } else {
            return String.format("Mismatched references:%n" +
                    "node at index %d: <%s>%n" +
                    "node at index %d: <%s>",
                index, describe(node), index+1, describe(node.next));
        }
    }

    private static <T> String describe(Node<T> node) {
        if (node == null) {
            return "null";
        }
        return String.format("%s{value=<%s>, prev=<%s>, next=<%s>}", node, node.value, node.prev, node.next);
    }
}
