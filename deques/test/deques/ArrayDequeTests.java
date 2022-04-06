package deques;

public class ArrayDequeTests extends BaseDequeTests {
    @Override
    protected <T> Deque<T> createDeque() {
        return new ArrayDeque<>();
    }

    @Override
    protected <T> void checkInvariants(Deque<T> deque) {
        // do nothing
    }

    // You can write additional tests here if you only want them to run for ArrayDequeTests and not LinkedDequeTests
}
