package bst;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import bst.BSTNode;

/**
 * BSTNodeTest Class
 * 
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-09-18
 */
public class BSTNodeTest {
    private BSTNode<Character, Integer> node1;
    private BSTNode<Character, Integer> node2;

    @Before
    public void setUp() {
        node1 = new BSTNode<Character, Integer>();
        node2 = new BSTNode<Character, Integer>();
    }

    @Test
    public void testConstructor() {
        BSTNode<Character, Integer> node3 = new BSTNode<Character, Integer>('a',
            90, node1, node2);
        assertNotNull(node3);
    }

    @Test
    public void testIsLeaf() {
        BSTNode<Character, Integer> node3 = new BSTNode<Character, Integer>('a',
            90, node1, node2);
        assertFalse(node3.isLeaf());
        assertTrue(node1.isLeaf()); 
        assertTrue(node2.isLeaf()); 
    }
}
