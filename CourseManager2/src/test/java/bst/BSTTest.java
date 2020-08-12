//package bst;
//
//import java.util.ArrayList;
//import student.TestCase;
//
///**
// * BSTTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kyleg997 Kyle Galindo
// * @version 2019-10-20
// */
//public class BSTTest extends TestCase {
//    private BST<Character, Integer> bst;
//
//    /**
//     * Setup for tests
//     */
//    public void setUp() {
//        bst = new BST<Character, Integer>();
//    }
//
//    /**
//     * Tests the clear() method
//     */
//    public void testClear() {
//        bst.insert('a', 0);
//        bst.insert('b', 1);
//        bst.clear();
//        assertEquals(bst.size(), 0);
//    }
//
//    /**
//     * Tests the insert() method
//     */
//    public void testInsert() {
//        bst.insert('a', 0);
//        bst.insert('b', 1);
//        bst.insert('c', 2);
//        assertEquals(bst.size(), 3);
//    }
//
//    /**
//     * Tests the remove() method
//     * (key with any value)
//     * when key exists
//     */
//    public void testRemove1() {
//        bst.insert('a', 0);
//        bst.insert('b', 1);
//        bst.insert('c', 2);
//        int i = bst.remove('c');
//        assertEquals(i, 2);
//    }
//
//    /**
//     * Tests the remove() method
//     * (key with any value)
//     * when key doesn't exist
//     */
//    public void testRemove2() {
//        bst.insert('a', 0);
//        bst.insert('b', 1);
//        bst.insert('c', 2);
//        Integer i = bst.remove('d');
//        assertNull(i);
//    }
//    
//    /**
//     * Tests the remove() method
//     * (key with specific value)
//     * when key exists
//     */
//    public void testRemove3() {
//        bst.insert('m', 0);
//        bst.insert('j', 1);
//        bst.insert('d', 2);
//        bst.insert('b', 3);
//        bst.insert('o', 4);
//        bst.insert('q', 5);
//        bst.insert('d', 6);
//        bst.insert('o', 7);
//        bst.insert('m', 8);
//        bst.insert('b', 9);
//        bst.insert('j', 10);
//        bst.insert('q', 11);
//        bst.inorder();
//        //System.out.println();
//        int i = bst.remove('o', 4);
//        //bst.inorder();
//        //System.out.println();
//        assertEquals(i , 4);
//        i = bst.remove('m', 0);
//        //bst.inorder();
//        //System.out.println();
//        assertEquals(i, 0);
//        i = bst.remove('m', 8);
//        //bst.inorder();
//        assertEquals(i, 8);
//    }
//    
//    /**
//     * Tests the remove() method
//     * (key with specific value)
//     * when key doesn't exist,
//     * but value does
//     */
//    public void testRemove4() {
//        bst.insert('m', 0);
//        bst.insert('j', 1);
//        bst.insert('d', 2);
//        bst.insert('b', 3);
//        bst.insert('o', 4);
//        bst.insert('q', 5);
//        bst.insert('d', 6);
//        bst.insert('o', 7);
//        bst.insert('m', 8);
//        bst.insert('b', 9);
//        bst.insert('j', 10);
//        bst.insert('q', 11);
//        Integer i = bst.remove('a', 0);
//        assertNull(i);
//    }
//    
//    /**
//     * Tests the remove() method
//     * (key with specific value)
//     * when key exists,
//     * but value doesn't
//     */
//    public void testRemove5() {
//        bst.insert('m', 0);
//        bst.insert('j', 1);
//        bst.insert('d', 2);
//        bst.insert('b', 3);
//        bst.insert('o', 4);
//        bst.insert('q', 5);
//        bst.insert('d', 6);
//        bst.insert('o', 7);
//        bst.insert('m', 8);
//        bst.insert('b', 9);
//        bst.insert('j', 10);
//        bst.insert('q', 11);
//        Integer i = bst.remove('m', 12);
//        assertNull(i);
//    }
//
//    /**
//     * Tests the find() method
//     * (key with any value)
//     * when key exists
//     */
//    public void testFind1() {
//        bst.insert('a', 0);
//        bst.insert('b', 1);
//        bst.insert('c', 2);
//        int i = bst.find('c');
//        assertEquals(i, 2);
//    }
//
//    /**
//     * Tests the find() method
//     * (key with any value)
//     * when key doesn't exist
//     */
//    public void testFind2() {
//        bst.insert('a', 0);
//        bst.insert('b', 1);
//        bst.insert('c', 2);
//        Integer i = bst.find('d');
//        assertNull(i);
//    }
//    
//    /**
//     * Tests the find() method
//     * (key with specific value)
//     * when key exists
//     */
//    public void testFind3() {
//        bst.insert('m', 0);
//        bst.insert('j', 1);
//        bst.insert('d', 2);
//        bst.insert('b', 3);
//        bst.insert('o', 4);
//        bst.insert('q', 5);
//        bst.insert('d', 6);
//        bst.insert('o', 7);
//        bst.insert('m', 8);
//        bst.insert('b', 9);
//        bst.insert('j', 10);
//        bst.insert('q', 11);
//        int i = bst.find('o', 4);
//        assertEquals(i , 4);
//        i = bst.find('o', 7);
//        assertEquals(i, 7);
//    }
//    
//    /**
//     * Tests the find() method
//     * (key with specific value)
//     * when key doesn't exist,
//     * but value does
//     */
//    public void testFind4() {
//        bst.insert('m', 0);
//        bst.insert('j', 1);
//        bst.insert('d', 2);
//        bst.insert('b', 3);
//        bst.insert('o', 4);
//        bst.insert('q', 5);
//        bst.insert('d', 6);
//        bst.insert('o', 7);
//        bst.insert('m', 8);
//        bst.insert('b', 9);
//        bst.insert('j', 10);
//        bst.insert('q', 11);
//        Integer i = bst.find('a', 0);
//        assertNull(i);
//    }
//    
//    /**
//     * Tests the find() method
//     * (key with specific value)
//     * when key exists,
//     * but value doesn't
//     */
//    public void testFind5() {
//        bst.insert('m', 0);
//        bst.insert('j', 1);
//        bst.insert('d', 2);
//        bst.insert('b', 3);
//        bst.insert('o', 4);
//        bst.insert('q', 5);
//        bst.insert('d', 6);
//        bst.insert('o', 7);
//        bst.insert('m', 8);
//        bst.insert('b', 9);
//        bst.insert('j', 10);
//        bst.insert('q', 11);
//        Integer i = bst.find('m', 12);
//        assertNull(i);
//    }
//    
//    /**
//     * Tests the findall() method
//     * when key exists
//     */
//    public void testFindAll1() {
//        bst.insert('m', 0);
//        bst.insert('j', 1);
//        bst.insert('d', 2);
//        bst.insert('b', 3);
//        bst.insert('o', 4);
//        bst.insert('q', 5);
//        bst.insert('d', 6);
//        bst.insert('o', 7);
//        bst.insert('m', 8);
//        bst.insert('b', 9);
//        bst.insert('j', 10);
//        bst.insert('q', 11);
//        ArrayList<Integer> ilist = bst.findall('o');
//        assertEquals(ilist.size(), 2);
//    }
//    
//    /**
//     * Tests the findall() method
//     * when key doesn't exist
//     */
//    public void testFindAll2() {
//        bst.insert('m', 0);
//        bst.insert('j', 1);
//        bst.insert('d', 2);
//        bst.insert('b', 3);
//        bst.insert('o', 4);
//        bst.insert('q', 5);
//        bst.insert('d', 6);
//        bst.insert('o', 7);
//        bst.insert('m', 8);
//        bst.insert('b', 9);
//        bst.insert('j', 10);
//        bst.insert('q', 11);
//        ArrayList<Integer> ilist = bst.findall('a');
//        assertEquals(ilist.size(), 0);
//    }
//
//    /**
//     * Tests the size() method
//     */
//    public void testSize() {
//        assertEquals(bst.size(), 0);
//    }
//}
