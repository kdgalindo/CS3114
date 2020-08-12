//package student;
//
//import student.TestCase;
//
///**
// * StudentTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kyleg997 Kyle Galindo
// * @version 2019-10-20
// */
//public class StudentTest extends TestCase {
//    private Student s1;
//    private Student s2;
//    private Student s3;
//    
//    /**
//     * Setup for tests
//     */
//    public void setUp() {
//        s1 = new Student(248476061, new Name("Winter", "Hodge"));
//        s2 = new Student(256593948, new Name("Sandra", "Lee", "Duncan"));
//        s3 = new Student(465830040, new Name("Cain", "Buckner"));
//    }
//    
//    /**
//     * Tests the getMiddle() method
//     */
//    public void testGetMiddle() {
//        String m = s2.getMiddle();
//        assertEquals(m, "Lee");
//    }
//    
//    /**
//     * Tests the compareTo() method
//     */
//    public void testCompareTo() {
//        int i = s3.compareTo(s2);
//        assertTrue(i > 0);
//        i = s2.compareTo(s2);
//        assertEquals(i, 0);
//        i = s1.compareTo(s2);
//        assertTrue(i < 0);
//    }
//}
