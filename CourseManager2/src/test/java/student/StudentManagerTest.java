//package student;
//
//import java.util.Iterator;
//import student.TestCase;
//
///**
// * StudentManagerTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kyleg997 Kyle Galindo
// * @version 2019-10-20
// */
//public class StudentManagerTest extends TestCase {
//    private StudentManager sm;
//    
//    /**
//     * Setup for tests
//     */
//    public void setUp() {
//        sm = new StudentManager();
//        Student s = new Student(248476061, new Name("Winter", "Hodge"));
//        sm.insert(248476061, s);
//        s = new Student(256593948, new Name("Sandra", "Lee", "Duncan"));
//        sm.insert(256593948, s);
//        s = new Student(465830040, new Name("Cain", "Buckner"));
//        sm.insert(465830040, s);
//        s = new Student(239721905, new Name("Aileen", "Ford"));
//        sm.insert(239721905, s);
//        s = new Student(924954712, new Name("Caleb", "Foley"));
//        sm.insert(924954712, s);
//    }
//    
//    /**
//     * Tests the size() method
//     */
//    public void testSize() {
//        int s = sm.size();
//        assertEquals(s, 5);
//    }
//    
//    /**
//     * Tests the isEmpty() method
//     */
//    public void testIsEmpty() {
//        boolean ie = sm.isEmpty();
//        assertFalse(ie);
//    }
//    
//    /**
//     * Tests the insert() method
//     */
//    public void testInsert() {
//        Student st = new Student(369295480, new Name("Uriah", "Gutierrez"));
//        sm.insert(369295480, st);
//        int sz = sm.size();
//        assertEquals(sz, 6);
//    }
//    
//    /**
//     * Tests the search() method
//     */
//    public void testSearch() {
//        String s = sm.search(465830040).toString();
//        assertEquals(s, "465830040, Cain Buckner");
//    }
//    
//    /**
//     * Tests the clear() method
//     */
//    public void testClear() {
//        sm.clear();
//        boolean ie = sm.isEmpty();
//        assertTrue(ie);
//    }
//    
//    /**
//     * Tests the iterator method
//     */
//    public void testIterator() {
//        Iterator<Student> itr = sm.iterator();
//        String s = itr.next().toString();
//        assertEquals(s, "239721905, Aileen Ford");
//        s = itr.next().toString();
//        assertEquals(s, "248476061, Winter Hodge");
//    }
//}
