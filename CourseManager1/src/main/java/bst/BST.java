package bst;

import java.util.Iterator;
import java.util.Stack;

/**
 * BST Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-10
 * 
 * @param <K> Key
 * @param <V> Value
 */
public class BST<K extends Comparable<? super K>, V> implements Iterable<V> {
    private BSTNode<K, V> root;
    private int nodecount;

    public BST() {
        root = null;
        nodecount = 0;
    }

    public void clear() {
        root = null;
        nodecount = 0;
    }

    public int size() {
        return nodecount;
    }

    /**
     * Inserts a record into the tree.
     * Records can be anything, but they must be comparable
     * 
     * @param k key
     * @param e record
     */
    public void insert(K k, V v) {
        root = inserthelp(root, k, v);
        nodecount++;
    }

    /**
     * Handles insertion of the record elements into the BST recursively
     * 
     * @param rt node
     * @param k key
     * @param e value
     * @return rt
     */
    private BSTNode<K, V> inserthelp(BSTNode<K, V> rt, K k, V v) {
        if (rt == null) {
            return new BSTNode<K, V>(k, v);
        }
        if (rt.key().compareTo(k) > 0) {
            rt.setLeft(inserthelp(rt.left(), k, v));
        }
        else {
            rt.setRight(inserthelp(rt.right(), k, v));
        }
        return rt;
    }

    /**
     * Removes a record from the tree
     * 
     * @param k key to remove
     * @return the record that was removed
     */
    public V remove(K k) {
        V recordRemoved = findhelp(root, k);
        if (recordRemoved != null) {
            root = removehelp(root, k);
            nodecount--;
        }
        return recordRemoved;
    }

    /**
     * Helper function that removes a BST node recursively
     * 
     * @param rt node
     * @param k key
     * @return rt
     */
    private BSTNode<K, V> removehelp(BSTNode<K, V> rt, K k) {
        if (rt == null) {
            return null;
        }
        if (rt.key().compareTo(k) > 0) {
            rt.setLeft(removehelp(rt.left(), k));
        }
        else if (rt.key().compareTo(k) < 0) {
            rt.setRight(removehelp(rt.right(), k));
        }
        else { // Found it
            if (rt.left() == null) {
                return rt.right();
            }
            else if (rt.right() == null) {
                return rt.left();
            }
            else {
                BSTNode<K, V> temp = getmin(rt.right());
                rt.setKey(temp.key());
                rt.setValue(temp.value());
                rt.setRight(deletemin(rt.right()));
            }
        }
        return rt;
    }

    /**
     * Gets the smallest element in a subtree
     * 
     * @param rt node
     * @return the smallest element
     */
    private BSTNode<K, V> getmin(BSTNode<K, V> rt) {
        if (rt.left() == null) {
            return rt;
        }
        return getmin(rt.left());
    }

    /**
     * Delete the minimum valued element in a subtree
     * 
     * @param rt node
     * @return the right tree after the deletion
     */
    private BSTNode<K, V> deletemin(BSTNode<K, V> rt) {
        if (rt.left() == null) {
            return rt.right();
        }
        else {
            rt.setLeft(deletemin(rt.left()));
        }
        return rt;
    }

    /**
     * Return the record with key value k, null if none exists
     * 
     * @param k key to find
     * @return element that's being sought after
     */
    public V find(K k) {
        return findhelp(root, k);
    }

    /**
     * Helper function for the find command that searches recursively for the
     * key value
     * 
     * @param rt node
     * @param k key
     * @return element that was found
     */
    private V findhelp(BSTNode<K, V> rt, K k) {
        if (rt == null) {
            return null;
        }
        if (rt.key().compareTo(k) > 0) {
            return findhelp(rt.left(), k);
        }
        else if (rt.key().compareTo(k) == 0) {
            return rt.value();
        }
        else {
            return findhelp(rt.right(), k);
        }
    }

    /**
     * Prints elements of the BST inorder
     */
    public void inorder() {
        int level = -1;
        inorderhelper(root, level);
    }

    /**
     * Prints elements of the BST recursively
     * 
     * @param rt node
     * @param level current tree level
     */
    private void inorderhelper(BSTNode<K, V> rt, int level) {
        if (rt == null) {
            return;
        }
        level++;
        inorderhelper(rt.left(), level);
        System.out.println(rt.value() + ", at level " + level);
        inorderhelper(rt.right(), level);
    }

    /**
     * Returns a BST iterator
     * 
     * @return iterator for a BST
     */
    @Override
    public Iterator<V> iterator() {
        return new BSTIterator();
    }

    /**
     * BSTIterator Class
     * 
     * @author kyleg997 Kyle Galindo
     * @version 2020-08-10
     */
    private class BSTIterator implements Iterator<V> {
        private Stack<BSTNode<K, V>> stack;

        public BSTIterator() {
        	stack = new Stack<BSTNode<K, V>>();
        	goLeftFrom(root);
        }

        private void goLeftFrom(BSTNode<K, V> rt) {
            while (rt != null) {
                stack.push(rt);
                rt = rt.left();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public V next() {
            BSTNode<K, V> current = stack.peek();
            stack.pop();
            if (current.right() != null) {
                goLeftFrom(current.right());
            }
            return current.value();
        }
    }
}
