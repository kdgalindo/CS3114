//package bst;
//
//import student.TestCase;
//
///**
// * BSTNodeTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kyleg997 Kyle Galindo
// * @version 2019-09-18
// */
//public class BSTNodeTest extends TestCase {
//    private BSTNode<Character, Integer> node1;
//    private BSTNode<Character, Integer> node2;
//
//
//    /**
//     * Creates two BSTNodes 
//     */
//    public void setUp() {
//        node1 = new BSTNode<Character, Integer>();
//        node2 = new BSTNode<Character, Integer>();
//
//    }
//
//
//    /**
//     * Tests constructor
//     */
//    public void testConstructor() {
//        BSTNode<Character, Integer> node3 = new BSTNode<Character, Integer>('a',
//            90, node1, node2);
//        assertNotNull(node3);
//    }
//
//
//    /**
//     * Tests leaf command
//     */
//    public void testIsLeaf() {
//        BSTNode<Character, Integer> node3 = new BSTNode<Character, Integer>('a',
//            90, node1, node2);
//        assertFalse(node3.isLeaf());
//        assertTrue(node1.isLeaf()); 
//        assertTrue(node2.isLeaf()); 
//
//    }
//}
