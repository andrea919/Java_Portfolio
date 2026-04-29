package edu.monmouth.CS205L.boggleLeaderboard;

import java.util.ArrayList;	
import java.util.List;

/**
 * Project 5
 *
 * A Splay Tree implementation of SortedMap.
 *
 * A splay tree is a self-adjusting BST: every access (get, put, remove)
 * "splays" the accessed node to the root via a sequence of rotations.
 * This gives amortized O(log n) operations and a useful locality property —
 * recently accessed keys are fast to find again.
 *
 * Real-world use: splay trees are used in the Windows NT virtual-memory
 * manager and in network packet-classification code because recently-seen
 * items tend to be re-accessed soon.
 *
 * ─────────────────────────────────────────────────────────────
 *  YOUR TASK
 * ─────────────────────────────────────────────────────────────
 *  1. Read the provided rotation helpers and make sure you
 *     understand what they do (they are complete — do not modify).
 *  2. Implement  splay()  using the three cases described below.
 *  3. Implement  put(),  get(),  and  remove()  using splay().
 *  4. Implement  minKey()  and  maxKey().
 *  5. Re-use the rangeSearch logic from BSTMap (copy/adapt it).
 * ─────────────────────────────────────────────────────────────
 */
public class SplayMap<K extends Comparable<K>, V> implements SortedMap<K, V> {

    // ── Node ────────────────────────────────────────────────────────────────
    private class Node {
        K    key;
        V    value;
        Node left, right, parent;

        Node(K key, V value) {
            this.key   = key;
            this.value = value;
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────
    private Node root;
    private int  size;
    private long comparisons;

    // ── Stat helpers (do not modify) ─────────────────────────────────────────
    private int compare(K a, K b) {
        comparisons++;
        return a.compareTo(b);
    }

    @Override public long getComparisonCount() { return comparisons; }
    @Override public void resetStats()          { comparisons = 0;   }
    @Override public int  size()                { return size;       }
    @Override public boolean isEmpty()          { return size == 0;  }

    // ── PROVIDED: Rotation helpers ────────────────────────────────────────────
    //
    //  These are complete. Read them carefully — splay() is built from them.
    //
    //  rotateRight(x):          rotateLeft(x):
    //
    //      p                         p
    //      |                         |
    //      x           →             y
    //     / \                       / \
    //    y   C                     A   x
    //   / \                           / \
    //  A   B                         B   C
    //
    //  After rotateRight, y takes x's place in the tree.
    //  After rotateLeft,  y takes x's place in the tree.

    private void rotateRight(Node x) {
        Node y = x.left;
        Node p = x.parent;

        // x adopts y's right child
        x.left = y.right;
        if (y.right != null) y.right.parent = x;

        // y takes x's position
        y.parent = p;
        if (p == null)             root = y;
        else if (p.left == x)      p.left = y;
        else                       p.right = y;

        // x becomes y's right child
        y.right = x;
        x.parent = y;
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
        Node p = x.parent;

        x.right = y.left;
        if (y.left != null) y.left.parent = x;

        y.parent = p;
        if (p == null)             root = y;
        else if (p.left == x)      p.left = y;
        else                       p.right = y;

        y.left = x;
        x.parent = y;
    }

    // ── Part 2A: splay() ─────────────────────────────────────────────────────
    //
    //  Move node x to the root using the THREE splay cases.
    //  Repeat until x.parent == null (x is the root).
    //
    //  Let p = x.parent, g = p.parent (grandparent).
    //
    //  CASE 1 — Zig (p is the root, no grandparent):
    //    If x is a left child  → rotateRight(p)
    //    If x is a right child → rotateLeft(p)
    //
    //  CASE 2 — Zig-Zig (x and p are on the same side):
    //    Both left children  → rotateRight(g), then rotateRight(p)
    //    Both right children → rotateLeft(g),  then rotateLeft(p)
    //
    //  CASE 3 — Zig-Zag (x and p are on opposite sides):
    //    x is right child, p is left child → rotateLeft(p),  then rotateRight(g)
    //    x is left child,  p is right child → rotateRight(p), then rotateLeft(g)
    //
    //  In all cases, after the rotations x has moved two levels up
    //  (or one, in the zig case). Loop until x reaches the root.

    private void splay(Node x) {
    		while(x.parent != null) {
    			Node p = x.parent;
        		Node g = p.parent;
        		// Case 1: Zig
            if (g == null) {
            		if (x == p.left) {
            			rotateRight(p);
                    	} else {
                    		rotateLeft(p);
                    }
            	}
             // Case 2: Zig-Zig
            	else if (x == p.left && p == g.left) {
            		rotateRight(g);
            		rotateRight(p);
            	} else if (x == p.right && p == g.right) {
            		rotateLeft(g);
                rotateLeft(p);
            }
            	// Case 3: Zig-Zag
            else if (x == p.right && p == g.left) {
            		rotateLeft(p);
                rotateRight(g);
            } else if (x == p.left && p == g.right) {
                rotateRight(p);
                rotateLeft(g);
            }      	
    		} // while
    } // splay

    // ── Part 2B: find() helper ────────────────────────────────────────────────
    //
    //  Walk down the tree looking for key.
    //  Return the node if found, or the LAST node visited if not found.
    //  Splay that node to the root before returning.
    //
    //  Why splay even on a miss? It keeps the tree balanced — the last
    //  visited node is close to where key would be inserted.

    private Node findAndSplay(K key) {
    		if(root == null) return null;
    		
        Node current = root;
        Node last = null;
        
        while(current!=null) {
        		last = current;
        		
        		int cmp = compare(key, current.key);
        		
        		if(cmp == 0) {
        			splay(current);
        			return current;
        		} 
        		else if(cmp < 0) current = current.left;
        		else current = current.right;
        }
        splay(last);
        return last;
        
    }

    // ── Part 2C: put ─────────────────────────────────────────────────────────
    //
    //  Algorithm:
    //    1. If empty, just create root and return.
    //    2. Call findAndSplay(key). The root is now the node closest to key.
    //    3. If root.key == key → update value and return.
    //    4. Otherwise, create a new node n for key.
    //       Split the tree at the root:
    //         If key < root.key:
    //           n.right = root
    //           n.left  = root.left
    //           root.left = null
    //           update root.left.parent if not null
    //         Else (key > root.key):
    //           n.left  = root
    //           n.right = root.right
    //           root.right = null
    //           update root.right.parent if not null
    //       Set n as the new root.

    @Override
    public void put(K key, V value) {
        if(root == null) {
        		root = new Node(key, value);
        		size++;
        		return;
        }
        findAndSplay(key);
        
        int cmp = compare(key, root.key);
        if(cmp == 0) {
        		root.value = value;
        		return;
        }
        
        Node n = new Node(key,value);
        if (cmp < 0) {
            n.right = root;
            n.left = root.left;

            if (n.left != null) {
                n.left.parent = n;
            }

            root.left = null;
            root.parent = n;
        } else {
            n.left = root;
            n.right = root.right;

            if (n.right != null) {
                n.right.parent = n;
            }

            root.right = null;
            root.parent = n;
        }

        root = n;
        size++;
    }
        
        
    // ── Part 2D: get ─────────────────────────────────────────────────────────
    //
    //  Algorithm:
    //    1. If empty, return null.
    //    2. Call findAndSplay(key). Root is now the closest node.
    //    3. If root.key == key → return root.value.
    //    4. Otherwise → return null.

    @Override
    public V get(K key) {
        if(isEmpty()) return null;
        findAndSplay(key);
        if(compare(root.key, key) == 0) return root.value;
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // ── Part 2E: remove ──────────────────────────────────────────────────────
    //
    //  Algorithm:
    //    1. Call findAndSplay(key). If root.key != key → do nothing.
    //    2. Splay has placed the target at the root.
    //       Detach root: left subtree L, right subtree R.
    //    3. If L is null → root = R, fix parent pointer.
    //       Else: splay the maximum of L to L's root (it will have no right child),
    //             attach R as L's root's right child,
    //             make L the new root.

    @Override
    public void remove(K key) {
        if(isEmpty()) return;
    		findAndSplay(key);
        if(compare(key, root.key) != 0) return;
        
        Node L = root.left;
        Node R = root.right;
        if (L != null) L.parent = null;
        if (R != null) R.parent = null;
        
        if(L == null) root = R;
        else {
        		root = L;
            Node max = maxNode(root);
            splay(max);
            
            root.right = R;
            if(R != null) R.parent = root; 
        }
        size--;
    }
    
    private Node minNode(Node node) {
        if (node == null) return null;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    private Node maxNode(Node node) {
        if (node == null) return null;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // ── Part 2F: min / max ───────────────────────────────────────────────────

    @Override
    public K minKey() {
        if(isEmpty()) return null;
        return minNode(root).key;
    }

    @Override
    public K maxKey() {
        if (isEmpty()) return null;
        return maxNode(root).key;
    }

    // ── Part 2G: rangeSearch ─────────────────────────────────────────────────
    //  Adapt your solution from BSTMap — the structure is identical.

    private void rangeSearch(Node node, K lo, K hi, List<K> result) {
		if(node == null ) return;
		
		int cmpLo = compare(node.key, lo);
	    int cmpHi = compare(node.key, hi);
	    
	    if (cmpLo > 0) {
	        rangeSearch(node.left, lo, hi, result);
	    }

	    if (cmpLo >= 0 && cmpHi <= 0) {
	        result.add(node.key);
	    }

	    if (cmpHi < 0) {
	        rangeSearch(node.right, lo, hi, result);
	    }
    }


    @Override
    public List<K> rangeSearch(K lo, K hi) {
        List<K> result = new ArrayList<>();
        rangeSearch(root, lo, hi, result);
        return result;
    }
}
