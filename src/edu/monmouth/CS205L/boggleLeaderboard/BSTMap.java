package edu.monmouth.CS205L.boggleLeaderboard;
import java.util.ArrayList;
import java.util.List;

/**
 * Project 5
 *
 * An unbalanced Binary Search Tree implementation of SortedMap.
 *
 * ─────────────────────────────────────────────────────────────
 *  YOUR TASK
 * ─────────────────────────────────────────────────────────────
 *  Implement the methods marked  TODO .
 *  Do NOT modify the Node class or any method signatures.
 *  You may add private helper methods freely.
 * ─────────────────────────────────────────────────────────────
 */
public class BSTMap<K extends Comparable<K>, V> implements SortedMap<K, V> {

    // ── Node ────────────────────────────────────────────────────────────────
    private class Node {
        K key;
        V value;
        Node left, right;

        Node(K key, V value) {
            this.key   = key;
            this.value = value;
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────
    private Node root;
    private int  size;
    private long comparisons;   // incremented by compare()

    // ── Stat helpers (do not modify) ─────────────────────────────────────────
    private int compare(K a, K b) {
        comparisons++;
        return a.compareTo(b);
    }

    @Override public long getComparisonCount() { return comparisons; }
    @Override public void resetStats()          { comparisons = 0;   }
    @Override public int  size()                { return size;       }
    @Override public boolean isEmpty()          { return size == 0;  }

    // ── Part 1A: put ─────────────────────────────────────────────────────────

    /**
     * Inserts or updates (key → value) in the BST.
     */
    @Override
    public void put(K key, V value) {
        // TODO
    		if(isEmpty()) {
    			root = new Node(key, value);
    		} 
    		Node node = new Node(key, value);
    		Node nextNode = root;
    		
    		while(!(nextNode.right.equals(null) && nextNode.left.equals(null))) {
    			if(nextNode.key.compareTo(node.key) > 0) {nextNode = nextNode.right;}
    			else if(nextNode.key.compareTo(node.key) == 0) { nextNode.value = node.value;}
    			else {nextNode = nextNode.left;}
    		}
    		
    		// when right and left are null means that it is a leaf
    		if(node.key.compareTo(nextNode.key) > 0) { nextNode.right = node;}
    		else {nextNode.left = node; }
    
        throw new UnsupportedOperationException("put() not yet implemented");
    }
    

    // ── Part 1B: get ─────────────────────────────────────────────────────────

    /**
     * Get a value mapped to a key.
     */
    @Override
    public V get(K key) {
        // TODO
        throw new UnsupportedOperationException("get() not yet implemented");
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // ── Part 1C: remove ──────────────────────────────────────────────────────

    /**
     * Removes the node with the given key (Hibbard deletion).
     *
     * Recall the three cases:
     *   0 children  → just remove the node
     *   1 child     → replace node with its only child
     *   2 children  → replace with in-order successor (min of right subtree),
     *                 then delete that successor from the right subtree
     *
     * Hint: write a private recursive helper
     *   Node remove(Node node, K key)
     * that returns the (possibly changed) subtree root.
     *
     * Also write: Node minNode(Node node) — returns the leftmost node.
     */
    @Override
    public void remove(K key) {
        // TODO
        throw new UnsupportedOperationException("remove() not yet implemented");
    }

    // ── Part 1D: min / max ───────────────────────────────────────────────────

    /**
     * Returns the minimum key (leftmost node), or null if empty.
     */
    @Override
    public K minKey() {
        // TODO
        throw new UnsupportedOperationException("minKey() not yet implemented");
    }

    /**
     * Returns the maximum key (rightmost node), or null if empty.
     */
    @Override
    public K maxKey() {
        // TODO
        throw new UnsupportedOperationException("maxKey() not yet implemented");
    }

    // ── Part 1E: rangeSearch ─────────────────────────────────────────────────

    /**
     * Returns all keys k such that lo <= k <= hi, in ascending order.
     *
     * A naive approach visits every node in the tree — O(n).
     * A smarter recursive approach PRUNES subtrees that cannot
     * possibly contain keys in [lo, hi]:
     *
     *   • If node.key < lo → skip the entire left subtree
     *   • If node.key > hi → skip the entire right subtree
     *   • Otherwise → recurse left, maybe collect this node, recurse right
     *
     * Implement the smarter version.
     *
     * @param result the list to append matching keys to (in order)
     */
    private void rangeSearch(Node node, K lo, K hi, List<K> result) {
        // TODO
    }

    @Override
    public List<K> rangeSearch(K lo, K hi) {
        List<K> result = new ArrayList<>();
        rangeSearch(root, lo, hi, result);
        return result;
    }

    // ── Provided: inorder traversal for debugging ─────────────────────────────

    /**
     * Returns all keys in ascending order (in-order traversal).
     * Useful for verifying your BST structure.
     */
    public List<K> inorder() {
        List<K> keys = new ArrayList<>();
        inorder(root, keys);
        return keys;
    }

    private void inorder(Node node, List<K> keys) {
        if (node == null) return;
        inorder(node.left, keys);
        keys.add(node.key);
        inorder(node.right, keys);
    }
}
