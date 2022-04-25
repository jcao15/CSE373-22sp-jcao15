package maps;


import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;
    private int elementSize;

    // You may add extra fields or helper methods though!

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        elementSize = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {

        for (int i = 0; i < elementSize; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                return entries[i].getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {

        int idx = 0;

        if (elementSize == entries.length) {
            SimpleEntry<K, V>[] newEntries = createArrayOfEntries(entries.length * 2);
            System.arraycopy(entries, 0, newEntries, 0, entries.length);
            entries = newEntries;
        }

        while (idx < elementSize) {
            if (Objects.equals(entries[idx].getKey(), key)) {
                V prevVal = entries[idx].getValue();
                entries[idx].setValue(value);
                return prevVal;
            } else {
                idx++;
            }
        }
        entries[idx] = new SimpleEntry<>(key, value);
        elementSize++;
        return null;
    }

    @Override
    public V remove(Object key) {

        V rmVal;
        for (int i = 0; i < elementSize; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                rmVal = entries[i].getValue();
                //if remove element is not the last one
                if (i != elementSize - 1) {
                    entries[i] = entries[elementSize - 1];
                }
                entries[elementSize - 1] = null;
                elementSize--;
                return rmVal;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        for (int i = 0; i < elementSize; i++) {
            entries[i] = null;
        }
        elementSize = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < elementSize; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return elementSize;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries);
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        public int idx;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
            idx = 0;
        }

        @Override
        public boolean hasNext() {
            return (idx != entries.length) && (entries[idx] != null);
        }

        @Override
        public Map.Entry<K, V> next() {
            if (hasNext()) {
                return entries[idx++];
            }
            throw new NoSuchElementException();
        }
    }
}
