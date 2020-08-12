//package student;
//
//import student.TestCase;
//
///**
// * NameTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kyleg997 Kyle Galindo
// * @version 2019-10-20
// */
//public class NameTest extends TestCase {
//    private Name n1;
//    private Name n2;
//    private Name n3;
//
//    /**
//     * Setup for tests
//     */
//    public void setUp() {
//        n1 = new Name("Young", "Cao");
//        n2 = new Name("Kyle", "David", "Galindo");
//        n3 = new Name("Angel", "Tehilla", "Isiadinso");
//    }
//
//    /**
//     * Tests the getFirst() method
//     */
//    public void testGetFirst() {
//        String f = n2.getFirst();
//        assertEquals(f, "Kyle");
//    }
//    
//    /**
//     * Tests the getMiddle() method
//     */
//    public void testGetMiddle() {
//        String m = n2.getMiddle();
//        assertEquals(m, "David");
//    }
//
//    /**
//     * Tests the getLast() method
//     */
//    public void testGetLast() {
//        String l = n2.getLast();
//        assertEquals(l, "Galindo");
//    }
//
//    /**
//     * Tests the compareTo() method
//     */
//    public void testCompareTo() {
//        int i = n3.compareTo(n2);
//        assertTrue(i > 0);
//        i = n2.compareTo(n2);
//        assertEquals(i, 0);
//        i = n1.compareTo(n2);
//        assertTrue(i < 0);
//    }
//
//    /**
//     * Tests the toString() method
//     */
//    public void testToString() {
//        String s = n2.toString();
//        assertEquals(s, "Kyle Galindo");
//    }
//}
