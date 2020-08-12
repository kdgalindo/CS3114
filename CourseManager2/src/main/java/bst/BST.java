package bst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * BST Class
 * 
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-09-19
 * 
 * @param <K> Key
 * @param <E> Value
 */
public class BST<K extends Comparable<? super K>, E extends Comparable<? super E>>
    implements Iterable<E> {
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
     * Inserts value into the BST
     * 
     * @param k key
     * @param e value
     */
    public void insert(K k, E e) {
        root = inserthelp(root, k, e);
        nodecount++;
    }

    /**
     * Handles insertion of the value into the BST
     * 
     * @param rt root
     * @param k key
     * @param e value
     * @return rt node
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
     * Removes value from the BST
     * matching key
     * 
     * @param k key
     * @return temp value
     */
    public E remove(K k) {
        E temp = find(k);
        if (temp != null) {
            root = removehelp(root, k);
            nodecount--;
        }
        return temp;
    }

    /**
     * Handles removal of the value
     * matching key
     * from the BST
     * 
     * @param rt root
     * @param k key
     * @return rt root
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
        else {
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
     * Removes value from the BST
     * matching key and value
     * 
     * @param k key
     * @param e value
     * @return temp value
     */
    public E remove(K k, E e) { // WATCH
        E temp = find(k, e);
        if (temp != null) {
            root = removehelp(root, k, e);
            nodecount--;
        }
        return temp;
    }

    /**
     * Handles removal of the value
     * matching key and value
     * from the BST
     * 
     * @param rt root
     * @param k key
     * @param e value
     * @return rt root
     */
    private BSTNode<K, E> removehelp(BSTNode<K, E> rt, K k, E e) { // WATCH
        if (rt == null) {
            return null;
        }
        if (rt.key().compareTo(k) > 0) {
            rt.setLeft(removehelp(rt.left(), k, e));
        }
        else if (rt.key().compareTo(k) < 0) {
            rt.setRight(removehelp(rt.right(), k, e));
        }
        else {
            if (rt.element().compareTo(e) == 0) {
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
            else {
                rt.setRight(removehelp(rt.right(), k, e));
            }
        }
        return rt;
    }

    /**
     * Gets smallest value in subtree
     * 
     * @param rt node
     * @return rt smallest value
     */
    private BSTNode<K, E> getmin(BSTNode<K, E> rt) {
        if (rt.left() == null) {
            return rt;
        }
        return getmin(rt.left());
    }

    /**
     * Delete the minimum value in subtree
     * 
     * @param rt node
     * @return rt subtree after removal
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
     * Finds value in the BST
     * matching key
     * 
     * @param k key
     * @return temp value
     */
    public E find(K k) {
        BSTNode<K, E> temp = findhelp(root, k);
        if (temp == null) {
            return null;
        }
        return temp.element();
    }

    /**
     * Handles finding value
     * matching key
     * in the BST
     * 
     * @param rt node
     * @param k key
     * @return element that was found
     */
    private BSTNode<K, E> findhelp(BSTNode<K, E> rt, K k) {
        if (rt == null) {
            return null;
        }
        if (rt.key().compareTo(k) > 0) {
            return findhelp(rt.left(), k);
        }
        else if (rt.key().compareTo(k) == 0) {
            return rt;
        }
        else {
            return findhelp(rt.right(), k);
        }
    }

    /**
     * Finds value in the BST
     * matching key and value
     * 
     * @param k key
     * @param e value
     * @return value
     */
    public E find(K k, E e) {
        BSTNode<K, E> node = findhelp(root, k);
        if (node == null) {
            return null;
        }
        BSTNode<K, E> temp = findhelp(node, k, e);
        if (temp == null) {
            return null;
        }
        return temp.element();
    }

    /**
     * Handles finding value
     * matching key and value
     * in the BST
     * 
     * @param rt node
     * @param k key
     * @param e value
     * @return value
     */
    private BSTNode<K, E> findhelp(BSTNode<K, E> rt, K k, E e) {
        if (rt == null) {
            return null;
        }
        if (rt.key().compareTo(k) > 0) {
            return findhelp(rt.left(), k, e);
        }
        else if ((rt.key().compareTo(k) == 0) && (rt.element().compareTo(
            e) == 0)) {
            return rt;
        }
        return findhelp(rt.right(), k, e);
    }

    /**
     * Finds all values in the BST
     * matching key
     * 
     * @param k key
     * @return value list
     */
    public ArrayList<E> findall(K k) {
        ArrayList<E> el = new ArrayList<E>();
        BSTNode<K, E> node = findhelp(root, k);
        if (node != null) {
            findallhelp(node, k, el);
        }
        return el;
    }

    /**
     * Handles finding all values
     * matching key
     * in the BST
     * 
     * @param rt node
     * @param k key
     * @param el value list
     * @return node
     */
    private void findallhelp(BSTNode<K, E> rt, K k, ArrayList<E> el) {
        if (rt == null) {
            return;
        }
        if (rt.key().compareTo(k) > 0) {
            findallhelp(rt.left(), k, el);
        }
        else if (rt.key().compareTo(k) == 0) {
            el.add(rt.element());
        }
        findallhelp(rt.right(), k, el);
    }

    /**
     * Finds all values in the BST
     * greater than or equal to k1
     * and less than or equal to k2
     * 
     * @param k1 lower key
     * @param k2 upper key
     * @return array list of elements
     */
    public ArrayList<E> findrange(K k1, K k2) {
        ArrayList<E> el = new ArrayList<E>();
        findrangehelp(root, k1, k2, el);
        return el;
    }

    /**
     * Handles finding all values
     * greater than or equal to k1
     * and less than or equal to k2
     * in the BST
     * 
     * @param rt root
     * @param k1 lower key
     * @param k2 higher key
     * @param el array list of elements
     */
    private void findrangehelp(BSTNode<K, E> rt, K k1, K k2, ArrayList<E> el) {
        if (rt == null) {
            return;
        }
        if (rt.key().compareTo(k1) > 0) {
            findrangehelp(rt.left(), k1, k2, el);
        }
        if ((rt.key().compareTo(k1) >= 0) && (rt.key().compareTo(k2) <= 0)) {
            el.add(rt.element());
        }
        if (rt.key().compareTo(k2) < 0) {
            findrangehelp(rt.right(), k1, k2, el);
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
     * Private Iterator Class
     * Iterator for the BST
     * 
     * @author aisiadinso Angel Isiadinso
     * @author kyleg997 Kyle Galindo
     * @version 2019-09-19
     */
    private class BSTIterator implements Iterator<E> {
        private Stack<BSTNode<K, E>> stack; // stack used to iterate
                                            // over a BST

        /**
         * Initializes the iterator and starts an inorder traversal
         */
        public BSTIterator() {
            if (root != null) {
                stack = new Stack<BSTNode<K, E>>();
                goLeftFrom(root);
            }
        }

        /**
         * In order traversal implementation and pushes
         * values into a stack for later access
         * 
         * @param rt root
         */
        private void goLeftFrom(BSTNode<K, E> rt) {
            while (rt != null) {
                stack.push(rt);
                rt = rt.left();
            }
        }

        /**
         * Checks if stack is empty
         * 
         * @return TRUE if stack not empty
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Gets the next element in the BST
         * 
         * @return value
         */
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
