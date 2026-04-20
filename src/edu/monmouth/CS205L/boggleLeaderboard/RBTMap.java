package edu.monmouth.CS205L.boggleLeaderboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Project 5
 *
 * A Left-Leaning Red-Black Tree (LLRB) implementation of SortedMap.
 *
 * Red-black trees guarantee O(log n) worst-case time for all operations
 * by enforcing a set of color invariants after every insert and delete.
 * Java's own java.util.TreeMap is a red-black tree.
 *
 * We use the "Left-Leaning" variant which is simpler.
 * Red links always lean left, which reduces the number of cases to handle.
 *
 * INVARIANTS:
 *   1. Every node is either red or black.
 *   2. Red links lean left (a node's left child may be red, never its right).
 *   3. No node has two consecutive red links.
 *   4. The tree has "perfect black balance" — every path from root to a null
 *      link has the same number of black links.
 *
 * ─────────────────────────────────────────────────────────────
 *  YOUR TASK
 * ─────────────────────────────────────────────────────────────
 *  The insert logic and fix-up are provided.
 *  You must implement the THREE structural helpers used by insert:
 *    • rotateLeft(Node h)   — TODO, Part 3A
 *    • rotateRight(Node h)  — TODO, Part 3B
 *    • flipColors(Node h)   — TODO, Part 3C  (no rotations needed)
 *
 *  Then answer the analysis questions at the bottom of this file.
 * ─────────────────────────────────────────────────────────────
 */
public class RBTMap<K extends Comparable<K>, V> implements SortedMap<K, V> {

    // ── Node ────────────────────────────────────────────────────────────────
    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private class Node {
        K       key;
        V       value;
        Node    left, right;
        boolean color;      // color of the link FROM parent TO this node

        Node(K key, V value) {
            this.key   = key;
            this.value = value;
            this.color = RED;   // new nodes are always inserted as red
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────
    private Node root;
    private int  size;
    private long comparisons;

    // ── Stat helpers (do not modify) ─────────────────────────────────────────
    private int compare(K a, K b) { comparisons++; return a.compareTo(b); }

    @Override public long getComparisonCount() { return comparisons; }
    @Override public void resetStats()          { comparisons = 0;   }
    @Override public int  size()                { return size;       }
    @Override public boolean isEmpty()          { return size == 0;  }

    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    // ── Part 3A: rotateLeft ──────────────────────────────────────────────────
    //
    //  Used when a right-leaning red link needs to be corrected.
    //
    //  Before:           After:
    //    h                 x
    //   / \RED            RED/ \
    //  A   x            h     C
    //     / \          / \
    //    B   C        A   B
    //
    //  Steps:
    //    1. x = h.right
    //    2. h.right = x.left
    //    3. x.left = h
    //    4. x.color = h.color   (x inherits h's color in the parent's view)
    //    5. h.color = RED        (h is now x's left child, linked by a red link)
    //    6. Return x            (new subtree root)

    private Node rotateLeft(Node h) {
        // TODO — Part 3A
        throw new UnsupportedOperationException("rotateLeft() not yet implemented");
    }

    // ── Part 3B: rotateRight ─────────────────────────────────────────────────
    //
    //  The mirror image of rotateLeft.
    //  Used when two consecutive red links lean left.
    //
    //  Before:           After:
    //      h               x
    //    RED/ \           / \RED
    //    x   C          A    h
    //   / \                 / \
    //  A   B               B   C
    //
    //  Steps are the mirror of rotateLeft. Work them out yourself!

    private Node rotateRight(Node h) {
        // TODO — Part 3B
        throw new UnsupportedOperationException("rotateRight() not yet implemented");
    }

    // ── Part 3C: flipColors ──────────────────────────────────────────────────
    //
    //  Used when both children of h are red (a 4-node in 2-3-4 tree terms).
    //  Splits the 4-node by flipping all three nodes' colors:
    //    h.color  → RED   (push h "up" toward its parent)
    //    h.left.color  → BLACK
    //    h.right.color → BLACK
    //
    //  No structural changes (no pointer changes) — just flip the colors.

    private void flipColors(Node h) {
        // TODO — Part 3C
        throw new UnsupportedOperationException("flipColors() not yet implemented");
    }

    // ── PROVIDED: put (complete — do not modify) ─────────────────────────────

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
        root.color = BLACK;   // root is always black
    }

    private Node put(Node h, K key, V value) {
        if (h == null) {
            size++;
            return new Node(key, value);
        }

        int cmp = compare(key, h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, value);
        else if (cmp > 0) h.right = put(h.right, key, value);
        else              h.value = value;   // update existing key

        // ── Fix-up: restore LLRB invariants bottom-up ──────────────────────
        //
        //   Right-leaning red link?  → rotate left
        if (isRed(h.right) && !isRed(h.left))       h = rotateLeft(h);
        //   Two consecutive left red links?          → rotate right
        if (isRed(h.left)  && isRed(h.left.left))   h = rotateRight(h);
        //   Both children red?                       → flip colors
        if (isRed(h.left)  && isRed(h.right))        flipColors(h);

        return h;
    }

    // ── PROVIDED: get, containsKey ───────────────────────────────────────────

    @Override
    public V get(K key) {
        Node x = root;
        while (x != null) {
            int cmp = compare(key, x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.value;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) { return get(key) != null; }

    // ── PROVIDED: remove ─────────────────────────────────────────────────────
    //  (Deletion in LLRB trees is beyond scope; provided for completeness.)

    @Override
    public void remove(K key) {
        if (!containsKey(key)) return;
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, key);
        size--;
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, K key) {
        if (compare(key, h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) h = rotateRight(h);
            if (compare(key, h.key) == 0 && h.right == null) return null;
            if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h);
            if (compare(key, h.key) == 0) {
                Node x = min(h.right);
                h.key   = x.key;
                h.value = x.value;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) { h.right = rotateRight(h.right); h = rotateLeft(h); flipColors(h); }
        return h;
    }

    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) { h = rotateRight(h); flipColors(h); }
        return h;
    }

    private Node balance(Node h) {
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  && isRed(h.left.left))  h = rotateRight(h);
        if (isRed(h.left)  && isRed(h.right))       flipColors(h);
        return h;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) return null;
        if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node min(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    // ── PROVIDED: minKey, maxKey ─────────────────────────────────────────────

    @Override
    public K minKey() {
        if (isEmpty()) return null;
        Node x = root;
        while (x.left != null) x = x.left;
        return x.key;
    }

    @Override
    public K maxKey() {
        if (isEmpty()) return null;
        Node x = root;
        while (x.right != null) x = x.right;
        return x.key;
    }

    // ── PROVIDED: rangeSearch ────────────────────────────────────────────────

    @Override
    public List<K> rangeSearch(K lo, K hi) {
        List<K> result = new ArrayList<>();
        rangeSearch(root, lo, hi, result);
        return result;
    }

    private void rangeSearch(Node node, K lo, K hi, List<K> result) {
        if (node == null) return;
        int cmpLo = compare(lo, node.key);
        int cmpHi = compare(hi, node.key);
        if (cmpLo < 0) rangeSearch(node.left,  lo, hi, result);
        if (cmpLo <= 0 && cmpHi >= 0) result.add(node.key);
        if (cmpHi > 0) rangeSearch(node.right, lo, hi, result);
    }
}
