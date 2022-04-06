package deques;

import edu.washington.cse373.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * Tests written in this class will be run by both ArrayDequeTests and LinkedDequeTests
 */
public abstract class BaseDequeTests extends BaseTest {
    protected abstract <T> Deque<T> createDeque();
    protected abstract <T> void checkInvariants(Deque<T> deque);

    @Test
    void size_whenEmpty_is0() {
        Deque<String> deque = createDeque();
        assertThat(deque).isEmpty();
        checkInvariants(deque);
    }

    @Test
    void get_at0_whenEmpty_returnsNull() {
        Deque<String> deque = createDeque();
        String output = deque.get(0);
        assertThat(output).isNull();
        checkInvariants(deque);
    }

    @Test
    void get_atNegative_whenEmpty_returnsNull() {
        Deque<String> deque = createDeque();
        String output = deque.get(-1);
        assertThat(output).isNull();
        checkInvariants(deque);
    }

    @Test
    void get_atPositive_whenEmpty_returnsNull() {
        Deque<String> deque = createDeque();
        String output = deque.get(1);
        assertThat(output).isNull();
        checkInvariants(deque);
    }

    @Test
    void usingMultipleDequesSimultaneously_doesNotCauseInterference() {
        Deque<Integer> d1 = createDeque();
        Deque<Integer> d2 = createDeque();
        d1.addFirst(1);
        d2.addFirst(2);
        d1.addFirst(3);

        assertThat(d1).hasSize(2);
        assertThat(d2).hasSize(1);
    }

    /*
    These following tests are not symmetrical.
    Tip: the grader will versions of these tests that add/remove from the other sides as well.
     */
    @Test
    void addTo_empty_doesNotThrow() {
        Deque<String> deque = createDeque();
        deque.addFirst("s");
        checkInvariants(deque);
    }

    @Test
    void size_with1Item_is1() {
        Deque<String> deque = createDeque();
        deque.addFirst("s");
        assertThat(deque).hasSize(1);
        checkInvariants(deque);
    }

    @Test
    void get_at0__with1Item_returnsItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("s");
        String output = deque.get(0);
        assertThat(output).isEqualTo("s");
        checkInvariants(deque);
    }

    @Test
    void remove_afterAdd1Item_toSameSide_returnsItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("s");
        String output = deque.removeFirst();
        assertThat(output).isEqualTo("s");
        checkInvariants(deque);
    }

    @Test
    void remove_afterAdd1Item_toOppositeSide_returnsItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("s");
        String output = deque.removeLast();
        assertThat(output).isEqualTo("s");
        checkInvariants(deque);
    }

    @Test
    void remove_whenEmpty_returnsNull() {
    Deque<String> deque = createDeque();
        String output = deque.removeFirst();
        assertThat(output).isNull();
        checkInvariants(deque);
    }

    @Test
    void getEach_afterAddToOppositeEnds_returnsCorrectItems() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        // this assertion calls `get` for each index
        assertThat(deque).containsExactly("a", "b");
        checkInvariants(deque);
    }

    @Test
    void size_afterAddToOppositeEnds_is2() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        assertThat(deque).hasSize(2);
        checkInvariants(deque);
    }

    @Test
    void remove_afterAddToOppositeEnds_returnsCorrectItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        String output = deque.removeFirst();
        assertThat(output).isEqualTo("a");
        checkInvariants(deque);
    }

    @Test
    void size_afterRemove_afterAddToOppositeEnds_is1() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        deque.removeFirst();
        assertThat(deque).hasSize(1);
        checkInvariants(deque);
    }

    @Test
    void remove_afterRemove_afterAddToOppositeEnds_returnsCorrectItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        deque.removeFirst();
        String output = deque.removeLast();
        assertThat(output).isEqualTo("b");
        checkInvariants(deque);
    }

    @Test
    void getEach_afterAddManyToSameSide_returnsCorrectItems() {
        Deque<Integer> deque = createDeque();
        IntStream.range(0, 20).forEach(deque::addLast);
        // this assertion calls `get` for each index
        assertThat(deque).containsExactly(IntStream.range(0, 20).boxed().toArray(Integer[]::new));
        checkInvariants(deque);
    }

    /* These tests check whether you can create Deques of different parameterized types. */
    @Test
    void remove_afterAddString_returnsCorrectString() {
        Deque<String> deque = createDeque();
        deque.addFirst("string");
        String s = deque.removeFirst();
        assertThat(s).isEqualTo("string");
        checkInvariants(deque);
    }
    @Test
    void remove_afterAddDouble_returnsCorrectDouble() {
        Deque<Double> deque = createDeque();
        deque.addFirst(3.1415);
        double d = deque.removeFirst();
        assertThat(d).isEqualTo(3.1415);
        checkInvariants(deque);
    }

    @Test
    void remove_afterAddBoolean_returnsCorrectBoolean() {
        Deque<Boolean> deque = createDeque();
        deque.addFirst(true);
        boolean b = deque.removeFirst();
        assertThat(b).isTrue();
        checkInvariants(deque);
    }

    /*
    This test demonstrates why we like unit tests that test a single unit at a time:
    calling multiple methods in the same test makes issues very hard to debug.
    */
    @Test
    void confusingTest() {
        Deque<Integer> deque = createDeque();
        deque.addFirst(0);
        assertThat(deque.get(0)).isEqualTo(0);

        deque.addLast(1);
        assertThat(deque.get(1)).isEqualTo(1);

        deque.addFirst(-1);
        deque.addLast(2);
        assertThat(deque.get(3)).isEqualTo(2);

        deque.addLast(3);
        deque.addLast(4);

        // Test that removing and adding back is okay
        assertThat(deque.removeFirst()).isEqualTo(-1);
        deque.addFirst(-1);
        assertThat(deque.get(0)).isEqualTo(-1);

        deque.addLast(5);
        deque.addFirst(-2);
        deque.addFirst(-3);

        // Test a tricky sequence of removes
        assertThat(deque.removeFirst()).isEqualTo(-3);
        assertThat(deque.removeLast()).isEqualTo(5);
        assertThat(deque.removeLast()).isEqualTo(4);
        assertThat(deque.removeLast()).isEqualTo(3);
        assertThat(deque.removeLast()).isEqualTo(2);

        // TODO ArrayDeque fails here; write better tests to help you find and fix the bug
        int actual = deque.removeLast();
        assertThat(actual).isEqualTo(1);
        checkInvariants(deque);
    }
}
