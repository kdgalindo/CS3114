package bst;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import bst.BST;

/**
 * BSTTest Class
 * 
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-09-18
 */
public class BSTTest {
    private BST<Character, Integer> bst;

    @Before
    public void setUp() {
        bst = new BST<Character, Integer>();
    }

    @Test
    public void testClear() {
        bst.insert('a', 0);
        bst.insert('b', 1);
        bst.clear();
        assertEquals(bst.size(), 0);
    }

    @Test
    public void testInsert() {
        bst.insert('a', 0);
        bst.insert('b', 1);
        bst.insert('c', 2);
        assertEquals(bst.size(), 3);
    }

    @Test
    public void testRemove1() {
        bst.insert('a', 0);
        bst.insert('b', 1);
        bst.insert('c', 2);
        int i = bst.remove('c');
        assertEquals(i, 2);
    }
    
    @Test
    public void testRemove2() {
        bst.insert('a', 0);
        bst.insert('b', 1);
        bst.insert('c', 2);
        Integer i = bst.remove('d');
        assertNull(i);
    }

    @Test
    public void testFind1() {
        bst.insert('a', 0);
        bst.insert('b', 1);
        bst.insert('c', 2);
        int i = bst.find('c');
        assertEquals(i, 2);
    }

    @Test
    public void testFind2() {
        bst.insert('a', 0);
        bst.insert('b', 1);
        bst.insert('c', 2);
        Integer i = bst.find('d');
        assertNull(i);
    }

    @Test
    public void testSize() {
        assertEquals(bst.size(), 0);
    }

}
