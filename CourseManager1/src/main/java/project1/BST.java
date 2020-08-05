package project1;

import java.util.Iterator;
import java.util.Stack;

/**
 * BST Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-07-29
 * 
 * @param <K> Key
 * @param <E> Value
 */
public class BST<K extends Comparable<? super K>, E> implements Iterable<E> {
    private BSTNode<K, E> root; // Root of the BST
    private int nodecount; // Number of nodes in the BST

    /**
     * BST default constructor
     */
    public BST() {
        root = null;
        nodecount = 0;
    }

    /**
     * Clears the BST
     */
    public void clear() {
        root = null;
        nodecount = 0;
    }

    /**
     * Returns the size of the BST
     * 
     * @return number of records in the dictionary
     */
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
    public void insert(K k, E e) {
        root = inserthelp(root, k, e);
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
    private BSTNode<K, E> inserthelp(BSTNode<K, E> rt, K k, E e) {
        if (rt == null) {
            return new BSTNode<K, E>(k, e);
        }
        if (rt.key().compareTo(k) > 0) {
            rt.setLeft(inserthelp(rt.left(), k, e));
        }
        else {
            rt.setRight(inserthelp(rt.right(), k, e));
        }
        return rt;
    }

    /**
     * Removes a record from the tree
     * 
     * @param k key to remove
     * @return the record that was removed
     */
    public E remove(K k) {
        E recordRemoved = findhelp(root, k);
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
    private BSTNode<K, E> removehelp(BSTNode<K, E> rt, K k) {
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
                BSTNode<K, E> temp = getmin(rt.right());
                rt.setKey(temp.key());
                rt.setElement(temp.element());
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
    private BSTNode<K, E> getmin(BSTNode<K, E> rt) {
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
    private BSTNode<K, E> deletemin(BSTNode<K, E> rt) {
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
    public E find(K k) {
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
    private E findhelp(BSTNode<K, E> rt, K k) {
        if (rt == null) {
            return null;
        }
        if (rt.key().compareTo(k) > 0) {
            return findhelp(rt.left(), k);
        }
        else if (rt.key().compareTo(k) == 0) {
            return rt.element();
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
    private void inorderhelper(BSTNode<K, E> rt, int level) {
        if (rt == null) {
            return;
        }
        level++;
        inorderhelper(rt.left(), level);
        System.out.println(rt.element() + ", at level " + level);
        inorderhelper(rt.right(), level);
    }

    /**
     * Returns a BST iterator
     * 
     * @return iterator for a BST
     */
    @Override
    public Iterator<E> iterator() {
        return new BSTIterator();
    }

    /**
     * BSTIterator Class
     * 
     * @author kyleg997 Kyle Galindo
     * @version 2020-07-29
     */
    private class BSTIterator implements Iterator<E> {
        private Stack<BSTNode<K, E>> stack;

        public BSTIterator() {
        	stack = new Stack<BSTNode<K, E>>();
        	goLeftFrom(root);
        }

        private void goLeftFrom(BSTNode<K, E> rt) {
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
        public E next() {
            BSTNode<K, E> current = stack.peek();
            stack.pop();
            if (current.right() != null) {
                goLeftFrom(current.right());
            }
            return current.element();
        }
    }
}
