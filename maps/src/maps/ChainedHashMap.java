package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {

    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 10;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;
    Iterator<Map.Entry<K, V>> itr;

    // You're encouraged to add extra fields (and helper methods) though!
    int numOfElements;
    double loadFactor;
    int chainNum;
    int chainCap;

    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {

        numOfElements = 0;
        loadFactor = resizingLoadFactorThreshold;
        chainNum = initialChainCount;
        chainCap = chainInitialCapacity;

        this.chains = this.createArrayOfChains(chainNum);
        for (int i = 0; i < chainNum; i++) {
            chains[i] = this.createChain(chainCap);
        }
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    //helper function to retrieve chain's index
    private int getChainIdx(Object key) {
        //If your ChainedHashMap receives a null key, use a hashcode of 0 for that key.
        if (Objects.equals(null, key)) {
            return 0;
        }
        int hashCode = key.hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return hashCode % chainNum;
    }

    @Override
    public V get(Object key) {
        int chainIndex = getChainIdx(key);
        //point to the corresponding ArrayMap(chain), then invoke ArrayMap.containsKey() function
        if (chains[chainIndex].containsKey(key)) {
            return chains[chainIndex].get(key);
        } else {
            return null;
        }
    }

    @Override
    public V put(K key, V value) {
        numOfElements++;
        int hashCode = getChainIdx(key);

        //check the current load factor lambda to determine if we need a resize
        if (numOfElements / chainNum >= loadFactor) {
            resize(chainNum * 2);
        }
        //Check if there is duplicate key, if so, replace the value with new put value, and the numOfElements stay same
        if (chains[hashCode].containsKey(key)) {
            numOfElements--;
        }
        return chains[hashCode].put(key, value);
    }

    //helper function to create a new chain list after resize
    private void resize(int newChainNum) {
        AbstractIterableMap<K, V>[] newChains = createArrayOfChains(newChainNum);

        for (int i = 0; i < newChainNum; i++) {
            newChains[i] = this.createChain(chainCap);
        }

        int oldChainNum = chainNum;
        chainNum *= 2;
        //re-distribute the element's positions by taking the mod with the new chain array length
        for (int i = 0; i < oldChainNum; i++) {
            //Check if it is an empty chain
            if (!Objects.equals(null, chains[i])) {
                //create an ArrayMap iterator to loop through the chain
                Iterator<Map.Entry<K, V>> itrResize = chains[i].iterator();
                while (itrResize.hasNext()) {
                    Map.Entry<K, V> chainBucket = itrResize.next();
                    int hashCode = getChainIdx(chainBucket.getKey());
                    newChains[hashCode].put(chainBucket.getKey(), chainBucket.getValue());
                }
            }
        }

        chains = newChains;
    }
    @Override
    public V remove(Object key) {

        int hashCode = getChainIdx(key);
        if (chains[hashCode].containsKey(key)) {
            numOfElements--;
            return chains[hashCode].remove(key);
        } else {
            return null;
        }
    }

    @Override
    public void clear() {
        for (AbstractIterableMap<K, V> chain : chains) {
            chain.clear();
        }
        numOfElements = 0;
        chainNum = DEFAULT_INITIAL_CHAIN_COUNT;
    }

    @Override
    public boolean containsKey(Object key) {

        int hashCode = getChainIdx(key);
        return chains[hashCode].containsKey(key);
    }

    @Override
    public int size() {
        return numOfElements;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains, this.itr);
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        private Iterator<Map.Entry<K, V>> itr;
        private int idx;

        // You may add more fields and constructor parameters
        private void moveNext() {
            idx++;
            //check if the chain bucket is null, if so, move to the next chain bucket
            while (idx < chains.length && chains[idx] == null && chains[idx].size() < 1) {
                idx++;
            }
            if (idx < chains.length) {
                this.itr = chains[idx].iterator();
            } else {
                this.itr = null;
            }

        }

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains, Iterator<Map.Entry<K, V>> itr) {
            this.chains = chains;
            idx = -1;
            //initiate the chain(arrayMap) iterator
            moveNext();

        }

        @Override
        public boolean hasNext() {
            while (idx < chains.length) {
                if (itr.hasNext()) {
                    return true;
                } else {
                    moveNext();
                }
            }
            return false;
        }

        @Override
        public Map.Entry<K, V> next() {
            while (hasNext()) {
                if (itr.hasNext()) {
                    return itr.next();
                } else {
                    moveNext();
                }
            }
            throw new NoSuchElementException();
        }
    }
}
