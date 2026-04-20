package edu.monmouth.CS205L.boggleLeaderboard;

/**
 * Project 5
 * DON'T CHANGE ANYTHING IN THIS FILE IT'S JUST THE INTERFACE!!!
 *
 * A map whose keys are kept in sorted order, supporting efficient
 * lookup, insertion, deletion, and range queries.
 *
 * All three tree implementations (BST, Splay, Red-Black) will
 * implement this interface so they can be swapped transparently
 * into the Leaderboard application.
 *
 * @param <K> key type — must be Comparable
 * @param <V> value type
 */
public interface SortedMap<K extends Comparable<K>, V> {

    /**
     * Inserts or updates a key-value pair.
     * If the key already exists, its value is overwritten.
     *
     * @param key   the key (must not be null)
     * @param value the value to associate with the key
     */
    void put(K key, V value);

    /**
     * Returns the value associated with the given key,
     * or null if the key is not present.
     *
     * @param key the key to search for
     * @return the associated value, or null
     */
    V get(K key);

    /**
     * Removes the key-value pair with the given key.
     * Does nothing if the key is not present.
     *
     * @param key the key to remove
     */
    void remove(K key);

    /**
     * Returns true if the map contains the given key.
     *
     * @param key the key to search for
     * @return true if present, false otherwise
     */
    boolean containsKey(K key);

    /**
     * Returns the number of key-value pairs in the map.
     *
     * @return size of the map
     */
    int size();

    /**
     * Returns true if the map contains no entries.
     *
     * @return true if empty
     */
    boolean isEmpty();

    /**
     * Returns the minimum key in the map, or null if empty.
     *
     * @return minimum key
     */
    K minKey();

    /**
     * Returns the maximum key in the map, or null if empty.
     *
     * @return maximum key
     */
    K maxKey();

    /**
     * Returns a list of all keys in the map that fall within
     * the range [lo, hi] (inclusive on both ends), in ascending order.
     *
     * This operation is the main reason we use a sorted tree instead
     * of a hash table — a hash table cannot answer range queries
     * efficiently.
     *
     * @param lo the lower bound (inclusive)
     * @param hi the upper bound (inclusive)
     * @return list of keys in [lo, hi], sorted ascending
     */
    java.util.List<K> rangeSearch(K lo, K hi);

    /**
     * Returns the number of comparisons made since the last call
     * to resetStats(), for benchmarking purposes.
     *
     * @return comparison count
     */
    long getComparisonCount();

    /**
     * Resets the comparison counter to zero.
     */
    void resetStats();
}
