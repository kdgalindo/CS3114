package bst;

/**
 * BSTNode Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-10
 * 
 * @param <K> key
 * @param <V> value
 */
public class BSTNode<K, V> implements BinNode<V> {
    private K key;
    private V value;
    private BSTNode<K, V> left;
    private BSTNode<K, V> right;

    public BSTNode() {
    	key = null;
    	value = null;
        left = null;
        right = null;
    }

    public BSTNode(K k, V v) {
        key = k;
        value = v;
        left = null;
        right = null;
    }

    public BSTNode(K k, V v, BSTNode<K, V> l, BSTNode<K, V> r) {
        key = k;
        value = v;
        left = l;
        right = r;
    }

    public K key() {
        return key;
    }

    public K setKey(K k) {
        key = k;
        return key;
    }

    public V value() {
        return value;
    }

    public V setValue(V v) {
    	value = v;
        return value;
    }

    public BSTNode<K, V> left() {
        return left;
    }

    public BSTNode<K, V> setLeft(BSTNode<K, V> l) {
        left = l;
        return left;
    }

    public BSTNode<K, V> right() {
        return right;
    }

    public BSTNode<K, V> setRight(BSTNode<K, V> r) {
        right = r;
        return right;
    }

    public boolean isLeaf() {
        return (left == null) && (right == null);
    }
}
