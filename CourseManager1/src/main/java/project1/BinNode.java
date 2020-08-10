package project1;

/**
 * BinNode Interface
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-10
 * 
 * @param <V> value
 */
interface BinNode<V> {

    public V value();

    public V setValue(V v);

    public BinNode<V> left();

    public BinNode<V> right();

    public boolean isLeaf();
}
