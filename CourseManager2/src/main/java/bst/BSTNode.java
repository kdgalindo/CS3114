package bst;

/**
 * BSTNode Class
 * 
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-10-20
 * 
 * @param <K> key
 * @param <E> value
 */
public class BSTNode<K, E> implements BinNode<E> {
    private K key; // Key for this node
    private E element; // Element for this node
    private BSTNode<K, E> left; // Pointer to left child
    private BSTNode<K, E> right; // Pointer to right child

    /**
     * BSTNode default constructor
     */
    public BSTNode() {
        left = null;
        right = null;
    }

    /**
     * BST Node constructor with key and value initialization
     * 
     * @param k key
     * @param val value
     */
    public BSTNode(K k, E val) {
        left = null;
        right = null;
        key = k;
        element = val;
    }

    /**
     * BST Node constructor with key, value, left node, and right node
     * initialization
     * 
     * @param k key
     * @param val value
     * @param l left node
     * @param r right node
     */
    public BSTNode(K k, E val, BSTNode<K, E> l, BSTNode<K, E> r) {
        left = l;
        right = r;
        key = k;
        element = val;
    }

    /**
     * Gets the key value
     * 
     * @return key
     */
    public K key() {
        return key;
    }

    /**
     * Sets the key value
     * 
     * @param k key
     * @return key
     */
    public K setKey(K k) {
        key = k;
        return key;
    }

    /**
     * gets the element value
     * 
     * @return value
     */
    public E element() {
        return element;
    }

    /**
     * sets the element value
     * 
     * @param v value
     * @return value
     */
    public E setElement(E v) {
        element = v;
        return element;
    }

    /**
     * Gets left node
     * 
     * @return left child
     */
    public BSTNode<K, E> left() {
        return left;
    }

    /**
     * Sets the left child of the Node
     * 
     * @param p left node
     * @return left child
     */
    public BSTNode<K, E> setLeft(BSTNode<K, E> p) {
        left = p;
        return left;
    }

    /**
     * Gets the right child of the Node
     * 
     * @return right child
     */
    public BSTNode<K, E> right() {
        return right;
    }

    /**
     * Sets right child of the node
     * 
     * @param p left node
     * @return right child
     */
    public BSTNode<K, E> setRight(BSTNode<K, E> p) {
        right = p;
        return right;
    }

    /**
     * return TRUE if a leaf node, FALSE otherwise
     * 
     * @return true or false
     */
    public boolean isLeaf() {
        return (left == null) && (right == null);
    }
}
