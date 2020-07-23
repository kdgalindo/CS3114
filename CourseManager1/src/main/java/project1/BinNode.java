package project1;

/**
 * BinNode interface for BSTNode
 * 
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-09-19
 * 
 * @param <E> element
 */
interface BinNode<E> {

    /**
     * Gets element value
     * 
     * @return element
     */
    public E element();


    /**
     * Sets the element value
     * 
     * @param v element
     * @return element
     */
    public E setElement(E v);


    /**
     * Returns the left children of the node
     * 
     * @return left child
     */
    public BinNode<E> left();


    /**
     * Returns the right children of the node
     * 
     * @return right child
     */
    public BinNode<E> right();


    /**
     * Return TRUE if a leaf node exists, FALSE otherwise
     * 
     * @return true or false
     */
    public boolean isLeaf();
}
